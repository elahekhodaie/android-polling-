package com.jasbuber.allpolls.services;

import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jasbuber.allpolls.PollActivity;
import com.jasbuber.allpolls.R;
import com.jasbuber.allpolls.models.Poll;
import com.jasbuber.allpolls.repositories.PollRepository;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jasbuber on 11/06/2016.
 */
public class ProviderService {

    private static final String baseUrl = "http://elections.huffingtonpost.com/pollster/api/";

    public interface ProviderServerService {
        @GET("polls.json")
        Call<JsonArray> getPartialPolls(@Query("topic") String topic);
    }

    public void getPartialPollsFromProvider(final PollActivity activity, final Poll poll, final ImageButton but) {

        if (poll.getRemoteId().isEmpty()) {
            poll.sortPartialsByDateDesc();
            new PollCalculator().calculateResults(poll);

            InternalPollService service = new InternalPollService(new PollRepository());

            if (service.pollExists(poll.getId())) {
                service.createOrUpdatePoll(poll);
            }
            activity.displayPieChart(poll);
            but.clearAnimation();
            but.setEnabled(true);

            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProviderServerService service = retrofit.create(ProviderServerService.class);

        Call<JsonArray> call = service.getPartialPolls(poll.getRemoteId());

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                HashMap<String, JsonObject> results = new HashMap<>();

                for (JsonElement poll : response.body()) {
                    JsonObject pollObj = poll.getAsJsonObject();
                    String pollster = pollObj.get("pollster").getAsString();

                    if (!results.containsKey(pollster)) {
                        results.put(pollster, pollObj);
                    }
                }

                new ProviderDataConverter().fillPollWithProviderData(poll, results);

                InternalPollService service = new InternalPollService(new PollRepository());

                if (service.pollExists(poll.getId())) {
                    service.createOrUpdatePoll(poll);
                }
                activity.displayPieChart(poll);
                but.clearAnimation();
                but.setEnabled(true);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                but.clearAnimation();
                but.setEnabled(true);
                Toast.makeText(activity, activity.getString(R.string.unexpected_error), Toast.LENGTH_LONG).show();
            }
        });
    }
}
