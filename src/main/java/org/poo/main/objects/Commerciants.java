package org.poo.main.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Commerciants {
    private List<String> names;
    private List<Double> moneySpent;


    public Commerciants() {
        moneySpent = new ArrayList<>();
        names = new ArrayList<>();
    }

    /**
     * Adds a commerciant to the list of commerciants.
     * If the commerciant is already in the list, this increments the money spent by the commerciant
     * @param name of the commerciant
     * @param moneySpent the amount of money spent by the commerciant in the current transaction
     */
    public void addCommerciants(final String name, final double moneySpent) {
        if (names.isEmpty()) {
            names.add(name);
            this.moneySpent.add(moneySpent);
            return;
        }

        if (names.contains(name)) {
            int index = names.indexOf(name);
            this.moneySpent.set(index, this.moneySpent.get(index) + moneySpent);
            return;
        }

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).compareTo(name) > 0) {
                names.add(i, name);
                this.moneySpent.add(i, moneySpent);
                return;
            }
        }
        names.addLast(name);
        this.moneySpent.addLast(moneySpent);
    }
}
