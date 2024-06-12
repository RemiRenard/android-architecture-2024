package renard.remi.androidarchitecture2024.domain.repository

import renard.remi.androidarchitecture2024.domain.model.DataError
import renard.remi.androidarchitecture2024.domain.model.Result
import renard.remi.androidarchitecture2024.domain.model.UserGithub

interface GithubRepository {

    suspend fun getGithubUser(username: String): Result<UserGithub, DataError.Network>
}