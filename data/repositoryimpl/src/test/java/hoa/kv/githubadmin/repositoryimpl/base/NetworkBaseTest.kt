package hoa.kv.githubadmin.repositoryimpl.base

import hoa.kv.githubadmin.repository.utils.ResourceUtils
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import java.io.IOException

abstract class NetworkBaseTest {

    fun getMockEngine(resourceFileName: String) = MockEngine {
        respond(
            ResourceUtils.getMockData(resourceFileName),
            HttpStatusCode.OK,
            headersOf("Content-Type", ContentType.Application.Json.toString())
        )
    }

    fun mockHttpExceptionEngine() = MockEngine {
        respond(
            "",
            HttpStatusCode.InternalServerError,
            headersOf("Content-Type", ContentType.Application.Json.toString())
        )
    }

    fun mockIOExceptionEngine() = MockEngine { throw IOException("Simulated IOException") }

    fun MockRequestHandleScope.respond(resourceFileName: String): HttpResponseData {
        return respond(
            content = ResourceUtils.getMockData(resourceFileName),
            status = HttpStatusCode.OK,
            headers = headersOf("Content-Type", ContentType.Application.Json.toString())
        )
    }

    fun MockRequestHandleScope.badRequest(): HttpResponseData {
        return respond(
            content = "",
            status = HttpStatusCode.BadRequest,
            headers = headersOf("Content-Type", ContentType.Application.Json.toString())
        )
    }
}