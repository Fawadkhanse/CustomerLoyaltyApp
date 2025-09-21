package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.project.data.dto.CreateUserRequest
import org.example.project.data.dto.User
import org.example.project.data.repository.UserRepository
import org.example.project.domain.models.Resource

class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        username: String,
        email: String
    ): Flow<Resource<User>> {
        val request = CreateUserRequest(
            name = name,
            username = username,
            email = email
        )
        return userRepository.createUser(request)
    }
}