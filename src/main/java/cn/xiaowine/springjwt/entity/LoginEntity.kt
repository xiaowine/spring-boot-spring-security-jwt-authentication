package cn.xiaowine.springjwt.entity

import jakarta.validation.constraints.NotBlank

class LoginEntity() {
    @NotBlank
    var username: String = ""
    @NotBlank
    var password: String = ""

    constructor(username: String, password: String) : this() {
        this.username = username
        this.password = password
    }
}
