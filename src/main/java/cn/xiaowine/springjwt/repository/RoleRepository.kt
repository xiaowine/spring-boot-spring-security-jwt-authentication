package cn.xiaowine.springjwt.repository

import cn.xiaowine.springjwt.models.ERole
import cn.xiaowine.springjwt.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: ERole?): Role?
}
