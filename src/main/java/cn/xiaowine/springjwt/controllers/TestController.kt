package cn.xiaowine.springjwt.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {
    @GetMapping("/all")
    fun allAccess(): String {
        return "Public Content."
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    fun userAccess(): String {
        return "User Content."
    }


    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/mod")
    @PreAuthorize("hasRole('ADMIN')")
    fun moderatorAccess(): String {
        return "Moderator Board."
    }
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "Admin Board."
    }
}
