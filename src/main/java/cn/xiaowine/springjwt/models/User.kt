package cn.xiaowine.springjwt.models

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "users",
    uniqueConstraints = [UniqueConstraint(columnNames = ["username"]), UniqueConstraint(columnNames = ["email"])]
)
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var username: @NotBlank @Size(max = 20) String = ""
    var email: @NotBlank @Size(max = 50) @Email String = ""
    var password: @NotBlank @Size(max = 120) String = ""

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = HashSet()

    constructor()
    constructor(username: String, email: String, password: String) {
        this.username = username
        this.email = email
        this.password = password
    }
}
