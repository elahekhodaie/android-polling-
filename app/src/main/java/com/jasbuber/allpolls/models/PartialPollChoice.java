package com.jasbuber.allpolls.models;

import com.jasbuber.allpolls.models.orm.PartialPollChoiceORM;

import java.io.Serializable;

/**
 * Created by Jasbuber on 09/06/2016.
 */
public class PartialPollChoice implements Serializable {

    public static final String UNDECIDED = "Undecided";

    private Long id;

    private String name;

    private String universalValue;

    private double value;

    public PartialPollChoice() {
    }

    public PartialPollChoice(PartialPollChoiceORM choice) {
        this.id = choice.getId();
        this.name = choice.getName();
        this.universalValue = choice.getUniversalValue();
        this.value = choice.getValue();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUniversalValue() {
        return universalValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

