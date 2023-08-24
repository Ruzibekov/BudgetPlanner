package uz.ruzibekov.budgetplanner.data.local.shared_pref

import android.content.SharedPreferences
import com.google.gson.Gson
import uz.ruzibekov.budgetplanner.data.local.shared_pref.PreferenceHelper.set

class LocalManager(
    private val preferences: SharedPreferences,
    private val gson: Gson,
) {

    var amount: Long = 0
        get() {
            return preferences.getLong(PREF_AMOUNT, 0)
        }
        set(value) {
            preferences[PREF_AMOUNT] = value
            field = value
        }

    companion object {
        const val PREF_AMOUNT = "pref-amount"
    }
}