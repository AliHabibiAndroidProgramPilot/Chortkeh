package info.alihabibi.sms.parser

import info.alihabibi.domain.models.transaction.TransactionType

/**
 * BluBank Parser
 * Sample Income: "دریافت پل ... 200,000,000 ریال به حساب شما نشست."
 * Sample Outcome: "برداشت پول ... 1,550,000ریال از حساب شما پرید."
 */
object BluBankSmsParser : BaseBankSmsParser() {
    private val amountRegex = Regex("""(\d{1,3}(?:,\d{3})+)\s*ریال""")

    override fun parse(smsText: String): ParsedTransaction? {
        val type = when {
            smsText.contains("به حساب شما نشست") ||
                    smsText.contains("دریافت پل") ||
                    smsText.contains("واریز") -> TransactionType.INCOME

            smsText.contains("از حساب شما پرید") ||
                    smsText.contains("برداشت") -> TransactionType.OUTCOME

            else -> return null
        }
        val amount = extractAmountByRegex(
            getTransactionLines(smsText),
            amountRegex
        ) ?: return null

        return ParsedTransaction(type, amount)
    }
}

/**
 * Pasargad Parser (Prefix Sign Format)
 * Sample Income: "+1,000,000"
 * Sample Outcome: "-1,000,000"
 */
object PasargadSmsParser : BaseBankSmsParser() {
    private val signedLineRegex = Regex("""^([+-])\s*(\d{1,3}(?:,\d{3})+)$""")

    override fun parse(smsText: String): ParsedTransaction? {
        for (line in getTransactionLines(smsText)) {
            val match = signedLineRegex.find(line) ?: continue
            val sign = match.groupValues[1]
            val amount = match.groupValues[2].replace(",", "").toLongOrNull() ?: continue
            val type = if (sign == "+") TransactionType.INCOME else TransactionType.OUTCOME
            return ParsedTransaction(type, amount)
        }
        return null
    }
}

/**
 * Labeled "مبلغ:" Parser (Used by Shahr, Karafarin, Keshavarzi, Maskan, Refah, etc.)
 * Sample Income: "واریز به: ... مبلغ:380,565,358ریال"
 * Sample Outcome: "برداشت از: ... مبلغ:700,000ریال"
 */
object LabeledPriceSmsParser : BaseBankSmsParser() {
    private val priceRegex = Regex("""مبلغ\s*:?\s*(\d{1,3}(?:,\d{3})+)""")

    override fun parse(smsText: String): ParsedTransaction? {
        val type = when {
            smsText.contains("واریز") -> TransactionType.INCOME
            smsText.contains("برداشت") || smsText.contains("خرید") -> TransactionType.OUTCOME
            else -> return null
        }
        val amount =
            extractAmountByRegex(
                getTransactionLines(smsText),
                priceRegex
            ) ?: return null

        return ParsedTransaction(type, amount)
    }
}

/**
 * Action-Labeled Parser (Used by Mellat & Tejarat)
 * Mellat Sample: "واریز10,000,000" / "برداشت674,140"
 * Tejarat Sample: "برداشت: 492,360 ریال"
 */
object ActionLabeledSmsParser : BaseBankSmsParser() {
    private val incomeRegex = Regex("""واریز\s*:?\s*(\d{1,3}(?:,\d{3})+)""")
    private val outcomeRegex = Regex("""برداشت\s*:?\s*(\d{1,3}(?:,\d{3})+)""")

    override fun parse(smsText: String): ParsedTransaction? {
        val lines = getTransactionLines(smsText)
        extractAmountByRegex(lines, incomeRegex)?.let { amount ->
            return ParsedTransaction(TransactionType.INCOME, amount)
        }
        extractAmountByRegex(lines, outcomeRegex)?.let { amount ->
            return ParsedTransaction(TransactionType.OUTCOME, amount)
        }

        return null
    }
}

/**
 * Suffix-Signed Parser (Used by Saderat & Melli)
 * Saderat Sample: "انتقال: 40,000,000+" / "پایانه فروش: 650,000-"
 * Melli Sample: "انتقالی:100,000,000+" / "برداشت:300,000-"
 */
object SuffixSignedSmsParser : BaseBankSmsParser() {
    private val suffixSignRegex = Regex("""(\d{1,3}(?:,\d{3})+)\s*([+-])$""")

    override fun parse(smsText: String): ParsedTransaction? {
        for (line in getTransactionLines(smsText)) {
            val match = suffixSignRegex.find(line) ?: continue
            val amount = match.groupValues[1].replace(",", "").toLongOrNull() ?: continue
            val sign = match.groupValues[2]

            val type = if (sign == "+") TransactionType.INCOME else TransactionType.OUTCOME
            return ParsedTransaction(type, amount)
        }
        return null
    }
}