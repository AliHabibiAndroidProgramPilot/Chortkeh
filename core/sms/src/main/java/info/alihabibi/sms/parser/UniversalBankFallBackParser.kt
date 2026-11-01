package info.alihabibi.sms.parser

import info.alihabibi.domain.models.transaction.TransactionType

object UniversalBankFallBackParser : BaseBankSmsParser() {

    private val specificParsers = listOf(
        BluBankSmsParser,
        PasargadSmsParser,
        SuffixSignedSmsParser,
        LabeledPriceSmsParser,
        ActionLabeledSmsParser
    )

    private val fallbackCurrencyRegex = Regex("""(\d{1,3}(?:,\d{3})+)""")

    override fun parse(smsText: String): ParsedTransaction? {
        // Try all known structured bank parsers in order of strictness
        for (parser in specificParsers) {
            val parsResult = parser.parse(smsText)
            if (parsResult != null) return parsResult
        }

        val type = when {
            smsText.contains("واریز") ||
                    smsText.contains("افزایش") ||
                    smsText.contains("دریافت") ||
                    smsText.contains("نشست") -> TransactionType.INCOME

            smsText.contains("برداشت") ||
                    smsText.contains("کاهش") ||
                    smsText.contains("کسر") ||
                    smsText.contains("پرید") -> TransactionType.OUTCOME

            else -> return null
        }
        val amount = extractAmountByRegex(
            getTransactionLines(smsText),
            fallbackCurrencyRegex
        ) ?: return null

        return ParsedTransaction(type, amount)
    }

}