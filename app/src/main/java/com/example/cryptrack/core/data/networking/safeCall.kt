package com.example.cryptrack.core.data.networking

import com.example.cryptrack.core.domain.util.NetworkError
import com.example.cryptrack.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(
    execute : () -> HttpResponse
): Result <T, NetworkError> {
    val response = try {
        execute()
    }catch (e : UnresolvedAddressException){
        return Result.Error(NetworkError.NO_INTERNET)
    }catch (e : SerializationException){
        return Result.Error(NetworkError.SERIALIZATION_ERROR)
    }catch (e : Exception){
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UNKNOWN_ERROR)
    }

    return responseToResult(response = response)
}