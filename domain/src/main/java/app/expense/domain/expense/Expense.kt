package app.expense.domain.expense

data class Expense(
    val id: Long? = null,
    val amount: Double,
    val paidTo: String?,
    val category: String,
    val time: Long,
)