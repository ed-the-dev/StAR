package assistant.stacking.star.notifications

/**
 * Created by Ravi Tamada on 21/02/17.
 * www.androidhive.info
 */


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    val BASE_URL = "http://api.androidhive.info/json/"
    private var retrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}
