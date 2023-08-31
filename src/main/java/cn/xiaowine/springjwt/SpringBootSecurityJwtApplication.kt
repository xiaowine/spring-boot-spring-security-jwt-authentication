package cn.xiaowine.springjwt

import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.Environment

@SpringBootApplication
class SpringBootSecurityJwtApplication

fun main() {
    val logger = LoggerFactory.getLogger(SpringBootSecurityJwtApplication::class.java)
    logger.info("开始启动WEB服务器")
    val app = SpringApplication(SpringBootSecurityJwtApplication::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    val context = app.run()
    val environment = context.getBean(Environment::class.java)
    var host = environment.getProperty("server.address")
    if (host == "0.0.0.0") host = "127.0.0.1"
    val port = environment.getProperty("server.port")
    @Suppress("HttpUrlsUsage")
    logger.info("访问地址：http://$host:$port")
    logger.info("OpenAPI地址：http://$host:$port/swagger-ui/index.html#/")
}