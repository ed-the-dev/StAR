package assistant.stacking.star.notifications;

import java.util.List;

import assistant.stacking.star.notifications.Message;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ravi Tamada on 21/02/17.
 * www.androidhive.info
 */

public interface ApiInterface {
    @GET("inbox.json")
    Call<List<Message>> getInbox();
}
