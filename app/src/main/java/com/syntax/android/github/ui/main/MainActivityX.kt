
package com.syntax.android.github.ui.main

import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.syntax.android.github.R
import com.syntax.android.github.model.AuthenticationPrefs


internal fun MainActivity.showUsernameDialog(callback: () -> Unit) {
  val dialogBuilder = AlertDialog.Builder(this)
  val dialogView = this.layoutInflater.inflate(R.layout.dialog_login, null)
  dialogBuilder.setView(dialogView)

  val username = dialogView.findViewById(R.id.usernameEditText) as EditText

  dialogBuilder.setTitle(getString(R.string.username_title))
  dialogBuilder.setPositiveButton(getString(R.string.username_ok), { _, _ ->
    AuthenticationPrefs.saveUsername(username.text.toString())
    callback()
  })
  dialogBuilder.setNegativeButton(getString(R.string.cancel), { _, _ ->
    //pass
  })

  val dialog = dialogBuilder.create()

  dialog.setOnShowListener {
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
  }

  username.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = canSaveUsername(username)
    }

    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
  })

  dialog.show()
}

private fun canSaveUsername(username: EditText) =
    !username.text.isNullOrBlank()