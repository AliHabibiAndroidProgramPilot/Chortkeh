package info.alihabibi.ui.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.common.banks.Bank
import info.alihabibi.common.banks.BankCardIdentifier
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Black
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray9
import info.alihabibi.designsystem.theme.Primary

@Composable
fun AppTitledTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (newValue: String) -> Unit,
    title: String = "",
    error: Boolean = false,
    placeHolderText: String = "",
    errorMessage: String = "",
    enabled: Boolean = true
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 12.dp, end = 12.dp, top = 6.dp),
            value = text,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Right,
                textDirection = TextDirection.Rtl
            ),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            isError = error,
            singleLine = true,
            placeholder = {
                if (placeHolderText.isNotEmpty())
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeHolderText,
                        style = MaterialTheme.typography.labelLarge.copy(
                            textAlign = TextAlign.Right,
                            textDirection = TextDirection.Rtl
                        )
                    )
            },
            shape = RoundedCornerShape(size = 12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                focusedTextColor = Black,
                cursorColor = Primary,
                unfocusedBorderColor = Gray11,
                unfocusedPlaceholderColor = Gray11,
                focusedPlaceholderColor = Gray9,
                unfocusedTextColor = Gray11,
                errorBorderColor = ErrorRed,
                errorTextColor = Black
            )
        )

        if (error)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium.copy(
                        textAlign = TextAlign.End,
                        color = ErrorRed
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.warning_red),
                    tint = ErrorRed,
                    contentDescription = null
                )

            }

    }

}

@Composable
fun AppTitledPriceTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (newValue: String) -> Unit,
    title: String = "",
    placeHolderText: String = "",
    enabled: Boolean = true
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 12.dp, end = 12.dp, top = 6.dp),
            value = text,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Left,
                textDirection = TextDirection.Ltr
            ),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            singleLine = true,
            placeholder = {
                if (placeHolderText.isNotEmpty())
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeHolderText,
                        style = MaterialTheme.typography.labelLarge.copy(
                            textAlign = TextAlign.Right,
                            textDirection = TextDirection.Rtl
                        )
                    )
            },
            shape = RoundedCornerShape(size = 12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                focusedTextColor = Black,
                cursorColor = Primary,
                unfocusedBorderColor = Gray11,
                unfocusedPlaceholderColor = Gray11,
                focusedPlaceholderColor = Gray9,
                unfocusedTextColor = Gray11,
                errorBorderColor = ErrorRed,
                errorTextColor = Black
            ),
            prefix = if (text.isNotEmpty()) {
                {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(id = R.string.toman),
                        style = MaterialTheme.typography.labelLarge.copy(color = Gray11)
                    )
                }
            } else null,
            visualTransformation = PriceVisualTransformation()
        )

    }

}

@Composable
fun AppTitledPhoneTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (newValue: String) -> Unit,
    title: String = "",
    error: Boolean = false,
    placeHolderText: String = "",
    errorMessage: String = "",
    enabled: Boolean = true
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 12.dp, end = 12.dp, top = 6.dp),
            value = text,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Left,
                textDirection = TextDirection.Ltr
            ),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            isError = error,
            singleLine = true,
            placeholder = {
                if (placeHolderText.isNotEmpty())
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeHolderText,
                        style = MaterialTheme.typography.labelLarge.copy(
                            textAlign = TextAlign.Left,
                            textDirection = TextDirection.Ltr
                        )
                    )
            },
            shape = RoundedCornerShape(size = 12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                focusedTextColor = Black,
                cursorColor = Primary,
                unfocusedBorderColor = Gray11,
                unfocusedPlaceholderColor = Gray11,
                focusedPlaceholderColor = Gray9,
                unfocusedTextColor = Gray11,
                errorBorderColor = ErrorRed,
                errorTextColor = Black
            ),
            visualTransformation = PhoneVisualTransformation()
        )

        if (error)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium.copy(
                        textAlign = TextAlign.End,
                        color = ErrorRed
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.warning_red),
                    tint = ErrorRed,
                    contentDescription = null
                )

            }

    }

}

@Composable
fun AppCardNumberTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (newValue: String) -> Unit,
    title: String = ""
) {

    val tempBank = remember(key1 = text.length >= 6) {
        if (text.length >= 6) BankCardIdentifier.identify(text) else Bank.UNKNOWN
    }
    val bank = remember(key1 = tempBank, key2 = text.length >= 8) {
        if (text.length >= 8) BankCardIdentifier.identifyPossibleNeoBanks(text, tempBank) else tempBank
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 12.dp, end = 12.dp, top = 6.dp),
            value = text,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(size = 28.dp),
                    painter = painterResource(id = bank.iconResId),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            textStyle = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Left,
                textDirection = TextDirection.Ltr
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            singleLine = true,
            shape = RoundedCornerShape(size = 12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                focusedTextColor = Black,
                cursorColor = Primary,
                unfocusedBorderColor = Gray11,
                unfocusedTextColor = Gray11,
            ),
            visualTransformation = CardNumberVisualTransformation()
        )

    }

}

@Preview
@Composable
fun InputTextFieldPreview() {

    AppTitledTextField(
        title = "نام و نام خانوادگی",
        text = "علی حبیبی",
        onValueChange = {}
    )

}