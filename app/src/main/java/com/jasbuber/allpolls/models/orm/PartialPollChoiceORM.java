package com.jasbuber.allpolls.models.orm;

import com.jasbuber.allpolls.models.PartialPollChoice;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Jasbuber on 16/06/2016.
 */
public class PartialPollChoiceORM extends RealmObject {

    public static final String UNDECIDED = "Undecided";

    @PrimaryKey
    private Long id;

    private String name;

    @Required
    private String universalValue;

    private double value;

    public PartialPollChoiceORM() {
    }

    public PartialPollChoiceORM(PartialPollChoice choice) {
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
