package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.dto.CreateUserRequest
import org.example.project.data.dto.User
import org.example.project.domain.models.Resource

class UserRepository(
    private val genericRepository: GenericRepository
) {

    suspend fun getUsers(): Flow<Resource<List<User>>> {
        return genericRepository.get(ApiEndpoints.TOKEN)
    }

    suspend fun getUserById(id: Int): Flow<Resource<User>> {
        return genericRepository.get(ApiEndpoints.couponById(""))
    }

    suspend fun createUser(request: CreateUserRequest): Flow<Resource<User>> {
        return genericRepository.post(ApiEndpoints.TOKEN, request)
    }

    suspend fun updateUser(id: Int, user: User): Flow<Resource<User>> {
        return genericRepository.put(ApiEndpoints.couponById(""), user)
    }

    suspend fun deleteUser(id: Int): Flow<Resource<Unit>> {
        return genericRepository.delete(ApiEndpoints.couponById(""))
    }
}