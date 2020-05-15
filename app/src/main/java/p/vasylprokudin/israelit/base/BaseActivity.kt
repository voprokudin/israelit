package p.vasylprokudin.israelit.base

import androidx.appcompat.app.ActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_toolbar.toolbar

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initViews()
    }

    private fun initViews() {
        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener { onToolbarNavigateUp() }
        configureActionBar(supportActionBar)
    }

    private fun configureActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun onToolbarNavigateUp() {
        onBackPressed()
    }
}
