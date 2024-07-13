package com.example.unittestingphilipp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unittestingphilipp.data.local.ShoppingItem
import com.example.unittestingphilipp.data.remote.response.ImageResponse
import com.example.unittestingphilipp.other.Resource

// TODO: Why this class 
// TODO: Why i didn't put it in the android test source set
// TODO: In this class we don't test the data coming from the network of the db
// TODO: but simulate that and that because we want to test the repository functions not the data of the server or else

class FakeShoppingRepository : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    private fun shouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    // TODO: Very Important
    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }

    }
}