package com.jasbuber.allpolls.models.orm;

import com.jasbuber.allpolls.models.PartialPoll;
import com.jasbuber.allpolls.models.Poll;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Jasbuber on 16/06/2016.
 */
public class PollORM extends RealmObject {

    @PrimaryKey
    private Long id;

    RealmList<PartialPollORM> partialPolls = new RealmList<>();

    @Required
    String topic;

    String remoteId;

    Date expirationDate;

    String location;

    public PollORM() {
    }

    public PollORM(String topic) {
        this.topic = topic;
    }

    public PollORM(Poll poll) {
        this.id = poll.getId();
        this.topic = poll.getTopic();
        this.remoteId = poll.getRemoteId();
        this.expirationDate = poll.getExpirationDate();
        this.location = poll.getLocation();

        for (PartialPoll partial : poll.getPartialPolls()) {
            this.partialPolls.add(new PartialPollORM(partial));
        }
    }

    public Long getId() {
        return id;
    }

    public List<PartialPollORM> getPartialPolls() {
        return partialPolls;
    }

    public String getTopic() {
        return topic;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Ignore
    public HashMap<String, Double> results;

    public HashMap<String, Double> getResults() {
        return results;
    }

    public void setResults(HashMap<String, Double> results) {
        this.results = results;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public String getLocation() {
        return location;
    }
}
