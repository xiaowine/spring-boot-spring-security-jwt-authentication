package cn.xiaowine.springjwt.point

import cn.xiaowine.springjwt.entity.ResultEntity
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Lazy
@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val resultEntity = ResultEntity().apply {
            message = authException.message ?: "error"
            when (authException.message) {
                "Unauthorized" -> {
                    logger.error("Unauthorized error: {}", authException.message)
                    code = HttpServletResponse.SC_UNAUTHORIZED
                }

                "Bad credentials" -> {
                    logger.error("Bad credentials error: {}", authException.message)
                    code = HttpServletResponse.SC_PAYMENT_REQUIRED
                }

                else -> {
                    logger.error("Error: {}", authException.message)
                    code = HttpServletResponse.SC_BAD_REQUEST
                }
            }
        }
        response.apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            status = resultEntity.code
            ObjectMapper().writeValue(outputStream, resultEntity)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
