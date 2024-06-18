package renard.remi.androidarchitecture2024.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val useDynamicColors: Boolean = true,
    val userToken: String = ""
)