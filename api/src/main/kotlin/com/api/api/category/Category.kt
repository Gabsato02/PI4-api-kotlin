package com.api.api.category

import com.api.api.item.Item

data class Category (
        var id: Int = 0,
        var name: String? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
        var items: List<Item>? = null
)