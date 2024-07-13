package com.example.unittestingphilipp.ui

 import androidx.arch.core.executor.testing.InstantTaskExecutorRule
 import com.example.unittestingphilipp.MainCoroutineRule
 import com.example.unittestingphilipp.getOrAwaitValueTest
import com.example.unittestingphilipp.other.Constants
import com.example.unittestingphilipp.other.Status
import com.example.unittestingphilipp.repositories.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
 import kotlinx.coroutines.ExperimentalCoroutinesApi
 import org.junit.Before
import org.junit.Rule
import org.junit.Test



@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule  = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }


    @Test
    fun `insert shopping item with empty field, returns error`() {
        viewModel.insertShoppingItem("name", "", "3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem(string, "5", "3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem("name", "5", string)
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {
        viewModel.insertShoppingItem("name", "99999999999999999", "3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns success`() {

        viewModel.insertShoppingItem("name", "5", "3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }


}