package renard.remi.androidarchitecture2024.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import renard.remi.androidarchitecture2024.ui.home.component.AppTextField

@Serializable
data class HomeScreen(
    val username: String? = null
)

@Composable
fun HomeScreenContent(
    navController: NavController,
    args: HomeScreen
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(context) {
        viewModel.events.collect { event ->
            if (event is HomeEventFromVm.Error) {
                Toast.makeText(
                    context,
                    event.errorMessage.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "Welcome ${args.username}")
            Spacer(modifier = Modifier.size(10.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                value = state.usernameGithub,
                label = "Github username",
                isError = state.usernameGithubError?.isNotBlank() == true,
                errorMessage = state.usernameGithubError,
                onValueChange = { viewModel.onEvent(HomeEventFromUI.ChangeUsername(it)) }
            )
            Spacer(modifier = Modifier.size(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.5F),
                onClick = {
                    viewModel.onEvent(HomeEventFromUI.SubmitGithubUser)
                }
            ) {
                Text(text = "Find Github profile")
            }
            Spacer(modifier = Modifier.size(30.dp))
            state.userGithub?.let {
                Text(text = "Name : ${it.username}")
                Text(text = "Bio : ${it.bio}")
            }
        }
    }
}