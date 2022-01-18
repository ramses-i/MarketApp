package com.riac.marketapp.data.remote.dto.query

import com.riac.marketapp.domain.model.Item

data class Result(
    val accepts_mercadopago: Boolean,
    val address: Address,
    val attributes: List<Attribute>,
    val available_quantity: String,
    val buying_mode: String,
    val catalog_listing: Boolean,
    val catalog_product_id: String,
    val category_id: String,
    val condition: String,
    val currency_id: String,
    val domain_id: String,
    val id: String,
    val installments: Any,
    val listing_type_id: String,
    val match_score: Any,
    val melicoin: Any,
    val offer_score: Any,
    val offer_share: Any,
    val official_store_id: Any,
    val order_backend: String,
    val original_price: Any,
    val permalink: String,
    val price: String,
    val prices: Prices,
    val sale_price: Any,
    val seller: Seller,
    val seller_address: SellerAddress,
    val shipping: Shipping,
    val site_id: String,
    val sold_quantity: String,
    val stop_time: String,
    val tags: List<String>,
    val thumbnail: String,
    val thumbnail_id: String,
    val title: String,
    val use_thumbnail_id: Boolean,
    val winner_item_id: Any
)

fun Result.toItem(): Item {
    return Item(id, title, price, currency_id, thumbnail)
}
