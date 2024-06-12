package renard.remi.androidarchitecture2024.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import renard.remi.androidarchitecture2024.data.repository.GithubRepositoryImpl
import renard.remi.androidarchitecture2024.domain.repository.GithubRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGithubRepository(githubRepository: GithubRepositoryImpl): GithubRepository
}