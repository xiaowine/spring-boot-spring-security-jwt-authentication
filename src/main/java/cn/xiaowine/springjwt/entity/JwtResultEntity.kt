package cn.xiaowine.springjwt.entity

class JwtResultEntity() {
    var accessToken: String = ""
    var id: Long = 0
    var username: String = ""
    var email: String = ""
    lateinit var roles: List<String>
    var tokenType: String = "Bearer"
    constructor(accessToken: String, id: Long, username: String, email: String, roles: List<String>) : this() {
        this.accessToken = accessToken
        this.id = id
        this.username = username
        this.email = email
        this.roles = roles
    }
}