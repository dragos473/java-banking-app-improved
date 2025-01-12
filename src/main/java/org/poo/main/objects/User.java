package org.poo.main.objects;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.objects.accounts.Account;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String occupation;
    private List<Account> accounts;
    private Transactions transactions;
    private String plan;

    public User(final String firstName, final String lastName,
                final String email, final String birthDate, final String occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        age = Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears();
        this.occupation = occupation;
        accounts = new ArrayList<>();
        transactions = new Transactions();
        if (occupation.equals("student")) {
            plan = "student";
        } else {
            plan = "standard";
        }
    }

    /**
     * Finds the account with the given key(IBAN/Alias)
     * @param key IBAN/Alias
     * @return Account with the given key or null if not found
     */
    public Account getAccount(final String key) {
        for (Account a : accounts) {
            if (a.getIBAN().equals(key) || (a.getAlias() != null && a.getAlias().equals(key))) {
                return a;
            }
        }
        return null;
    }

    /**
     * Removes the account with the given IBAN
     * @param key IBAN of the account to be removed
     */
    public void removeAccount(final String key) {
        accounts.removeIf(a -> a.getIBAN().equals(key));
    }
}
