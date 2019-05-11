package com.syntax.android.github.ui.profile

import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.syntax.android.github.R
import kotlinx.android.synthetic.main.fragment_profile.*


internal fun ProfileFragment.showCompanyDialog(currentCompany: String, callback: (newCompany: String) -> Unit) {
  val dialogBuilder = AlertDialog.Builder(company.context)
  val dialogView = this.layoutInflater.inflate(R.layout.dialog_company, null)
  dialogBuilder.setView(dialogView)

  val companyEditText = dialogView.findViewById(R.id.companyEditText) as EditText
  companyEditText.setText(currentCompany)

  dialogBuilder.setTitle(getString(R.string.company_title))
  dialogBuilder.setPositiveButton(getString(R.string.company_ok), { _, _ ->
    callback(companyEditText.text.toString())
  })
  dialogBuilder.setNegativeButton(getString(R.string.cancel), { _, _ ->
    //pass
  })

  val dialog = dialogBuilder.create()
  dialog.show()
}
