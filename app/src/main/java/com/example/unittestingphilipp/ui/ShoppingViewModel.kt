package com.example.unittestingphilipp.ui

import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittestingphilipp.data.local.ShoppingItem
import com.example.unittestingphilipp.data.remote.response.ImageResponse
import com.example.unittestingphilipp.other.Constants
import com.example.unittestingphilipp.other.Event
import com.example.unittestingphilipp.other.Resource
import com.example.unittestingphilipp.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO: why Event
// TODO: postValue vs setValue

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {
    // TODO: What is that
    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus


    // TODO: why post
    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The fields must not be null",
                        null
                    )
                )
            )
            return
        }

        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The Name Must Not Exceed ${Constants.MAX_NAME_LENGTH}",
                        null
                    )
                )
            )
            return
        }
        if (priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The Price Must Not Exceed ${Constants.MAX_PRICE_LENGTH}",
                        null
                    )
                )
            )
            return
        }

        val amount: Int = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "Please enter a valid amount",
                        null
                    )
                )
            )
            return
        }

        val shoppingItem =
            ShoppingItem(name, amount, priceString.toFloat(), _curImageUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()) {
            return
        }

        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchImage(imageQuery)
            _images.value = Event(response)
        }

    }


}