package com.example.unittestingphilipp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.unittestingphilipp.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@SmallTest // Unit Test
class ShoppingDaoTest {
    /**
     * from async matters
     * TODO
     *  To make sure that each unit test function doesn't run async
     *  but run on the same thread one action after the other
     *
     *  TODO
     *   What is the meaning if Rule
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao


    // TODO: usage of inMemoryDatabaseBuilder
    // TODO: usage of allowMainThreadQueries


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }


    @After
    fun teardown() {
        database.close()
    }

    /**
     * LiveData is working async so it has a diff way to handle it
     */
    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)

        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems: List<ShoppingItem> = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)

    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem1 = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItem(shoppingItem1)
        dao.deleteShoppingItem(shoppingItem1)
        val allShoppingItems: List<ShoppingItem> = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem1)

    }


    /***
     *  TODO: why we didn't Test the observeAllShoppingItems function
     */

    @Test
    fun observeTotalPriceSum() = runTest {
        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", id = 3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)


        val totalSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalSum).isEqualTo(2 * 10f + 4 * 5.5f)

    }

}