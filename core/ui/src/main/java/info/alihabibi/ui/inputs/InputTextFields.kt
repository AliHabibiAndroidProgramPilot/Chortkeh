package info.alihabibi.ui.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Black
import info.alihabibi.designsystem.theme.ChortkehTheme
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.Gray9
import info.alihabibi.designsystem.theme.Primary

@Composable
fun PhoneNumberTextField(
    phone: String,
    onValueChange: (newValue: String) -> Unit,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
    error: Boolean = false,
    errorMessage: String = stringResource(id = R.string.empty_phone_number_warning),
    enabled: Boolean = true
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 2.dp),
            text = stringResource(id = R.string.phone_number),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                textAlign = TextAlign.End
            )
        )

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                value = phone,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyLarge,
                enabled = enabled,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    showKeyboardOnFocus = true
                ),
                isError = error,
                singleLine = true,
                trailingIcon = { MobileTextFieldIcons() },
                leadingIcon = { MobileTextFieldIcons() },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.phone_number),
                        style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.End)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    focusedLeadingIconColor = Black,
                    focusedTrailingIconColor = Black,
                    focusedTextColor = Black,
                    cursorColor = Black,
                    unfocusedBorderColor = Gray9,
                    unfocusedLeadingIconColor = Gray9,
                    unfocusedTrailingIconColor = Gray9,
                    unfocusedPlaceholderColor = Gray9,
                    unfocusedTextColor = Gray9,
                    errorBorderColor = ErrorRed,
                    errorLeadingIconColor = Black,
                    errorTrailingIconColor = Black,
                    errorTextColor = Black
                )
            )

        }

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
fun MobileTextFieldIcons() {
    Icon(
        painter = painterResource(id = R.drawable.mobile),
        contentDescription = null
    )
}

@Preview
@Composable
fun InputTextFieldPreview() {

    ChortkehTheme {

        PhoneNumberTextField(
            phone = "09214101822",
            onValueChange = {}
        )

    }

}