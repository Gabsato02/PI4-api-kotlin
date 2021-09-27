package com.api.api.item

import com.api.api.category.Category
import com.api.api.characteristics.Characteristic
import com.api.api.trait.Trait

data class Item(
        var id: Int = 0,
        var name: String? = null,
        var price: Int = 0,
        var description: String? = null,
        var volume: String? = null,
        var category_id: Int = 0,
        var category: Category? = null,
        var traits: List<Trait>? = null,
        var characteristics: List<Characteristic>? = null,
        var created_at: String? = null,
        var updated_at: String? = null,
        var deleted_at: String? = null,
)