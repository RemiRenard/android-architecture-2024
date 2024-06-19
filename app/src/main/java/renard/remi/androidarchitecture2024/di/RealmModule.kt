package renard.remi.androidarchitecture2024.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import renard.remi.androidarchitecture2024.data.db.UserGithubRealm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    UserGithubRealm::class
                )
            )
        )
        return realm
    }
}