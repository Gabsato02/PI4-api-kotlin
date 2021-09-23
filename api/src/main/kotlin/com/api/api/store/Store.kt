package com.api.api.store

data class Store(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var owner_id: Int? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var deleted_at: String? = null,
)
