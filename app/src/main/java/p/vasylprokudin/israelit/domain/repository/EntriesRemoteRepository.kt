package p.vasylprokudin.israelit.domain.repository

import io.reactivex.Single
import p.vasylprokudin.israelit.data.model.RawListing

interface EntriesRemoteRepository {

    fun getRepositories(limit: Int, after: String): Single<RawListing>
}
