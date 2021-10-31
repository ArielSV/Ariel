package com.example.ariel.base.activitie

import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.ariel.R
import com.example.ariel.base.interfaces.AndroidProvider
import com.example.ariel.utils.removeFromParent
import com.example.ariel.views.LoadingView

open class BaseFragmentActivity : AppCompatActivity(), AndroidProvider {

    private val loadingView by lazy { LoadingView(this) }

    private fun showProgressDialog(message: String) {
        loadingView.setMessage(message)
        if (loadingView.parent == null) {
            addContentView(
                loadingView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    private fun showProgressDialog(@StringRes idMessage: Int) =
        showProgressDialog(getString(idMessage))

    override fun showError(message: String) {
        dismissProgressDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        dismissProgressDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() = showProgressDialog(R.string.loading_copy)

    override fun dismissProgressDialog() = loadingView.removeFromParent()

    override fun showLoading(show: Boolean) {
        if (show) showProgressDialog() else dismissProgressDialog()
    }

    override fun backToolbar() = onBackPressed()
}