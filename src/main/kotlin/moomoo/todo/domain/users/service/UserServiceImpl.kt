package moomoo.todo.domain.users.service

import moomoo.todo.domain.exception.ModelNotFoundException
import moomoo.todo.domain.users.dto.*
import moomoo.todo.domain.users.model.Address
import moomoo.todo.domain.users.model.toResponse
import moomoo.todo.domain.users.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun signUp(request: SignUpRequest): UserResponse {
        TODO()
    }

    override fun getUser(userId: Long): UserResponse {
        TODO()
    }

    override fun login(request: LoginRequest): LoginResponse {
        TODO()
    }

    override fun updateAddress(userId:Long, request: UpdateAddressRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        user.address = Address(request.zipcode,request.streetNameAddress, request.detailedAddress)

        return user.toResponse()
    }
}