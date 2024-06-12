package renard.remi.androidarchitecture2024.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import renard.remi.androidarchitecture2024.ui.auth.AuthScreen
import renard.remi.androidarchitecture2024.ui.auth.AuthScreenContent
import renard.remi.androidarchitecture2024.ui.home.HomeScreen
import renard.remi.androidarchitecture2024.ui.home.HomeScreenContent
import renard.remi.androidarchitecture2024.ui.theme.AndroidArchitecture2024Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidArchitecture2024Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AuthScreen
                ) {
                    composable<AuthScreen> {
                        AuthScreenContent(
                            navController = navController
                        )
                    }
                    composable<HomeScreen> {
                        val args = it.toRoute<HomeScreen>()
                        HomeScreenContent(
                            navController = navController,
                            args = args
                        )
                    }
                }
            }
        }
    }
}

