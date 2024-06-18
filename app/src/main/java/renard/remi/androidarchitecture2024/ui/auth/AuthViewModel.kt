package renard.remi.androidarchitecture2024.ui.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import renard.remi.androidarchitecture2024.domain.model.Result
import renard.remi.androidarchitecture2024.domain.use_case.GithubUseCases
import renard.remi.androidarchitecture2024.extension.UiText
import renard.remi.androidarchitecture2024.extension.asUiText
import renard.remi.androidarchitecture2024.extension.dataStore
import renard.remi.androidarchitecture2024.ui.home.HomeEventFromVm
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(AuthState())

    private val eventChannel = Channel<AuthEventFromVm>()
    val events = eventChannel.receiveAsFlow()

    suspend fun updateUserToken(context: Context) = viewModelScope.launch {
        context.dataStore.updateData {
            it.copy(
                userToken = "rndtoken"
            )
        }
        eventChannel.send(AuthEventFromVm.RedirectionSuccess)
    }
}

sealed interface AuthEventFromVm {
    data object RedirectionSuccess : AuthEventFromVm
}
