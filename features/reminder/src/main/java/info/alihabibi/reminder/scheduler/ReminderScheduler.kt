package info.alihabibi.reminder.scheduler

interface ReminderScheduler {

    fun schedule(reminderId: Long, triggerAtMillis: Long)

    fun cancelReminder(reminderId: Long)

}