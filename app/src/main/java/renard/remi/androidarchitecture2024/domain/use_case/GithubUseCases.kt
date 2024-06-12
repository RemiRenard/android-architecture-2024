package renard.remi.androidarchitecture2024.domain.use_case

import renard.remi.androidarchitecture2024.domain.repository.GithubRepository

data class GithubUseCases(
    private val githubRepository: GithubRepository,
    private val validateUsername: ValidateUsername,
) {
    suspend fun getGithubUser(username: String?) = githubRepository.getGithubUser(username ?: "")

    fun validateUsername(username: String?): ValidationResult = validateUsername.execute(username)
}

class ValidateUsername {
    fun execute(username: String?): ValidationResult {
        if (username.isNullOrBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The username can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)