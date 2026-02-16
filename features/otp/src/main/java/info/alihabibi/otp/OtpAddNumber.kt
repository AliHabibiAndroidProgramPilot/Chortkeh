package info.alihabibi.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import info.alihabibi.designsystem.R
import info.alihabibi.ui.inputs.PhoneNumberTextField

@Composable
fun OtpAddNumberScreen() {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 20.dp, end = 20.dp, bottom = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.budget_pana_onboard),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                text = stringResource(R.string.please_add_number),
                style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
            )

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                PhoneNumberTextField(
                    phone = "",
                    onValueChange = {},
                    placeHolderText = "09*********",
                    enableTrailingIcon = false,
                    enableLeadingIcon = false
                )

            }

        }

    }

}