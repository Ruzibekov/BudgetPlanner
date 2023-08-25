package uz.ruzibekov.budgetplanner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.ruzibekov.budgetplanner.data.local.room.dao.TransactionDao
import uz.ruzibekov.budgetplanner.data.local.room.database.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "app-database"
    ).build()

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao =
        database.transactionDao()
}