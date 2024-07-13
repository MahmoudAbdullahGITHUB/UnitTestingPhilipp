package com.example.unittestingphilipp.repositories

import androidx.lifecycle.LiveData
import com.example.unittestingphilipp.data.local.ShoppingItem
import com.example.unittestingphilipp.data.remote.response.ImageResponse
import com.example.unittestingphilipp.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchImage(imageQuery:String):Resource<ImageResponse>


}