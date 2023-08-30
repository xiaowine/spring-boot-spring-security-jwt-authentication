package cn.xiaowine.springjwt.entity

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class SignupEntity() {
    @NotBlank
    var username: String = ""
    @Email
    var email: String = ""
    @NotBlank
    lateinit var role: Set<String>
    @NotBlank
    var password: String = ""
    constructor(username: String, email: String, role: Set<String>, password: String) : this() {
        this.username = username
        this.email = email
        this.role = role
        this.password = password
    }
}
