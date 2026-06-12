package info.alihabibi.ui.inputs

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * This class is visual transformer for price text fields, where it can show price number as excepted with comma characters.
 * @author Claude Sonnet 4.6, Check by Ali Habibi
 * @since version 1.0
 */
class PriceVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val formattedNumber = formatWithCommas(originalText)
        val transformedText = AnnotatedString(formattedNumber)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val n = originalText.length
                var commas = 0
                for (k in 1..n) {
                    val commaAt = n - 3 * k
                    if (commaAt in 1..offset) commas++
                }
                return offset + commas
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset >= formattedNumber.length) return originalText.length
                var commas = 0
                for (i in 0 until offset) {
                    if (formattedNumber[i] == ',') commas++
                }
                return (offset - commas).coerceIn(0, originalText.length)
            }
        }

        return TransformedText(transformedText, offsetMapping)
    }

    private fun formatWithCommas(input: String): String {
        if (input.isEmpty()) return ""
        val n = input.length
        return buildString {
            input.forEachIndexed { index, c ->
                append(c)
                val fromRight = n - 1 - index
                if (fromRight > 0 && fromRight % 3 == 0) append(',')
            }
        }
    }
}