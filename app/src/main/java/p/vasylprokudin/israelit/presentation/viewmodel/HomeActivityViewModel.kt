package p.vasylprokudin.israelit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import p.vasylprokudin.israelit.Constants
import p.vasylprokudin.israelit.domain.interactor.GetEntriesUseCase
import p.vasylprokudin.israelit.domain.interactor.base.EmptySingleObserver
import p.vasylprokudin.israelit.data.model.RawListing.RawData
import p.vasylprokudin.israelit.data.model.RawListing.RawData.RawChild
import javax.inject.Inject

class HomeActivityViewModel
@Inject constructor(
    private val getEntriesUseCase: GetEntriesUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    val repositoriesInfoList: LiveData<ArrayList<RawChild>> by lazy { mutableRepositoriesInfoList }

    private val mutableRepositoriesInfoList = MutableLiveData<ArrayList<RawChild>>()

    private var previousTotal = Constants.Integer.ZERO

    private var isLoading = true

    private var after = Constants.Strings.EMPTY

    fun fetchEntries() {
        getEntriesUseCase.execute(observer = GetEntriesObserver(), params = after)
    }

    fun onScroll(dy: Int, visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if (dy > 0) {
            if (isLoading) {
                if (totalItemCount > previousTotal) {
                    isLoading = false
                    previousTotal = totalItemCount
                }
            }
            val viewThreshold = Constants.Integer.ENTRIES_LIMIT
            if (!isLoading && (totalItemCount - visibleItemCount) <=
                pastVisibleItems + viewThreshold) {
                mutableScreenState.value = ScreenState.FetchEntries
                isLoading = true
            }
        }
    }

    internal inner class GetEntriesObserver : EmptySingleObserver<RawData>() {
        override fun onSuccess(result: RawData) {
            after = result.after
            val oldList = mutableRepositoriesInfoList.value
            oldList?.addAll(result.children)
            val newList = oldList ?: result.children
            mutableRepositoriesInfoList.value = newList
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        object FetchEntries : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}
