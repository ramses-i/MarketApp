package com.riac.marketapp.domain.model

import com.denzcoskun.imageslider.models.SlideModel
import com.riac.marketapp.data.remote.dto.item.Attribute

data class ItemDetail(
    val id: String,
    val title: String,
    val price: String,
    val currency_id: String,
    val pictures: ArrayList<SlideModel>,
    val attributes: List<Attribute>
)