package com.api.api.trait

data class Trait(
        var id: Int = 0,
        var name: String? = null,
        var description: String? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
) {
    fun validate(): String {
        if (name.isNullOrBlank()) return "O traço não deve estar em branco."
        return "OK"
    }
}
