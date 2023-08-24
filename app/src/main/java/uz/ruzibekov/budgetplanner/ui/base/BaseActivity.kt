package uz.ruzibekov.budgetplanner.ui.base

import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.walletmanager.data.local.shared_pref.LocalManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var localManager: LocalManager

    fun EditText.onFocus(){
        requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    fun String.getAmountWithoutSpaces(): Long {
        return replace(" ", "").toLong()
    }

    fun changeCurrentAccount(id: Long, activity: AppCompatActivity){
        localManager.currentAccountId = id
        activity.recreate()
    }

    fun showToast(textRes: Int){
        Toast.makeText(this, getString(textRes), Toast.LENGTH_LONG).show()
    }

    fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}