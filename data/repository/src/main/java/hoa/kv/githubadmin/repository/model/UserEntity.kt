package hoa.kv.githubadmin.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "login") val loginName: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    @ColumnInfo(name = "html_url") val landingPageUrl: String,
    @ColumnInfo(name = "rank") val rank: Int,
) {
    constructor(user: User, rank: Int) : this(
        id = user.id,
        loginName = user.loginName,
        avatarUrl = user.avatarUrl,
        landingPageUrl = user.landingPageUrl,
        rank = rank
    )
}