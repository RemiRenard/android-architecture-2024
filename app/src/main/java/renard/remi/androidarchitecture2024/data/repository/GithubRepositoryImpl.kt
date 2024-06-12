package renard.remi.androidarchitecture2024.data.repository

import renard.remi.androidarchitecture2024.data.network.ApiService
import renard.remi.androidarchitecture2024.domain.model.DataError
import renard.remi.androidarchitecture2024.domain.model.Result
import renard.remi.androidarchitecture2024.domain.model.UserGithub
import renard.remi.androidarchitecture2024.domain.repository.GithubRepository
import retrofit2.HttpException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GithubRepository {

    override suspend fun getGithubUser(username: String): Result<UserGithub, DataError.Network> {
        return try {
            val user = apiService.getGithubUser(username)
            Result.Success(user.toUserGithub())
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> Result.Error(DataError.Network.NOT_FOUND)
                500 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}