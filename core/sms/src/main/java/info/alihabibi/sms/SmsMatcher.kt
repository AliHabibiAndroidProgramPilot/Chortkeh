package info.alihabibi.sms

object SmsMatcher {

    /**
     * Checks if the incoming sender address matches a saved channel SMS source.
     * Handles phone number normalization (e.g., +989123456789 vs 09123456789 vs 9123456789)
     * as well as shortcodes and alphanumeric sender IDs (e.g., "BankMelli", "200000").
     */
    fun matches(savedSource: String?, incomingSender: String?): Boolean {
        if (savedSource.isNullOrBlank() || incomingSender.isNullOrBlank()) return false

        val cleanSaved = savedSource.trim()
        val cleanSender = incomingSender.trim()

        // Direct exact or case-insensitive match (for shortcodes / bank names / exact numbers)
        if (cleanSaved.equals(cleanSender, ignoreCase = true)) return true

        // Check if both look like phone numbers
        val savedDigits = cleanSaved.filter { it.isDigit() }
        val senderDigits = cleanSender.filter { it.isDigit() }

        if (savedDigits.length >= 7 && senderDigits.length >= 7) {
            // Take the last 9 digits (significant digits of phone numbers)
            val minLen = minOf(9, savedDigits.length, senderDigits.length)
            val savedSuffix = savedDigits.takeLast(minLen)
            val senderSuffix = senderDigits.takeLast(minLen)

            if (savedSuffix == senderSuffix) return true
        }

        return false
    }
}
