package cn.xiaowine.springjwt.models

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    lateinit var name: ERole
}