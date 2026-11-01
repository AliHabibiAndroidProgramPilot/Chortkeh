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
        rawSms.forEach { character ->
            val mapped = when (character) {
                in '۰'..'۹' -> '0' + (character - '۰')
                in '٠'..'٩' -> '0' + (character - '٠')
                'ي', 'ئ' -> 'ی'
                'ك' -> 'ک'
                '٬', '،' -> ','
                else -> character
            }
            sb.append(mapped)
        }
        return sb.toString().trim()
    }

    internal fun isLikelyTransactionSms(normalizedSms: String) : Boolean {
        return ignoredKeywords.none { normalizedSms.contains(it) }
    }

}