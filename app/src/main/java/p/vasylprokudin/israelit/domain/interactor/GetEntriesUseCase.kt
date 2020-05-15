package p.vasylprokudin.israelit.domain.interactor

import io.reactivex.Single
import p.vasylprokudin.israelit.Constants
import p.vasylprokudin.israelit.data.model.RawListing.RawData
import p.vasylprokudin.israelit.domain.interactor.base.BaseSingleUseCase
import p.vasylprokudin.israelit.domain.repository.EntriesRemoteRepository
import javax.inject.Inject

class GetEntriesUseCase
@Inject constructor(
    private val entriesRemoteRepository: EntriesRemoteRepository
) : BaseSingleUseCase<RawData, String>() {

    override fun buildUseCaseSingle(params: String?): Single<RawData> {
        params ?: return Single.error(IllegalStateException("after must be provided"))

        return entriesRemoteRepository.getRepositories(Constants.Integer.ENTRIES_LIMIT, params)
            .map { it.data }
    }
}
