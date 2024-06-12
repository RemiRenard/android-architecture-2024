package renard.remi.androidarchitecture2024.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import renard.remi.androidarchitecture2024.domain.repository.GithubRepository
import renard.remi.androidarchitecture2024.domain.use_case.GithubUseCases
import renard.remi.androidarchitecture2024.domain.use_case.ValidateUsername
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGithubUseCases(
        githubRepository: GithubRepository
    ): GithubUseCases {
        return GithubUseCases(
            githubRepository = githubRepository,
            validateUsername = ValidateUsername()
        )
    }
}