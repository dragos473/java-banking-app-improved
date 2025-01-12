package org.poo.main.objects;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.objects.accounts.Account;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Commerciant {
    private final String name;
    private final String iban;
    private final String type;
    private final String cashbackStrategy;
    private final int id;
    Map<Account, Integer> payments;

    public Commerciant(final String name, final String iban, final String type,
                       final String cashbackStrategy, final int id) {
        this.name = name;
        this.iban = iban;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
        this.id = id;
        payments= new HashMap<>();
    }

}