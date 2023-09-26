package com.example.cft_lab.ui.sign_up

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.cft_lab.ui.theme.Cft_labTheme
import com.example.cft_lab.ui.theme.PADDING_BIG
import com.example.cft_lab.ui.theme.PADDING_MED
import com.example.domain.models.User
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSuccess: () -> Unit,
    onIntent: (SignUpIntent) -> Unit,
    uiState: SignUpViewModel.UiState
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(vertical = PADDING_MED, horizontal = PADDING_BIG)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(PADDING_BIG),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,

            )

        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = { onIntent(SignUpIntent.UpdateFirstName(it)) },
            label = {
                Text("firstname")
            },
            supportingText = {
                if (uiState.firstNameError != null)
                    Text(uiState.firstNameError)
            },
            isError = uiState.firstNameError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = { onIntent(SignUpIntent.UpdateLastName(it)) },
            supportingText = {
                if (uiState.lastNameError != null)
                    Text(uiState.lastNameError)
            },
            isError = uiState.lastNameError != null,
            label = {
                Text("lastname")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            modifier = Modifier.fillMaxWidth()
        )

        DatePickerField(
            date = uiState.birthDate,
            label = { Text("birth date") },
            supportingText = {
                if (uiState.birthDateError != null)
                    Text(uiState.birthDateError)
            },
            isError = uiState.birthDateError != null,
            onDateChanged = { year, month, day ->
                when {
                    day < 10 && month < 10 -> onIntent(SignUpIntent.UpdateBirthDate("0$day.0$month.$year"))

                    day < 10 -> onIntent(SignUpIntent.UpdateBirthDate("0$day.$month.$year"))

                    month < 10 -> onIntent(SignUpIntent.UpdateBirthDate("$day.0$month.$year"))

                    else -> onIntent(SignUpIntent.UpdateBirthDate("$day.$month.$year"))
                }
            })

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onIntent(SignUpIntent.UpdatePassword(it)) },
            label = {
                Text("password")
            },
            supportingText = {
                if (uiState.passwordError != null)
                    Text(uiState.passwordError)
            },
            isError = uiState.passwordError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = uiState.passwordConfirm,
            onValueChange = {
                onIntent(
                    SignUpIntent.UpdatePasswordConfirm(
                        repeatedPassword = it,
                        password = uiState.password
                    )
                )
            },
            label = {
                Text("password confirm")
            },
            supportingText = {
                if (uiState.passwordConfirmError != null)
                    Text(uiState.passwordConfirmError)
            },
            isError = uiState.passwordConfirmError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            enabled = uiState.firstName.isNotEmpty() &&
                    uiState.lastName.isNotEmpty() &&
                    uiState.password.isNotEmpty() &&
                    uiState.passwordConfirm.isNotEmpty() &&
                    uiState.birthDate.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onIntent(
                    SignUpIntent.CreateUser {
                        if (it)
                            onSuccess()
                        else
                            Toast.makeText(
                                context,
                                "Check the fields",
                                Toast.LENGTH_LONG
                            ).show()
                    })
            }
        ) {
            if (uiState.isLoading)
                CircularProgressIndicator()
            else
                Text("Sign Up")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerField(
    date: String,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean,
    onDateChanged: (Int, Int, Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYearOfLife: Int, mMonthOfYear: Int, mDayOfMonth: Int ->
            onDateChanged(mYearOfLife, mMonthOfYear + 1, mDayOfMonth)
        }, mYear, mMonth, mDay
    )

    Box {
        OutlinedTextField(
            readOnly = true,
            value = date,
            onValueChange = {},
            supportingText = supportingText,
            isError = isError,
            label = label,
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange, contentDescription = "dateIcon"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = { mDatePickerDialog.show() }),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    Cft_labTheme(darkTheme = false) {
        SignUpScreen(
            onSuccess = {},
            uiState = SignUpViewModel.UiState(),
            onIntent = {}
        )
    }
}
