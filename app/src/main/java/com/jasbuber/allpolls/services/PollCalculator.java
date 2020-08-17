package com.jasbuber.allpolls.services;

import com.jasbuber.allpolls.models.PartialPoll;
import com.jasbuber.allpolls.models.PartialPollChoice;
import com.jasbuber.allpolls.models.Poll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PollCalculator {

    ArrayList<String> universalValues;

    public Poll calculateResults(Poll poll) {

        List<Map<String, Double>> choicesList = new ArrayList<>();

        List<PartialPoll> partialPolls = poll.getPartialPolls();

        universalValues = new ArrayList<>();

        for (PartialPoll partial : partialPolls) {
            if (universalValues.isEmpty()) {
                universalValues.addAll(partial.getUniversalValues());
            } else {
                universalValues.retainAll(partial.getUniversalValues());
            }
        }

        Collections.sort(universalValues);

        for (PartialPoll partial : partialPolls) {
            List<PartialPollChoice> choices = (ArrayList) partial.getPollerChoices();
            PartialPollChoice[] universalChoices = new PartialPollChoice[universalValues.size()];
            List<PartialPollChoice> otherChoices = new ArrayList<>();

            Map<String, Double> partChoices = new LinkedHashMap<>();

            Double total = 0.0;
            boolean isRecalculate = false;

            for (PartialPollChoice choice : choices) {

                String universalValue = choice.getUniversalValue();

                if (universalValues.contains(universalValue)) {
                    partChoices.put(universalValue, choice.getValue());
                    total += choice.getValue();

                    int index = universalValues.indexOf(universalValue);
                    universalChoices[index] = choice;
                } else {
                    otherChoices.add(choice);
                    isRecalculate = true;
                }
            }

            ArrayList<PartialPollChoice> orderedChoices = new ArrayList<>(Arrays.asList(universalChoices));
            orderedChoices.addAll(otherChoices);
            partial.setPollerChoices(orderedChoices);
            double difference = 100.0 - total;

            partChoices.put(PartialPollChoice.UNDECIDED, difference);

            if (isRecalculate) {
                partChoices = getRecalculatedMap(partChoices);

            }
            choicesList.add(partChoices);
        }

        universalValues.add(PartialPollChoice.UNDECIDED);
        poll.setResults(getFinalResults(choicesList));

        return poll;
    }

    private Map<String, Double> getRecalculatedMap(Map<String, Double> choicesMap) {

        double sum = 0.0;
        Map<String, Double> newMap = new LinkedHashMap<>();

        for (Double choice : choicesMap.values()) {
            sum += choice;
        }
        for (Map.Entry<String, Double> choice : choicesMap.entrySet()) {
            double newValue = (choice.getValue() / sum) * 100;
            newMap.put(choice.getKey(), newValue);
        }

        return newMap;
    }

    private LinkedHashMap<String, Double> getFinalResults(List<Map<String, Double>> choicesList) {

        LinkedHashMap<String, Double> result = new LinkedHashMap<>();

        for (String universalValue : universalValues) {
            double newValue = 0.0;
            for (Map<String, Double> partial : choicesList) {
                newValue += partial.get(universalValue);
            }
            newValue = newValue / choicesList.size();

            if(newValue > 0.1) {
                result.put(universalValue, newValue);
            }
        }

        return result;
    }

    public Map<String, Double> calculatePartialResults(PartialPoll poll) {

        Collection<PartialPollChoice> choices = poll.getPollerChoices();
        Map<String, Double> partChoices = new LinkedHashMap<>();

        Double total = 0.0;
        boolean isRecalculate = false;

        for (PartialPollChoice choice : choices) {
            partChoices.put(choice.getUniversalValue(), choice.getValue());
            total += choice.getValue();
        }
        double difference = 100.0 - total;

        if (Math.abs(difference) > 0.1) {
            isRecalculate = true;
            if(difference > 0) {
                partChoices.put(PartialPollChoice.UNDECIDED, difference);
            }
        }

        if (isRecalculate) {
            partChoices = getRecalculatedMap(partChoices);
        }

        return partChoices;

    }


}
