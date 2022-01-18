package com.riac.marketapp.data.remote.dto.item

import com.denzcoskun.imageslider.models.SlideModel
import com.riac.marketapp.domain.model.ItemDetail

data class ItemDto(
    val accepts_mercadopago: Boolean,
    val attributes: List<Attribute>,
    val automatic_relist: Boolean,
    val available_quantity: String,
    val base_price: String,
    val buying_mode: String,
    val catalog_listing: Boolean,
    val catalog_product_id: String,
    val category_id: String,
    val channels: List<String>,
    val condition: String,
    val coverage_areas: List<Any>,
    val currency_id: String,
    val date_created: String,
    val deal_ids: List<Any>,
    val descriptions: List<Any>,
    val differential_pricing: Any,
    val domain_id: String,
    val health: Any,
    val id: String,
    val initial_quantity: String,
    val international_delivery_mode: String,
    val last_updated: String,
    val listing_source: String,
    val listing_type_id: String,
    val location: Location,
    val non_mercado_pago_payment_methods: List<Any>,
    val official_store_id: Any,
    val original_price: Any,
    val parent_item_id: Any,
    val permalink: String,
    val pictures: List<Picture>,
    val price: String,
    val sale_terms: List<SaleTerm>,
    val secure_thumbnail: String,
    val seller_address: SellerAddress,
    val seller_contact: Any,
    val seller_id: String,
    val shipping: Shipping,
    val site_id: String,
    val sold_quantity: String,
    val start_time: String,
    val status: String,
    val stop_time: String,
    val sub_status: List<Any>,
    val subtitle: Any,
    val tags: List<String>,
    val thumbnail: String,
    val thumbnail_id: String,
    val title: String,
    val variations: List<Any>,
    val video_id: Any,
    val warnings: List<Any>,
    val warranty: String
)

/**
 * I DO NOT NEED ALL OF THE PRODUCT INFO SO I WILL JUST PASS IT TO MY MAIN MODEL CLASS
 * THAT HAS ALL THE NECESSARY INFORMATION FOR THE VISUAL REPRESENTATION, AND ALSO
 * KEEPING THE ID IN CASE WE WANT TO KNOW MORE INFORMATION LATER ON, WE CAN JUST
 * FETCH THAT INFO FROM THERE.
 */
fun ItemDto.toItemDetail(): ItemDetail {
    val imagesLinks = arrayListOf<SlideModel>()
    pictures.forEach {
        imagesLinks.add(SlideModel(it.secure_url))
    }
    return ItemDetail(id, title, price, currency_id, imagesLinks, attributes)
}

