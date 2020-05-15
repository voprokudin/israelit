package p.vasylprokudin.israelit.presentation.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_home.progressBarLoadMore
import kotlinx.android.synthetic.main.activity_home.rvItems
import kotlinx.android.synthetic.main.layout_toolbar.toolbarProgressBar
import p.vasylprokudin.israelit.R
import p.vasylprokudin.israelit.base.BaseActivity
import p.vasylprokudin.israelit.data.model.RawListing.RawData.RawChild
import p.vasylprokudin.israelit.extensions.obtainViewModel
import p.vasylprokudin.israelit.presentation.view.list.adapter.ListItemsAdapter
import p.vasylprokudin.israelit.presentation.view.list.listener.ItemClickedListener
import p.vasylprokudin.israelit.presentation.viewmodel.HomeActivityViewModel
import javax.inject.Inject
import p.vasylprokudin.israelit.presentation.viewmodel.HomeActivityViewModel.ScreenState.ShowGeneralError
import p.vasylprokudin.israelit.presentation.viewmodel.HomeActivityViewModel.ScreenState.FetchEntries

class HomeActivity : BaseActivity(), ItemClickedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<HomeActivityViewModel>(viewModelFactory) }

    private val listItemsAdapter = ListItemsAdapter()

    private var pastVisibleItems = 0

    private var visibleItemCount = 0

    private var totalItemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupView()
        setupViewModel()
        viewModel.fetchEntries()
    }

    private fun setupView() {
        progressBarVisibility(visible = true)
        val linearLayoutManager = LinearLayoutManager(this)

        rvItems.run {
            adapter = listItemsAdapter
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }

        rvItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                viewModel.onScroll(dy, visibleItemCount, totalItemCount, pastVisibleItems)
            }
        })
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(this, ScreenActionObserver())
        viewModel.repositoriesInfoList.observe(this, Observer { showEntries(it) })
    }

    private fun showEntries(listItems: ArrayList<RawChild>) {
        progressBarVisibility(visible = false)
        progressBarMoreVisibility(visible = false)
        listItemsAdapter.run {
            itemsClickedListener = this@HomeActivity
            submitList(listItems.toMutableList())
        }
    }

    private fun fetchEntries() {
        progressBarMoreVisibility(visible = true)
        viewModel.fetchEntries()
    }

    private fun showError(errorMessage: String?) {
        progressBarVisibility(visible = false)
        progressBarMoreVisibility(visible = false)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> fetchEntries() }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun progressBarMoreVisibility(visible: Boolean) {
        progressBarLoadMore.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun progressBarVisibility(visible: Boolean) {
        toolbarProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private inner class ScreenActionObserver : Observer<HomeActivityViewModel.ScreenState> {
        override fun onChanged(screenAction: HomeActivityViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is FetchEntries -> fetchEntries()
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }

    override fun onItemRowClicked(url: String) {
        openEntryUrl(url)
    }

    private fun openEntryUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(resources.getColor(R.color.colorPrimaryDark))
        builder.build().launchUrl(this, Uri.parse(url))
    }
}
