package uz.ruzibekov.budgetplanner.utils

import android.widget.EditText

object EditTextHelper {

    fun changeCursorIndex(
        editText: EditText?,
        changedText: String,
        formattedText: String,
        cursorIndex: Int,
        textAdded: Boolean
    ) {

        if(changedText != "0" && formattedText != "0") {

            when {

                //added new character for editText
                textAdded -> {

                    editText?.setSelection(
                        if (changedText.length == formattedText.length)
                            cursorIndex + 1
                        else
                            cursorIndex + 2
                    )
                }

                //removed character from edittext
                textAdded.not() && cursorIndex != editText?.selectionStart -> {

                    editText?.setSelection(
                        if (changedText.length == formattedText.length)
                            cursorIndex
                        else
                            cursorIndex - 1
                    )
                }
            }
        }
    }


}