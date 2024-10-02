package hoa.kv.githubadmin.systemdesign.usercard

data class UserCardViewModel(
    val name: String,
    val avatarUrl: String,
    val landingPageUrl: String?,
    val location: String?
)