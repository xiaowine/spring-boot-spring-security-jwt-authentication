package cn.xiaowine.springjwt.tools


import cn.xiaowine.springjwt.entity.ResultEntity
import org.springframework.http.ResponseEntity

object ResponseTools {
    fun ok(data: Any?, message: String? = null): ResponseEntity<ResultEntity> {
        return ResponseEntity.ok(ResultEntity(200, data, message ?: "ok"))
    }

    fun badRequest(message: String? = null): ResponseEntity<ResultEntity> {
        return ResponseEntity.badRequest().body(ResultEntity(400, null, message ?: "bad request"))
    }

    fun unauthorized(message: String? = null): ResponseEntity<ResultEntity> {
        return ResponseEntity.status(401).body(ResultEntity(401, null, message ?: "unauthorized"))
    }

    fun forbidden(message: String? = null): ResponseEntity<ResultEntity> {
        return ResponseEntity.status(403).body(ResultEntity(403, null, message ?: "forbidden"))
    }

    fun notFound(message: String? = null): ResponseEntity<ResultEntity> {
        return ResponseEntity.status(404).body(ResultEntity(404, null, message ?: "not found"))
    }

    fun internalServerError(message: String? = null): ResponseEntity<ResultEntity> {
        return ResponseEntity.status(500).body(ResultEntity(500, null, message ?: "internal server error"))
    }
}