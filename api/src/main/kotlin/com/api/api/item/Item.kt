package com.api.api.item

import com.api.api.category.Category
import java.sql.Timestamp

data class Item(
        var id: Int = 0,
        var name: String? = null,
        var price: Int = 0,
        var description: String? = null,
        var volume: String? = null,
        var category_id: Int = 0,
        var category: Category? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
)