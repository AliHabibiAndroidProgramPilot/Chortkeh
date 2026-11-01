package info.alihabibi.sms.parser

abstract class BaseBankSmsParser {

    abstract fun parse(smsText: String): ParsedTransaction?

    protected fun getTransactionLines(normalizedSms: String): List<String> {
        return normalizedSms.lines()
            .map { it.trim() }
            .filter { line ->
                line.isNotEmpty() &&
                        !line.startsWith("مانده") &&
                        !line.startsWith("موجودی") &&
                        !line.startsWith("حساب")
            }
    }

    protected fun extractAmountByRegex(lines: List<String>, regex: Regex): Long? {
        for (line in lines) {
            val matchResult = regex.find(line)
            if (matchResult != null) {
                val rawNumber = matchResult.groupValues[1].replace(",", "")
                return rawNumber.toLongOrNull()
            }
        }
        return null
    }

}