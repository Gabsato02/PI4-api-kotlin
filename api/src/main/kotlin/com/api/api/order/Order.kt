package com.api.api.order

import com.api.api.item.Item
import com.api.api.store.Store

data class Order (
    var id: Int = 0,
    var store_id: Int = 0,
    var store: Store? = null,
    var user_id: Int = 0,
    var order_items: List<OrderItem>? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var deleted_at: String? = null,
) {
    fun validate(): String {
        if (store_id <= 0) return "ID de loja inválido."
        if (user_id <= 0) return "ID de usuário inválido."
        return "OK"
    }
}

data class OrderItem (
    var id: Int = 0,
    var price: Int = 0,
    var quantity: Int = 0,
    var order_id: Int = 0,
    var item_id: Int = 0,
    var item: Item? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var deleted_at: String? = null,
) {
    fun validate(): String {
        if (price <= 0) return "O preço deve ser maior que 0."
        if (quantity < 1) return "A quantidade deve ser maior que 1."
        if (order_id <= 0) return "ID de pedido inválido."
        if (item_id <= 0) return "ID de item inválido."
        return "OK"
    }
}