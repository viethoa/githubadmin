package hoa.kv.githubadmin.userdetail.utils

import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.usercard.UserCardViewModel

/**
 * Copy [User] data to [UserCardViewModel] data.
 * [UserCardViewModel.landingPageUrl] will be empty as the business logic is not showing LandingPage url
 * in UserCard in Details Screen
 */
internal fun User.toUserCardViewModel(): UserCardViewModel {
    return UserCardViewModel(
        name = this.loginName,
        avatarUrl = this.avatarUrl.orEmpty(),
        location = this.location,
        landingPageUrl = ""
    )
}