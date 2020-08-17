package com.jasbuber.allpolls.models;

import com.jasbuber.allpolls.models.orm.PartialPollChoiceORM;
import com.jasbuber.allpolls.models.orm.PartialPollORM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jasbuber on 09/06/2016.
 */
public class PartialPoll implements Serializable {

    private Long id;

    private List<PartialPollChoice> pollerChoices;

    private String provider;

    private String pollster;

    private Set<String> universalValues = new HashSet<>();

    Date lastUpdated;

    public PartialPoll() {
    }

    public PartialPoll(PartialPollORM poll) {
        this.id = poll.getId();
        this.provider = poll.getProvider();
        this.pollster = poll.getPollster();
        this.lastUpdated = poll.getLastUpdated();

        pollerChoices = new ArrayList<>();
        for (PartialPollChoiceORM choice : poll.getPollerChoices()) {
            this.pollerChoices.add(new PartialPollChoice(choice));
        }
    }

    public Long getId() {
        return id;
    }

    public List<PartialPollChoice> getPollerChoices() {
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
        for (PartialPollChoice choice : pollerChoices) {
            universalValues.add(choice.getUniversalValue());
        }
    }

    public void setPollerChoices(List<PartialPollChoice> pollerChoices) {
        this.pollerChoices = pollerChoices;
    }
}
