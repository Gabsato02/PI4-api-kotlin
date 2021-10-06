package com.api.api.characteristics

data class Characteristic(
        var id: Int = 0,
        var name: String? = null,
        var description: String? = null,
        var characteristics_value: String? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
) {
    fun validate(): String {
        if (name.isNullOrBlank()) return "A caracteristica não deve estar em branco."
        if (characteristics_value.isNullOrBlank()) return "O valor da caracteristica não deve estar em branco."
        return "OK"
    }
}

