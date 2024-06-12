package renard.remi.androidarchitecture2024.ui.home

import renard.remi.androidarchitecture2024.domain.model.UserGithub

data class HomeState(
    val usernameGithub: String = "",
    val usernameGithubError: String? = null,
    val userGithub: UserGithub? = null
)