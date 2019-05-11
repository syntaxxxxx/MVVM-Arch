package com.syntax.android.github.ui.gists

import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.syntax.android.github.R
import kotlinx.android.synthetic.main.fragment_gists.*


internal fun GistsFragment.showGistDialog() {
  val dialogBuilder = AlertDialog.Builder(gistsRecyclerView.context)
  val dialogView = this.layoutInflater.inflate(R.layout.dialog_gist, null)
  dialogBuilder.setView(dialogView)

  val description = dialogView.findViewById(R.id.descriptionEditText) as EditText
  val filename = dialogView.findViewById(R.id.filenameEditText) as EditText
  val content = dialogView.findViewById(R.id.contentEditText) as EditText

  dialogBuilder.setTitle(getString(R.string.new_gist))
  dialogBuilder.setPositiveButton(getString(R.string.make_gist), { _, _ ->
    sendGist(description.text.toString(), filename.text.toString(), content.text.toString())
  })
  dialogBuilder.setNegativeButton(getString(R.string.cancel), { _, _ ->
    //pass
  })

  val dialog = dialogBuilder.create()

  dialog.setOnShowListener {
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
  }

  val textChangedListener = object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
      dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = canSendGist(description, filename, content)
    }
    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
  }

  description.addTextChangedListener(textChangedListener)
  filename.addTextChangedListener(textChangedListener)
  content.addTextChangedListener(textChangedListener)

  dialog.show()
}

private fun canSendGist(description: EditText, filename: EditText, content: EditText) =
    !description.text.isNullOrBlank() && !filename.text.isNullOrBlank() && !content.text.isNullOrBlank()