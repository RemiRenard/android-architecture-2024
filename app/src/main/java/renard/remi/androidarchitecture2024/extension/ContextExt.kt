package renard.remi.androidarchitecture2024.extension

import android.content.Context
import androidx.datastore.dataStore
import renard.remi.androidarchitecture2024.data.datastore.AppSettingsSerializer

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)