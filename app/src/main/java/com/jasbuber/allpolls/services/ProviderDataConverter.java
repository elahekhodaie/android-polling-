package com.jasbuber.allpolls.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jasbuber.allpolls.models.PartialPoll;
import com.jasbuber.allpolls.models.PartialPollChoice;
import com.jasbuber.allpolls.models.Poll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jasbuber on 11/06/2016.
 */
public class ProviderDataConverter {

    public static final int POLSTERS_NR = 10;

    public Poll fillPollWithProviderData(Poll poll, HashMap<String, JsonObject> data) {

        List<PartialPoll> toDelete = new ArrayList<>();

        List<PartialPoll> partialPolls = poll.getPartialPolls();

        for (PartialPoll partial : partialPolls) {
            if (data.get(partial.getPollster()) != null) {
                fetchPartialPollData(partial, data.get(partial.getPollster()), poll.getRemoteId());
            } else {
                toDelete.add(partial);
            }
        }

        partialPolls.removeAll(toDelete);

        poll.sortPartialsByDateDesc();

        if (partialPolls.size() >= POLSTERS_NR) {
            poll.setPartialPolls(new ArrayList<>(partialPolls.subList(0, POLSTERS_NR - 1)));
        }

        new PollCalculator().calculateResults(poll);
        return poll;

    }

    private PartialPoll fetchPartialPollData(PartialPoll partial, JsonObject data, String topic) {

        try {
            String unparsedDate = data.get("last_updated").getAsString();
            if(unparsedDate.contains(".")){
                partial.setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
                        .parse(unparsedDate));
            }else{
                partial.setLastUpdated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                        .parse(unparsedDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JsonArray questions = data.get("questions").getAsJsonArray();

        for (int i = 0; i < questions.size(); i++) {
            JsonObject question = questions.get(i).getAsJsonObject();

            JsonElement qTopic = question.get("topic");

            if (!qTopic.isJsonNull() && qTopic.getAsString().toLowerCase().equals(topic)) {
                JsonArray choices = question.get("subpopulations").getAsJsonArray()
                        .get(0).getAsJsonObject().get("responses").getAsJsonArray();

                List<PartialPollChoice> pChoices = new ArrayList<>();
                pChoices.addAll(partial.getPollerChoices());

                for (JsonElement choiceElem : choices) {
                    JsonObject choice = choiceElem.getAsJsonObject();
                    String partialChoice = choice.get("choice").getAsString();

                    PartialPollChoice partialObj = null;

                    for (PartialPollChoice pChoice : pChoices) {
                        if (pChoice.getName().equals(partialChoice)) {
                            partialObj = pChoice;
                        }
                    }

                    if (partialObj != null) {
                        partialObj.setValue(choice.get("value").getAsDouble());
                        partial.getUniversalValues().add(partialObj.getUniversalValue());
                        pChoices.remove(partialObj);
                    }
                }
            }
        }

        return partial;
    }
}
