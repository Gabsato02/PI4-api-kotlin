package com.api.api.item

import java.sql.Timestamp

data class Item(
        var id: Int = 0,
        var name: String? = null,
        var price: Int = 0,
        var description: String? = null,
        var volume: String? = null,
        var category_id: Int = 0,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
)