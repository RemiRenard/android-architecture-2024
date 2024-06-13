package renard.remi.androidarchitecture2024.data.db

import io.realm.kotlin.types.RealmObject
import renard.remi.androidarchitecture2024.domain.model.UserGithub

class UserGithubRealm : RealmObject {

    var id: Int? = null
    var username: String? = null
    var avatarUrl: String? = null
    var reposUrl: String? = null
    var bio: String? = null

    fun toUserGithub() = UserGithub(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        reposUrl = reposUrl,
        bio = bio
    )
}