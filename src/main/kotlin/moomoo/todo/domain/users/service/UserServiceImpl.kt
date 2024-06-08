package moomoo.todo.domain.users.service

import jakarta.transaction.Transactional
import moomoo.todo.domain.exception.*
import moomoo.todo.domain.users.dto.*
import moomoo.todo.domain.users.model.Address
import moomoo.todo.domain.users.model.Role
import moomoo.todo.domain.users.model.User
import moomoo.todo.domain.users.model.toResponse
import moomoo.todo.domain.users.repository.UserRepository
import moomoo.todo.infra.security.UserPrincipal
import moomoo.todo.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {

    override fun signUp(request: SignUpRequest): UserResponse {
        if (userRepository.existsByUserIdentifier(request.userIdentifier)) throw UserIdentifierAlreadyExistsException(
            request.userIdentifier
        )

        val user = User(
            userIdentifier = request.userIdentifier,
            name = request.name,
            password = passwordEncoder.encode(request.password),
            address = Address(
                request.zipCode, request.streetNameAddress, request.detailedAddress
            ),
            role = when (request.role) {
                "USER" -> Role.USER
                "ADMIN" -> Role.ADMIN
                else -> throw InvalidUserRoleException(request.role)
            }
        )

        return userRepository.save(user).toResponse()

    }

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUserIdentifier(request.userIdentifier) ?: throw UserNotFoundException(
            request.userIdentifier
        )

        if (user.role.name != request.role || !passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(user.id.toString(), user.userIdentifier, user.role.name)
        )
    }

    override fun getUser(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        if (userPrincipal.id !=user.id) throw UnauthorizedAccessException("user info")

        return user.toResponse()
    }

    @Transactional
    override fun updateAddress(userId: Long, request: UpdateAddressRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        if (userPrincipal.id !=user.id) throw UnauthorizedAccessException("user info")

        user.updateAddress(request.zipcode, request.streetNameAddress, request.detailedAddress)

        return user.toResponse()
    }
}