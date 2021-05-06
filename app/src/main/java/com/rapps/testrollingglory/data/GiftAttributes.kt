package com.rapps.testrollingglory.data

data class GiftAttributes(
    val description:String,
    val id:Int,
    val images:ArrayList<String>,
    val info:String,
    val isNew:Int,
    val isWishlist:Int,
    val name:String,
    val numOfReviews:Int,
    val points:Int,
    val rating:Float,
    val slug:String,
    val stock:Int
)
