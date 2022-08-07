package app.expense.presentation.viewStates

import java.util.*

sealed class DateRange {
    object ThisMonth : DateRange() {
        override fun getFrom(): Long? {
            return null
        }

        override fun getTo(): Long {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            return calendar.timeInMillis
        }

    }

    object Last30Days : DateRange() {
        override fun getFrom(): Long? {
            return null
        }

        override fun getTo(): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -30)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            return calendar.timeInMillis
        }

    }

    object LastMonth : DateRange() {
        override fun getFrom(): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            return calendar.timeInMillis
        }

        override fun getTo(): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, 30)
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 23)
            calendar.set(Calendar.SECOND, 59)
            return calendar.timeInMillis
        }

    }

    object ThisYear : DateRange() {
        override fun getFrom(): Long? {
            return null
        }

        override fun getTo(): Long {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_YEAR, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            return calendar.timeInMillis
        }

    }

    data class Custom(private val from: Long?, private val to: Long) : DateRange() {
        override fun getFrom(): Long? {
            return from
        }

        override fun getTo(): Long {
            return to
        }

    }

    abstract fun getFrom(): Long?

    abstract fun getTo(): Long
}