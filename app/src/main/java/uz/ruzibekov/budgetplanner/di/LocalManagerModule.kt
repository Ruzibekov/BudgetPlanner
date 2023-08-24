package uz.ruzibekov.budgetplanner.di

import android.content.Context
import android.content.SharedPreferences
import uz.ruzibekov.budgetplanner.data.local.shared_pref.LocalManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalManagerModule {

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "wallet-manager",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providesLocalManager(
        @ApplicationContext context: Context,
        preferences: SharedPreferences,
    ): LocalManager {
        return LocalManager(preferences, Gson())
    }
}