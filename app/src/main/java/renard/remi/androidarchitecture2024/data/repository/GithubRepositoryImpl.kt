package renard.remi.androidarchitecture2024.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import renard.remi.androidarchitecture2024.data.db.UserGithubRealm
import renard.remi.androidarchitecture2024.data.model.dto.UserGithubDto
import renard.remi.androidarchitecture2024.data.network.ApiService
import renard.remi.androidarchitecture2024.domain.model.DataError
import renard.remi.androidarchitecture2024.domain.model.Result
import renard.remi.androidarchitecture2024.domain.model.UserGithub
import renard.remi.androidarchitecture2024.domain.repository.GithubRepository
import retrofit2.HttpException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val realm: Realm
) : GithubRepository {

    override suspend fun getGithubUser(
        forceRefresh: Boolean,
        username: String
    ): Result<UserGithub, DataError.Network> {
        if (forceRefresh) {
            return getGithubUserFromNetwork(username)
        } else {
            val user = getRealmGithubUser(username = username)
            return if (user != null) {
                Result.Success(user)
            } else {
                getGithubUserFromNetwork(username)
            }
        }
    }

    private suspend fun getGithubUserFromNetwork(username: String): Result<UserGithub, DataError.Network> {
        return try {
            val user = apiService.getGithubUser(username)
            saveRealmGithubUser(user)
            Result.Success(user.toUserGithub())
        } catch (error: Exception) {
            if (error is HttpException) {
                when (error.code()) {
                    404 -> Result.Error(DataError.Network.NOT_FOUND)
                    500 -> Result.Error(DataError.Network.SERVER_ERROR)
                    else -> Result.Error(DataError.Network.UNKNOWN)
                }
            } else {
                Result.Error(DataError.Network.NO_INTERNET)
            }
        }
    }

    private fun getRealmGithubUser(username: String): UserGithub? {
        return realm
            .query<UserGithubRealm>("username ==[c] $0", username).find()
            .map { it.toUserGithub() }
            .firstOrNull()
    }

    private suspend fun saveRealmGithubUser(user: UserGithubDto) {
        realm.write {
            val realmUser = UserGithubRealm().apply {
                this.id = user.id
                this.username = user.login
                this.bio = user.bio
                this.reposUrl = user.repos_url
                this.avatarUrl = user.avatar_url
            }
            copyToRealm(realmUser, UpdatePolicy.ALL)
        }
    }
}