package org.poo.main.objects.accounts;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.objects.User;
import org.poo.main.objects.accounts.Cards.Card;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class BusinessAccount implements Account{
    private String IBAN;
    private String currency;
    private String alias;
    private double balance;
    private double minBalance;
    private ArrayList<Card> cards;
    private String plan;
    Map<String, Boolean> coupons;

    private String owner;
    private Map<String, List<Double>> managers;
    private Map<String, List<Double>> employees;

    public Boolean isAssociated(final String email) {
        if (owner.equals(email)) {
            return true;
        }
        return managers.containsKey(email) || employees.containsKey(email);
    }

    public List<Double> getAssociate(final String email) {
        if (owner.equals(email)) {
            return null;
        }
        if (managers.containsKey(email)) {
            return managers.get(email);
        }
        if (employees.containsKey(email)) {
            return employees.get(email);
        }
        return null;
    }

    /**
     * Registers an account, used for the AccountFactory
     * @param currency Currency of the account
     */
    public void register(final String currency, final String owner) {
        IBAN = Utils.generateIBAN();
        balance = 0.0;
        minBalance = 0.0;
        this.currency = currency;
        this.owner = owner;

        cards = new ArrayList<>();
        coupons = new HashMap<>();
        managers = new HashMap<>();
        employees = new HashMap<>();

        coupons.put("2%FOOD", false);
        coupons.put("5%CLOTHES", false);
        coupons.put("10%TECH", false);
        coupons.put(">100RON", false);
        coupons.put(">300RON", false);
        coupons.put(">500RON", false);
    }
}
