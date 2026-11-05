package info.alihabibi.sms.parser

object SmsNormalizer {

    private val ignoredKeywords = arrayOf(
        "رمز پویا",
        "رمز دوم",
        "کد تایید",
        "کد ورود",
        "ورود به همراه بانک",
        "استعلام",
        "ورود",
        "تایید",
        "همراه بانک",
        "رمز",
        "خوش آمدید",
        "خدمات",
        "مشتری گرامی",
        "مشترک گرامی"
    )

    internal fun normalizer(rawSms: String): String {
        val sb = StringBuilder(rawSms.length)
        for (ch in rawSms) {
            when (ch) {
                in '\u06F0'..'\u06F9' -> sb.append('0' + (ch - '\u06F0')) // Persian digits
                in '\u0660'..'\u0669' -> sb.append('0' + (ch - '\u0660')) // Arabic-Indic digits
                '\u064A', '\u0649' -> sb.append('\u06CC')                 // ي, ى -> ی
                '\u0643' -> sb.append('\u06A9')                           // ك -> ک
                '\u0629' -> sb.append('\u0647')                           // ة -> ه
                '\u066C', '\u060C' -> sb.append(',')                      // ٬ ، -> ,
                '\u00A0' -> sb.append(' ')                                // NBSP -> space
                '\u200C', '\u200D', '\u200E', '\u200F',                   // ZWNJ, ZWJ, LRM, RLM
                in '\u202A'..'\u202E', in '\u2066'..'\u2069',             // bidi embeddings/isolates
                '\u0640', '\uFEFF' -> Unit                                // tatweel, BOM: drop
                else -> sb.append(ch)
            }
        }
        return sb.toString().trim()
    }

    internal fun isLikelyTransactionSms(normalizedSms: String) : Boolean {
        return ignoredKeywords.none { normalizedSms.contains(it) }
    }

}