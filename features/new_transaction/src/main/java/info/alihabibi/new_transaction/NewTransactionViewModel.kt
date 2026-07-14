package info.alihabibi.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.common.Utils.loog
import info.alihabibi.domain.local.usecases.database.usecase.CategoryUseCases
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewTransactionViewModel(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewTransactionUiState())
    val uiState: StateFlow<NewTransactionUiState> = _uiState.asStateFlow()

    val formattedTransactionDate: StateFlow<String> = combine(
        uiState.map { it.transactionYear },
        uiState.map { it.transactionMonth },
        uiState.map { it.transactionDay }
    ) { year, month, day ->
        if (year != null && month != null && day != null)
            PersianDateFormatter.format(year, month, day)
        else ""
    }.stateIn(
        scope = viewModelScope,
        initialValue = "",
        started = SharingStarted.WhileSubscribed(3_000)
    )

    fun onEvent(event: NewTransactionUiIntent) {
        when (event) {

            is NewTransactionUiIntent.Init -> init()

            is NewTransactionUiIntent.OnPriceChanged -> changePrice(event.price)

            is NewTransactionUiIntent.OnDateChanged -> changeDate(event.year, event.month, event.day)

            is NewTransactionUiIntent.OnTimeChanged -> changeTime(event.hour, event.minute)

            is NewTransactionUiIntent.OnCategoryChanged -> changeCategory(event.category)

            is NewTransactionUiIntent.OnTransactionTypeChanged -> changeTransactionType(event.type)

        }
    }

    private fun init() {
        viewModelScope.launch {
            combine(
                categoryUseCases.getCategoriesUseCase.invoke(),
                _uiState.map { it.transactionType }.distinctUntilChanged()
            ) { categories, transactionType ->
                categories
                    .map { it.toUiModel() }
                    .filter {
                        when (transactionType) {
                            TransactionTypeOptionUiModel.OUTCOME -> it.type == CategoryTypeOptionUiModel.OUTCOME
                            TransactionTypeOptionUiModel.INCOME -> it.type == CategoryTypeOptionUiModel.INCOME
                        }
                    }
            }.collect { filteredCategories ->
                _uiState.update { it.copy(categories = filteredCategories) }
            }
        }
    }

    private fun changePrice(price: String) {
        val digits = price
            .filter(Char::isDigit)
            .take(17)
            .trimStart('0')
        _uiState.update {
            it.copy(transactionPrice = digits)
        }
    }

    private fun changeTime(hour: Int?, minute: Int?) {
        val formattedTime =
            if (hour != null && minute != null) "$hour : $minute" else ""
        _uiState.update {
            it.copy(
                formattedTransactionTime = formattedTime,
                transactionHour = hour,
                transactionMinute = minute
            )
        }
    }

    private fun changeDate(year: Int, month: Int, day: Int) {
        _uiState.update {
            it.copy(
                transactionYear = year,
                transactionMonth = month,
                transactionDay = day
            )
        }
    }

    private fun changeTransactionType(type: TransactionTypeOptionUiModel) {
        _uiState.update {
            it.copy(transactionType = type, transactionCategory = null)
        }
    }

    private fun changeCategory(category: CategoryUiModel) {
        _uiState.update { it.copy(transactionCategory = category) }
    }

}

sealed interface NewTransactionUiIntent {

    data object Init : NewTransactionUiIntent

    data class OnPriceChanged(val price: String) : NewTransactionUiIntent

    data class OnDateChanged(val year: Int, val month: Int, val day: Int) : NewTransactionUiIntent

    data class OnTimeChanged(val hour: Int?, val minute: Int?) : NewTransactionUiIntent

    data class  OnCategoryChanged(val category: CategoryUiModel) : NewTransactionUiIntent

    data class OnTransactionTypeChanged(val type: TransactionTypeOptionUiModel) :
        NewTransactionUiIntent

}

data class NewTransactionUiState(
    val transactionType: TransactionTypeOptionUiModel = TransactionTypeOptionUiModel.INCOME,
    val transactionPrice: String = "",
    val formattedTransactionTime: String = "",
    val transactionHour: Int? = null,
    val transactionMinute: Int? = null,
    val transactionYear: Int? = null,
    val transactionMonth: Int? = null,
    val transactionDay: Int? = null,
    val transactionCategory: CategoryUiModel? = null,
    val categories: List<CategoryUiModel> = emptyList()
)