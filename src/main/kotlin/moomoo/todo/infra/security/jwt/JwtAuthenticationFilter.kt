package moomoo.todo.infra.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import moomoo.todo.infra.security.UserPrincipal
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt is String) {
            jwtPlugin.validateToken(jwt).onSuccess {
                    val userId = it.payload.subject.toLong()
                    val userIdentifier = it.payload["userIdentifier"] as String
                    val role = it.payload["role"] as String


                    val principal = UserPrincipal(id = userId, userIdentifier = userIdentifier, roles = setOf(role))

                    val authentication =
                        JwtAuthenticationToken(principal, WebAuthenticationDetailsSource().buildDetails(request))

                    SecurityContextHolder.getContext().authentication = authentication
                }
        }

        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return if(headerValue.contains("Bearer ")) headerValue.removePrefix("Bearer ") else null
    }
}