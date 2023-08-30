package cn.xiaowine.springjwt.entity

class ResultEntity() {
    var code: Int = 0
    var data: Any? = null
    lateinit var message: String
    val time: Long = System.currentTimeMillis()

    init {
        if (data == null) {
            this.data = HashMap<String, Any>()
        }
    }
    constructor(code: Int, data: Any?, message: String) : this() {
        this.code = code
        this.data = data
        this.message = message
    }
}
