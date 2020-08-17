package com.jasbuber.allpolls.services;

import com.jasbuber.allpolls.models.PartialPoll;
import com.jasbuber.allpolls.models.Poll;
import com.jasbuber.allpolls.models.orm.PollORM;
import com.jasbuber.allpolls.repositories.PollRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class InternalPollService {

    private PollRepository repository;

    public InternalPollService(PollRepository repository) {
        this.repository = repository;
    }

    public List<PollORM> getMyPolls() {
        return repository.getMyPolls();
    }

    public void createOrUpdatePoll(Poll poll) {
        PollORM pollORM = new PollORM(poll);
        repository.createOrUpdatePoll(pollORM);
    }

    public Poll getPoll(long id) {

        Poll poll = new Poll(repository.getPoll(id));

        List<PartialPoll> partialPolls = poll.getPartialPolls();

        Collections.sort(partialPolls, new Comparator<PartialPoll>() {
            public int compare(PartialPoll p1, PartialPoll p2) {
                return p2.getLastUpdated().compareTo(p1.getLastUpdated());
            }
        });

        if (partialPolls.size() >= ProviderDataConverter.POLSTERS_NR) {
            poll.setPartialPolls(partialPolls.subList(0, ProviderDataConverter.POLSTERS_NR - 1));
        }

        return poll;
    }

    public boolean pollExists(long id) {
        return repository.pollExists(id);
    }

    public void deletePoll(Poll poll) {
        repository.deletePoll(poll);
    }

}
