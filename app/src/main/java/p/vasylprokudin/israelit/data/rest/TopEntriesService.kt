package p.vasylprokudin.israelit.data.rest

import io.reactivex.Single
import p.vasylprokudin.israelit.data.model.RawListing
import retrofit2.http.GET
import retrofit2.http.Query

interface TopEntriesService {

    @GET("redditdev.json")
    fun getTopEntries(
        @Query("limit") limit: Int,
        @Query("after") after: String
    ): Single<RawListing>
}
