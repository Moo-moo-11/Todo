package moomoo.todo.domain.users.service

import moomoo.todo.domain.users.dto.*

interface UserService {
    fun signUp(request: SignUpRequest): UserResponse

    fun getUser(userId: Long): UserResponse

    fun login(request: LoginRequest): LoginResponse

    fun updateAddress(userId:Long, request: UpdateAddressRequest): UserResponse
}