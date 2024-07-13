package com.example.unittestingphilipp.di

import android.content.Context
import androidx.room.Room
import com.example.unittestingphilipp.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    /**
     * Note: that we didn't use @Singleton for this fun
     * as we need different instance each time for each of the test cases
     */
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}