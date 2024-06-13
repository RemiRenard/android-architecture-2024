package renard.remi.androidarchitecture2024.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import renard.remi.androidarchitecture2024.domain.model.Result
import renard.remi.androidarchitecture2024.domain.use_case.GithubUseCases
import renard.remi.androidarchitecture2024.extension.UiText
import renard.remi.androidarchitecture2024.extension.asUiText
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: GithubUseCases
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    private val eventChannel = Channel<HomeEventFromVm>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: HomeEventFromUI) {
        when (event) {
            is HomeEventFromUI.ChangeUsername -> validateUsernameField(event.username)
            is HomeEventFromUI.SubmitGithubUser -> getGithubUser(state.usernameGithub)
        }
    }

    private fun validateUsernameField(username: String): Boolean {
        state = state.copy(usernameGithub = username)
        val usernameResult = useCases.validateUsername(state.usernameGithub)
        if (!usernameResult.successful) {
            usernameResult.errorMessage?.let {
                state = state.copy(usernameGithubError = it)
            }
        } else {
            state = state.copy(usernameGithubError = null)
        }
        return usernameResult.successful
    }

    private fun getGithubUser(username: String) {
        if (!validateUsernameField(username)) {
            return
        }
        viewModelScope.launch {
            when (val result = useCases.getGithubUser(false, username)) {
                is Result.Error -> {
                    state = state.copy(userGithub = null)
                    val errorMessage = result.error.asUiText()
                    eventChannel.send(HomeEventFromVm.Error(errorMessage))
                }

                is Result.Success -> {
                    state = state.copy(userGithub = result.data)
                }
            }
        }
    }
}

sealed interface HomeEventFromUI {
    data object SubmitGithubUser : HomeEventFromUI
    data class ChangeUsername(val username: String) : HomeEventFromUI
}

sealed interface HomeEventFromVm {
    data class Error(val errorMessage: UiText) : HomeEventFromVm
}

