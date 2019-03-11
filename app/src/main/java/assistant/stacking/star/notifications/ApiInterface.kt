package assistant.stacking.star.notifications

import assistant.stacking.star.notifications.Message
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Ravi Tamada on 21/02/17.
 * www.androidhive.info
 */

interface ApiInterface {
    @get:GET("inbox.json")
    val inbox: Call<List<Message>>
}
