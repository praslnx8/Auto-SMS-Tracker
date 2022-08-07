package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.expense.AddExpenseUseCase
import app.expense.domain.expense.Expense
import app.expense.domain.expense.FetchExpenseUseCase
import app.expense.domain.suggestion.FetchSuggestionUseCase
import app.expense.presentation.viewStates.AddExpenseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val fetchExpenseUseCase: FetchExpenseUseCase,
    private val fetchSuggestionUseCase: FetchSuggestionUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _addExpenseViewStateFlow = MutableStateFlow(AddExpenseViewState())
    val addExpenseViewState: StateFlow<AddExpenseViewState>
        get() = _addExpenseViewStateFlow

    suspend fun getAddExpenseViewState(
        expenseId: Long? = null,
        suggestionId: Long? = null
    ) {
        if (expenseId != null) {
            val expense = fetchExpenseUseCase.getExpense(expenseId).run {
                collect()
                last()
            }
            _addExpenseViewStateFlow.value = AddExpenseViewState(
                amount = expense.amount.toString(),
                paidTo = expense.paidTo?:"",
                category = expense.category,
                time = expense.time
            )

        } else if (suggestionId != null) {
            val suggestion = fetchSuggestionUseCase.getSuggestion(suggestionId).run {
                collect()
                last()
            }
            //TODO Get category based on paidTo by ML or other intelligent way
            _addExpenseViewStateFlow.value = AddExpenseViewState(
                amount = suggestion.amount.toString(),
                paidTo = suggestion.paidTo?:"",
                time = suggestion.time
            )
        }
    }

    suspend fun addExpense(amount: String, paidTo: String?, category: String?, time: Long) {
        addExpenseUseCase.addExpense(
            Expense(
                amount = amount.toDouble(),
                paidTo = paidTo,
                category = category ?: "OTHER",
                time = time
            )
        )
    }
}