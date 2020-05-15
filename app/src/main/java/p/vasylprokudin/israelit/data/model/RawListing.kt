package p.vasylprokudin.israelit.data.model

import com.google.gson.annotations.SerializedName

data class RawListing(
    val data: RawData
) {
    data class RawData(
        val children: ArrayList<RawChild>,
        val after: String
    ) {
        data class RawChild(
            val data: RawEntry
        ) {
            data class RawEntry(
                @SerializedName("author_fullname") val author: String,
                @SerializedName("num_comments") val comments: Int,
                @SerializedName("created_utc") val created: Long,
                val id: String,
                val title: String,
                val subreddit: String,
                val score: Int,
                val url: String
            )
        }
    }
}
