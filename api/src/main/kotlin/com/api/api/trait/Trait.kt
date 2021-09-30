package com.api.api.trait

data class Trait(
        var id: Int? = null,
        var name: String? = null,
        var description: String? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
) {
    fun validate(): Boolean {
        if (name.isNullOrBlank()) return false
        return true
    }
}
