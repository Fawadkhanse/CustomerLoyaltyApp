package org.example.project.domain


import kotlinx.coroutines.flow.Flow
import org.example.project.data.api.HttpMethod
import org.example.project.domain.models.Resource

interface RemoteRepository {
    suspend fun makeApiRequest(
        requestModel: Any? = null,
        endpoint: String,
        httpMethod: HttpMethod = HttpMethod.POST,
        returnErrorBody: Boolean=false,
        isMock: Boolean = false
    ): Flow<Resource<String>>
}
