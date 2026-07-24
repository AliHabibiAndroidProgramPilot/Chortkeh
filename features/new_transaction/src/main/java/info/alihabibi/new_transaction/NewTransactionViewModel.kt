package info.alihabibi.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.domain.local.usecases.database.usecase.CategoryUseCases
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryIconOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
        started = SharingStarted.WhileSubscribed(5_000, 10_000)
    )

    fun onEvent(event: NewTransactionUiIntent) {
        when (event) {

            is NewTransactionUiIntent.Init -> init()

            is NewTransactionUiIntent.SaveCategory -> saveCategory()

            is NewTransactionUiIntent.PriceChanged -> changePrice(event.price)

            is NewTransactionUiIntent.DateChanged -> changeDate(event.year, event.month, event.day)

            is NewTransactionUiIntent.TimeChanged -> changeTime(event.hour, event.minute)

            is NewTransactionUiIntent.CategoryChanged -> changeCategory(event.category)

            is NewTransactionUiIntent.CategoryNameChanged -> changeCategoryName(event.categoryName)

            is NewTransactionUiIntent.CategoryTypeChanged -> changeCategoryType(event.categoryType)

            is NewTransactionUiIntent.CategoryIconChanged -> changeCategoryIcon(event.categoryIcon)

            is NewTransactionUiIntent.TransactionTypeChanged -> changeTransactionType(event.type)

        }
    }

    private fun init() {
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
        }.onEach { filteredCategories ->
            _uiState.update { it.copy(categories = filteredCategories) }
        }.launchIn(viewModelScope)
    }

    private fun saveCategory() {
        viewModelScope.launch {
            val state = _uiState.value
            if(state.categoryName.isEmpty() || state.categoryType == null || state.categoryIcon == null)
                return@launch

            val category = CategoryUiModel(
                title = _uiState.value.categoryName,
                isDefault = false,
                icon = _uiState.value.categoryIcon ?: CategoryIconOptionUiModel.OTHERS,
                type = _uiState.value.categoryType ?: CategoryTypeOptionUiModel.OUTCOME
            ).toDomain()
            categoryUseCases.saveCategoryUseCase.invoke(category)
            // reset Saved values from ui state
            _uiState.update {
                it.copy(
                    categoryName = "",
                    categoryType = null,
                    categoryIcon = null
                )
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

    private fun changeCategoryName(categoryName: String) {
        if (categoryName.length <= 30)
            _uiState.update { it.copy(categoryName = categoryName) }
    }

    private fun changeCategoryType(categoryType: CategoryTypeOptionUiModel) {
        _uiState.update { it.copy(categoryType = categoryType) }
    }

    private fun changeCategoryIcon(categoryIcon: CategoryIconOptionUiModel) {
        _uiState.update { it.copy(categoryIcon = categoryIcon) }
    }

}

sealed interface NewTransactionUiIntent {

    data object Init : NewTransactionUiIntent

    data object SaveCategory: NewTransactionUiIntent

    data class PriceChanged(val price: String) : NewTransactionUiIntent

    data class DateChanged(val year: Int, val month: Int, val day: Int) : NewTransactionUiIntent

    data class TimeChanged(val hour: Int?, val minute: Int?) : NewTransactionUiIntent

    data class CategoryChanged(val category: CategoryUiModel) : NewTransactionUiIntent

    data class CategoryNameChanged(val categoryName: String) : NewTransactionUiIntent

    data class CategoryTypeChanged(val categoryType: CategoryTypeOptionUiModel) : NewTransactionUiIntent

    data class CategoryIconChanged(val categoryIcon: CategoryIconOptionUiModel) : NewTransactionUiIntent

    data class TransactionTypeChanged(val type: TransactionTypeOptionUiModel) : NewTransactionUiIntent

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
    val categoryName: String = "",
    val categoryType: CategoryTypeOptionUiModel? = null,
    val categoryIcon: CategoryIconOptionUiModel? = null,
    val categories: List<CategoryUiModel> = emptyList()
) {
    val isCategorySaveEnabled: Boolean
        get() = categoryName.isNotEmpty() && categoryType != null && categoryIcon != null
}