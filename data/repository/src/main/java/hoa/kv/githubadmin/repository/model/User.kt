package hoa.kv.githubadmin.repository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int,
    @SerialName("login")
    val loginName: String,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("html_url")
    val landingPageUrl: String,
    @SerialName("location")
    val location: String? = null,
    @SerialName("followers")
    val followers: Int = 0,
    @SerialName("following")
    val following: Int = 0
) {
    constructor(userEntity: UserEntity) : this(
        id = userEntity.id,
        loginName = userEntity.loginName,
        avatarUrl = userEntity.avatarUrl,
        landingPageUrl = userEntity.landingPageUrl
    )

    constructor(data: Map<String, Any>) : this(
        id = data["id"] as? Int ?: 0,
        loginName = data["login"] as? String ?: "",
        avatarUrl = data["avatar_url"] as? String,
        landingPageUrl = data["html_url"] as? String ?: "",
        location = data["location"] as? String,
        followers = data["followers"] as? Int ?: 0,
        following = data["following"] as? Int ?: 0
    )
}