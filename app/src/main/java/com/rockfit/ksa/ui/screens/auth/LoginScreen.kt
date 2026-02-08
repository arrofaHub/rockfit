package com.rockfit.ksa.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import com.rockfit.ksa.R
import com.rockfit.ksa.BuildConfig
import com.rockfit.ksa.data.remote.AuthRepositorySupabase
import com.rockfit.ksa.model.UserRole
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(onSignInSuccess: (String, UserRole) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.Coach) }
    var error by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepositorySupabase() }
    val invalidCreds = stringResource(id = R.string.invalid_credentials)
    val resetSent = stringResource(id = R.string.reset_email_sent)
    val resetFailed = stringResource(id = R.string.reset_email_failed)
    val signUpSuccess = stringResource(id = R.string.sign_up_success)
    val signUpFailed = stringResource(id = R.string.sign_up_failed)
    val enterEmail = stringResource(id = R.string.enter_email)
    val enterPassword = stringResource(id = R.string.enter_password)

    fun errorMessageOrFallback(ex: Exception, fallback: String): String {
        val message = ex.message?.trim().orEmpty()
        return if (message.isBlank()) fallback else message
    }

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.displayLarge)
        Text(text = stringResource(id = R.string.app_tagline), style = MaterialTheme.typography.bodyLarge)

        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    val ltrStyle = TextStyle(textDirection = TextDirection.Ltr)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(id = R.string.email)) },
                        singleLine = true,
                        textStyle = ltrStyle,
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Next
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(id = R.string.password)) },
                        singleLine = true,
                        textStyle = ltrStyle,
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }

                Text(text = stringResource(id = R.string.role_label), style = MaterialTheme.typography.labelLarge)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    UserRole.values().forEach { role ->
                        val selected = role == selectedRole
                        FilterChip(
                            selected = selected,
                            onClick = { selectedRole = role },
                            label = { Text(text = stringResource(id = roleLabelRes(role))) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF1E3A8A),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                if (error != null) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
                }
                if (message != null) {
                    Text(text = message ?: "", color = MaterialTheme.colorScheme.primary)
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            try {
                                if (email.isBlank() || password.isBlank()) {
                                    error = if (email.isBlank()) enterEmail else enterPassword
                                    return@launch
                                }
                                if (email.trim().equals(BuildConfig.ADMIN_EMAIL, ignoreCase = true) &&
                                    password == BuildConfig.ADMIN_PASSWORD
                                ) {
                                    error = null
                                    message = null
                                    onSignInSuccess(email.trim(), selectedRole)
                                    return@launch
                                }
                                authRepository.signIn(email, password)
                                error = null
                                message = null
                                onSignInSuccess(email.trim(), selectedRole)
                            } catch (ex: Exception) {
                                error = errorMessageOrFallback(ex, invalidCreds)
                            }
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.sign_in))
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                try {
                                    if (email.isBlank()) {
                                        error = enterEmail
                                        return@launch
                                    }
                                    authRepository.resetPassword(email)
                                    message = resetSent
                                    error = null
                                } catch (ex: Exception) {
                                    error = errorMessageOrFallback(ex, resetFailed)
                                }
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.forgot_password))
                    }
                    TextButton(
                        onClick = {
                            scope.launch {
                                try {
                                    if (email.isBlank() || password.isBlank()) {
                                        error = if (email.isBlank()) enterEmail else enterPassword
                                        return@launch
                                    }
                                    authRepository.signUp(email, password)
                                    message = signUpSuccess
                                    error = null
                                } catch (ex: Exception) {
                                    error = errorMessageOrFallback(ex, signUpFailed)
                                }
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.create_account))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(id = R.string.admin_hint), style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

private fun roleLabelRes(role: UserRole): Int {
    return when (role) {
        UserRole.Athlete -> R.string.role_athlete
        UserRole.Coach -> R.string.role_coach
        UserRole.Medical -> R.string.role_medical
        UserRole.Federation -> R.string.role_federation
    }
}
