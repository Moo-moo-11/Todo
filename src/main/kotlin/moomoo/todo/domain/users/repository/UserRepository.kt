package moomoo.todo.domain.users.repository

import moomoo.todo.domain.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
}