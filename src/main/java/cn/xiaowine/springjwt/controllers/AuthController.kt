package cn.xiaowine.springjwt.controllers

import cn.xiaowine.springjwt.models.ERole
import cn.xiaowine.springjwt.models.User
import cn.xiaowine.springjwt.entity.LoginEntity
import cn.xiaowine.springjwt.entity.SignupEntity
import cn.xiaowine.springjwt.entity.JwtResultEntity
import cn.xiaowine.springjwt.repository.RoleRepository
import cn.xiaowine.springjwt.repository.UserRepository
import cn.xiaowine.springjwt.tools.JwtTools
import cn.xiaowine.springjwt.services.UserDetailsImpl
import cn.xiaowine.springjwt.tools.ResponseTools
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
    var authenticationManager: AuthenticationManager,
    var userRepository: UserRepository,
    var roleRepository: RoleRepository,
    var encoder: PasswordEncoder,
    var jwtUtils: JwtTools
) {


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody @Validated loginRequest: LoginEntity): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.map { it.authority }
        return ResponseEntity.ok(JwtResultEntity(jwt, userDetails.id, userDetails.username, userDetails.email, roles))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody @Validated signUpRequest: SignupEntity): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseTools.badRequest("Username is already taken!")
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseTools.badRequest("Email is already in use!")
        }

        val roles = signUpRequest.role.map { roleName ->
            roleRepository.findByName(ERole.valueOf("ROLE_${roleName.uppercase()}"))
                ?: throw RuntimeException("Error: Role is not found.")
        }.toSet()
        val user = User(
            signUpRequest.username,
            signUpRequest.email,
            encoder.encode(signUpRequest.password)
        )
        user.roles = roles
        userRepository.save(user)
        return ResponseTools.ok(null, "User registered successfully!")
    }
}
