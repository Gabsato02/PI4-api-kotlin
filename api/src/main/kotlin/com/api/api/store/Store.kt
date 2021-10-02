package com.api.api.store

import com.api.api.item.Item

data class Store(
    var id: Int = 0,
    var name: String? = null,
    var description: String? = null,
    var owner_id: Int? = null,
    var items: List<Item>? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var deleted_at: String? = null,
)
