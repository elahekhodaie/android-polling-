package com.jasbuber.allpolls.models.orm;

import com.jasbuber.allpolls.models.PartialPoll;
import com.jasbuber.allpolls.models.PartialPollChoice;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Jasbuber on 16/06/2016.
 */
public class PartialPollORM extends RealmObject {

    @PrimaryKey
    private Long id;

    private RealmList<PartialPollChoiceORM> pollerChoices = new RealmList<>();

    private String provider;

    @Required
    private String pollster;

    @Ignore
    private Set<String> universalValues = new HashSet<>();

    Date lastUpdated;

    public PartialPollORM() {
    }

    public PartialPollORM(PartialPoll poll) {
        this.id = poll.getId();
        this.provider = poll.getProvider();
        this.pollster = poll.getPollster();
        this.lastUpdated = poll.getLastUpdated();

        for (PartialPollChoice choice : poll.getPollerChoices()) {
            this.pollerChoices.add(new PartialPollChoiceORM(choice));
        }
    }

    public Long getId() {
        return id;
    }

    public List<PartialPollChoiceORM> getPollerChoices() {
        return pollerChoices;
    }

    public String getProvider() {
        return provider;
    }

    public String getPollster() {
        return pollster;
    }

    public Set<String> getUniversalValues() {

        if (universalValues.isEmpty()) {
            fillUniversalValues();
        }
        return universalValues;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    protected void fillUniversalValues() {
        for (PartialPollChoiceORM choice : pollerChoices) {
            universalValues.add(choice.getUniversalValue());
        }
    }
}
