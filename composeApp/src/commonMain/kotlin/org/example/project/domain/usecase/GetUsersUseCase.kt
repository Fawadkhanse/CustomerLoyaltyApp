package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.project.data.dto.User
import org.example.project.data.repository.UserRepository
import org.example.project.domain.models.Resource

class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<User>>> {
        return userRepository.getUsers()
    }
}