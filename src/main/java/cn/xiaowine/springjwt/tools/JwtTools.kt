package cn.xiaowine.springjwt.tools

import cn.xiaowine.springjwt.services.UserDetailsImpl
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTools {
    @Value("\${bezkoder.app.jwtSecret}")
    private val jwtSecret: String = ""

    @Value("\${bezkoder.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0
    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl
        return Jwts.builder().apply {
            setSubject(userPrincipal.username)
            setIssuedAt(Date())
            setExpiration(Date(Date().time + jwtExpirationMs))
            signWith(key(), SignatureAlgorithm.HS256)
        }.compact()
    }


    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).body.subject
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).body.expiration.before(Date())
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken)
            return true
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: ${e.message}")
        }
        return false
    }

    private fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
