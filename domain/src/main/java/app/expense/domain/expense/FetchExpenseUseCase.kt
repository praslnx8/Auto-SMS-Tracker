package app.expense.domain.expense

import app.expense.api.ExpenseAPI
import app.expense.domain.mappers.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchExpenseUseCase(
    private val expenseAPI: ExpenseAPI,
    private val dataMapper: DataMapper
) {

    fun getExpenses(from: Long?, to: Long): Flow<List<Expense>> {
        return expenseAPI.getExpenses(from = from, upTo = to).map { expenses ->
            expenses.map { expenseDTO ->
                dataMapper.mapToExpense(expenseDTO)
            }
        }
    }

    fun getExpense(id: Long): Flow<Expense> {
        return expenseAPI.getExpense(id).map { expenseDTO ->
            dataMapper.mapToExpense(expenseDTO)
        }
    }
}