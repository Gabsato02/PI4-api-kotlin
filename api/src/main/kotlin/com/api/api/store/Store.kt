package com.api.api.store

import com.api.api.item.Item

data class Store(
    var id: Int = 0,
    var name: String? = null,
    var description: String? = null,
    var owner_id: Int = 0,
    var items: List<Item>? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var deleted_at: String? = null,
) {
    fun validate(): String {
        if (name.isNullOrBlank()) return "O nome da loja não deve estar em branco."
        if (owner_id <= 0) return "ID inválido."
        return "OK"
    }
}
