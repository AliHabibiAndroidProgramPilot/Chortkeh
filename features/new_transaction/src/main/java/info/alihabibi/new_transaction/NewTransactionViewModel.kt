package info.alihabibi.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.designsystem.R
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.category.CategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NewTransactionViewModel : ViewModel() {

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

            is NewTransactionUiIntent.OnDateChanged -> changeDate(
                event.year,
                event.month,
                event.day
            )

            is NewTransactionUiIntent.OnTimeChanged -> changeTime(event.hour, event.minute)

            is NewTransactionUiIntent.OnTransactionTypeChanged -> changeTransactionType(event.type)

        }
    }

    private fun init() {
        // currently mocking categories
        val categories = listOf(
            CategoryUiModel(id = 1, title = "خوردنی", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 2, title = "خوشگذرونی", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 3, title = "خونه", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 4, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 5, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 6, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 7, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 8, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 9, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 10, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 11, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 12, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 13, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 14, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 15, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 16, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 17, title = "ماشین", iconResId = R.drawable.header_app_logo),
            CategoryUiModel(id = 18, title = "ماشین", iconResId = R.drawable.header_app_logo),
        )
        _uiState.update {
            it.copy(categories = categories)
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
            it.copy(transactionType = type)
        }
    }

}

sealed interface NewTransactionUiIntent {

    data object Init : NewTransactionUiIntent

    data class OnPriceChanged(val price: String) : NewTransactionUiIntent

    data class OnDateChanged(val year: Int, val month: Int, val day: Int) : NewTransactionUiIntent

    data class OnTimeChanged(val hour: Int?, val minute: Int?) : NewTransactionUiIntent

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
    val categories: List<CategoryUiModel> = emptyList()
)