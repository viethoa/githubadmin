package hoa.kv.githubadmin.landing.utils

import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.usercard.UserCardViewModel

/**
 * Copy [User] data to [UserCardViewModel] data.
 * [UserCardViewModel.location] will be empty
 * as the business logic is not showing location in Main Screen
 */
internal fun User.toUserCardViewModel(): UserCardViewModel {
    return UserCardViewModel(
        name = this.loginName,
        avatarUrl = this.avatarUrl.orEmpty(),
        landingPageUrl = this.landingPageUrl,
        location = ""
    )
}