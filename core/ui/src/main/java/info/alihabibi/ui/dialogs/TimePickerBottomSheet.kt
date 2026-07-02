package info.alihabibi.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray11
import info.alihabibi.designsystem.theme.Gray12
import info.alihabibi.ui.buttons.AppButton

/**
 * Bottom sheet time picker with "staged" state management.
 *
 * State flow:
 * - `pickerValue`      -> last confirmed time value.
 * - `tmpPickerValue`   -> currently selected value inside the picker.
 *
 * User interactions:
 * - While scrolling the picker, only `tmpPickerValue` is updated and
 *   `onTimeValueChange()` is invoked for live updates.
 * - Pressing Confirm copies `tmpPickerValue` into `pickerValue` and
 *   returns the selected time through `onSubmitClick()`.
 * - Pressing Cancel ignores any unconfirmed picker changes and returns
 *   the last confirmed value through `onDismissRequest()`.
 *
 * This separation prevents accidental persistence of picker changes
 * until the user explicitly confirms the selection.
 */

@Composable
fun TimePickerBottomSheetContent(
    initialTime: Pair<Int, Int> = Pair(18, 30),
    onSubmitClick: (hour: Int, minute: Int) -> Unit,
    onTimeValueChange: (hour: Int, minute: Int) -> Unit,
    onDismissRequest: (confirmedHour: Int, confirmedMinute: Int) -> Unit,
) {

    // Last confirmed value (restored when user cancels)
    var pickerValue by remember {
        mutableStateOf<Hours>(
            FullHours(initialTime.first, initialTime.second)
        )
    }
    var tmpPickerValue by remember {
        mutableStateOf<Hours>(
            FullHours(initialTime.first, initialTime.second)
        )
    }

    Surface {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.clock),
            style = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Spacer(modifier = Modifier.height(height = 40.dp))

                HoursNumberPicker(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    dividersColor = Gray12,
                    leadingZero = false,
                    textStyle = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
                    hoursDivider = {
                        Text(
                            modifier = Modifier
                                .size(size = 24.dp)
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center,
                            text = ":"
                        )
                    },
                    onValueChange = {
                        onTimeValueChange(it.hours, it.minutes)
                        tmpPickerValue = it
                    },
                    value = tmpPickerValue
                )

            }

            Spacer(modifier = Modifier.size(size = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {

                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = Gray11),
                    onClick = {
                        onDismissRequest(pickerValue.hours, pickerValue.minutes)
                    },
                    shape = RectangleShape,
                    content = {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp,
                                color = Gray11
                            )
                        )
                    }
                )

                AppButton(
                    onClick = {
                        pickerValue = tmpPickerValue
                        onSubmitClick(pickerValue.hours, pickerValue.minutes)
                    },
                    modifier = Modifier.weight(weight = 1f),
                    text = stringResource(id = R.string.confirm)
                )

            }

        }

    }

}