package renard.remi.androidarchitecture2024.data.model.dto

import renard.remi.androidarchitecture2024.domain.model.UserGithub

data class UserGithubDto(
    val id: Int? = null,
    val login: String? = null,
    val avatar_url: String? = null,
    val repos_url: String? = null,
    val bio: String? = null
) {
    fun toUserGithub() = UserGithub(
        id = id,
        username = login,
        avatarUrl = avatar_url,
        reposUrl = repos_url,
        bio = bio
    )
}