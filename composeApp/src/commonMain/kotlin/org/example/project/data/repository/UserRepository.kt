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
        return genericRepository.get(ApiEndpoints.USERS)
    }

    suspend fun getUserById(id: Int): Flow<Resource<User>> {
        return genericRepository.get(ApiEndpoints.userById(id))
    }

    suspend fun createUser(request: CreateUserRequest): Flow<Resource<User>> {
        return genericRepository.post(ApiEndpoints.USERS, request)
    }

    suspend fun updateUser(id: Int, user: User): Flow<Resource<User>> {
        return genericRepository.put(ApiEndpoints.userById(id), user)
    }

    suspend fun deleteUser(id: Int): Flow<Resource<Unit>> {
        return genericRepository.delete(ApiEndpoints.userById(id))
    }
}