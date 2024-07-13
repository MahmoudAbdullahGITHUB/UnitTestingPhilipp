package com.example.unittestingphilipp.repositories

import androidx.lifecycle.LiveData
import com.example.unittestingphilipp.data.local.ShoppingDao
import com.example.unittestingphilipp.data.local.ShoppingItem
import com.example.unittestingphilipp.data.remote.PixabayAPI
import com.example.unittestingphilipp.data.remote.response.ImageResponse
import com.example.unittestingphilipp.other.Resource
import javax.inject.Inject

// TODO: what is test double
class DefaultShoppingRepository
@Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI,
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {

        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return try {
             val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An Error occured", null)
            } else
                Resource.error("An Error occured", null)

        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}