package renard.remi.androidarchitecture2024.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import renard.remi.androidarchitecture2024.ui.home.HomeScreen

@Serializable
object AuthScreen

@Composable
fun AuthScreenContent(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Fake Auth Screen")
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                navController.navigate(
                    HomeScreen(
                        username = "Rémi Renard"
                    )
                )
            }
        ) {
            Text(text = "Go to Home screen")
        }
    }
}