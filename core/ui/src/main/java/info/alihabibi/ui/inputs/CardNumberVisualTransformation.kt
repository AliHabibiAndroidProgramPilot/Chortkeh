package info.alihabibi.ui.inputs

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardNumberVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val formatted = buildString {
            digits.forEachIndexed { index, char ->
                if (index != 0 && index % 4 == 0) append(' ')
                append(char)
            }
        }

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                return offset + (offset - 1) / 4
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return offset
                return (offset - offset / 5).coerceIn(0, digits.length)
            }

        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)

    }

}