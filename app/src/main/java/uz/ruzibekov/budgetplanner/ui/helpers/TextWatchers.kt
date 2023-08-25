package uz.ruzibekov.budgetplanner.ui.helpers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import uz.ruzibekov.budgetplanner.utils.EditTextHelper
import uz.ruzibekov.budgetplanner.utils.TextFormatter

class TextWatchers(editText: EditText?) {

    val etAmountTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            editText?.removeTextChangedListener(this)

            if (p0?.length == 1) {
                editText?.setText("0")
                editText?.setSelection(1)
            }

            editText?.addTextChangedListener(this)
        }

        override fun onTextChanged(
            text: CharSequence?,
            selectionIndex: Int,
            removed: Int,
            added: Int
        ) {
            if (text.toString().isNotBlank()) {

                if (text.toString()[0] == '0' && text.toString().length > 1) {

                    editText?.removeTextChangedListener(this)

                    editText?.setText(text.toString()[1].toString())
                    editText?.setSelection(1)

                    editText?.addTextChangedListener(this)

                } else {

                    editText?.removeTextChangedListener(this)

                    val num = text.toString().getAmountWithoutSpaces()
                    val formattedText = TextFormatter.spaceBetween3Numbers(num)

                    editText?.setText(formattedText)

                    EditTextHelper.changeCursorIndex(
                        editText = editText,
                        changedText = text.toString(),
                        formattedText = formattedText,
                        cursorIndex = selectionIndex,
                        textAdded = added == 1
                    )

                    editText?.addTextChangedListener(this)
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    private fun String.getAmountWithoutSpaces(): Long {
        return replace(" ", "").toLong()
    }
}