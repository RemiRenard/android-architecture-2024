package renard.remi.androidarchitecture2024.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import renard.remi.androidarchitecture2024.extension.dataStore
import renard.remi.androidarchitecture2024.ui.auth.AuthScreen
import renard.remi.androidarchitecture2024.ui.auth.AuthScreenContent
import renard.remi.androidarchitecture2024.ui.home.HomeScreen
import renard.remi.androidarchitecture2024.ui.home.HomeScreenContent
import renard.remi.androidarchitecture2024.ui.theme.AndroidArchitecture2024Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidArchitecture2024Theme {
                val navController = rememberNavController()
                val context = LocalContext.current

                NavHost(
                    navController = navController,
                    startDestination = if (
                        runBlocking {
                            context.dataStore.data.first().userToken.isNotBlank()
                        }
                    ) HomeScreen() else AuthScreen
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

