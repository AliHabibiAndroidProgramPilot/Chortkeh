package info.alihabibi.ui.inputs

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * This class is visual transformer for phone text fields, where it can show phone number as excepted with extra space characters.
 * @author GPT-5, Check by Ali Habibi
 * @since version 1.0
 */
class PhoneVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formatted = buildString {
            text.text.forEachIndexed { index, character ->
                append(character)
                when (index) {
                    3, 6 -> append(" ")
                }
            }
        }

        return TransformedText(
            androidx.compose.ui.text.AnnotatedString(formatted),
            object : OffsetMapping {

                override fun originalToTransformed(offset: Int): Int {
                    return when {
                        offset <= 4 -> offset
                        offset <= 7 -> offset + 1
                        else -> offset + 2
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return when {
                        offset <= 4 -> offset
                        offset <= 8 -> offset - 1
                        else -> offset - 2
                    }
                }
            }
        )

    }

}