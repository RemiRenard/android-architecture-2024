package renard.remi.androidarchitecture2024

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    // Initialize the realm database to prevent a minor blink.
    lateinit var realm: Realm
}