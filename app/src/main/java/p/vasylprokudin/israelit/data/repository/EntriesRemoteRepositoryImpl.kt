package p.vasylprokudin.israelit.data.repository

import io.reactivex.Single
import p.vasylprokudin.israelit.data.model.RawListing
import p.vasylprokudin.israelit.data.rest.TopEntriesService
import p.vasylprokudin.israelit.domain.repository.EntriesRemoteRepository
import javax.inject.Inject

class EntriesRemoteRepositoryImpl
@Inject constructor(
    private val topEntriesService: TopEntriesService
) : EntriesRemoteRepository {

    override fun getRepositories(limit: Int, after: String): Single<RawListing> = topEntriesService.getTopEntries(limit, after)
}
