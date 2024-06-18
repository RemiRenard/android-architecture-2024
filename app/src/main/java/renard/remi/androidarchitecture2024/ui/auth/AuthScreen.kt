package renard.remi.androidarchitecture2024.ui.auth

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import renard.remi.androidarchitecture2024.ui.home.HomeScreen
import renard.remi.androidarchitecture2024.ui.utils.BiometricPromptManager
import renard.remi.androidarchitecture2024.ui.utils.BiometricPromptManager.BiometricResult

@Serializable
object AuthScreen

@Composable
fun AuthScreenContent(
    navController: NavController
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<AuthViewModel>()
    val promptManager = BiometricPromptManager(activity = context as FragmentActivity)
    val biometricResult by promptManager.promptResults.collectAsState(
        initial = null
    )
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity result: $it")
        }
    )

    LaunchedEffect(context) {
        promptManager.showBiometricPrompt(
            title = "Fingerprint",
            description = "Please verify your identity"
        )
        viewModel.events.collect { event ->
            if (event is AuthEventFromVm.RedirectionSuccess) {
                navController.navigate(HomeScreen())
            }
        }
    }

    LaunchedEffect(biometricResult) {
        biometricResult?.let { result ->
            when (result) {
                is BiometricResult.AuthenticationError -> {
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }

                BiometricResult.AuthenticationFailed -> {
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }

                BiometricResult.AuthenticationNotSet -> {
                    Toast.makeText(context, "Authentication not set", Toast.LENGTH_SHORT).show()
                    if (Build.VERSION.SDK_INT >= 30) {
                        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                            )
                        }
                        enrollLauncher.launch(enrollIntent)
                    }
                }

                BiometricResult.AuthenticationSuccess -> {
                    viewModel.updateUserToken(context)
                }

                BiometricResult.FeatureUnavailable -> {
                    Toast.makeText(context, "Feature unavailable", Toast.LENGTH_SHORT).show()
                }

                BiometricResult.HardwareUnavailable -> {
                    Toast.makeText(context, "Hardware unavailable", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                promptManager.showBiometricPrompt(
                    title = "Fingerprint",
                    description = "Please verify your identity"
                )
            }
        ) {
            Text(text = "Login")
        }
    }
}