package com.api.api.user

data class User(
        var id: Int = 0,
        var name: String? = null,
        var email: String? = null,
        var password: String? = null,
        var role: String? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
) {
    fun validate(): String {
        if (name.isNullOrBlank()) return "O nome não deve estar em branco."
        if (email.isNullOrBlank()) return "O e-mail não deve estar em branco."
        if (password.isNullOrBlank()) return "A senha não deve estar em branco."
        if (password!!.length < 8) return "A senha não deve ser menor que 8 caracteres."
        if (role.isNullOrBlank()) return "O Tipo de usuário não deve estar em branco."
        return "OK"
    }
}

