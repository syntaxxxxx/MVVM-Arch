package com.syntax.android.github.ui.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.syntax.android.github.R
import com.syntax.android.github.model.ApiError
import com.syntax.android.github.model.Either
import com.syntax.android.github.model.Status
import com.syntax.android.github.model.User
import com.syntax.android.github.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

  private lateinit var profileViewModel: ProfileViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_profile, container, false)

    profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    profileViewModel.getUser().observe(this, observer)

    val onClickListener = View.OnClickListener {
      showCompanyDialog(company.text.toString()) { newCompany ->
        profileViewModel.updateUser(newCompany).observe(this, observer)
      }
    }

    login.setOnClickListener(onClickListener)
    company.setOnClickListener(onClickListener)
  }

  private val observer = Observer<Either<User>> { either ->
    if (either?.status == Status.SUCCESS && either.data != null) {
      val user = either.data
      login.text = String.format(getString(R.string.screen_name_format), user.login)
      repoName.text = user.name
      company.text = user.company
      Picasso.with(context).load(user.avatarUrl).into(avatar)
    } else {
      if (either?.error == ApiError.USER) {
        Toast.makeText(context, getString(R.string.error_retrieving_user), Toast.LENGTH_SHORT).show()
      }
    }
  }
}