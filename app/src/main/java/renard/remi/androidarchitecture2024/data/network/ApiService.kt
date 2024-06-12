package renard.remi.androidarchitecture2024.data.network

import renard.remi.androidarchitecture2024.data.model.dto.UserGithubDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{username}")
    suspend fun getGithubUser(@Path("username") username: String): UserGithubDto
}