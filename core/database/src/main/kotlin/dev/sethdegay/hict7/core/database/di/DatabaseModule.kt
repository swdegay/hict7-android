package dev.sethdegay.hict7.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sethdegay.hict7.core.database.Hict7Database
import dev.sethdegay.hict7.core.database.WorkoutPreloadCallback
import dev.sethdegay.hict7.core.database.util.AppTransactionProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesHict7Database(@ApplicationContext context: Context): Hict7Database =
        Room.databaseBuilder(
            context = context,
            klass = Hict7Database::class.java,
            name = "data.db",
        )
            .addCallback(WorkoutPreloadCallback(scope = CoroutineScope(Dispatchers.IO)))
            .build()

    @Provides
    @Singleton
    fun providesAppTransactionProvider(database: Hict7Database): AppTransactionProvider =
        AppTransactionProvider(database)
}