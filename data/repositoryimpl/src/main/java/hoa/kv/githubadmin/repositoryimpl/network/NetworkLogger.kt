package hoa.kv.githubadmin.repositoryimpl.network

import hoa.kv.githubadmin.repositoryimpl.BuildConfig
import io.ktor.client.plugins.logging.Logger

class NetworkLogger : Logger {
    override fun log(message: String) {
        if (BuildConfig.DEBUG) {
            println(message)
        }
    }
}