package org.poo.main.objects.accounts;

import lombok.Getter;
import lombok.Setter;
import org.poo.main.objects.accounts.Cards.Card;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class SavingsAccount implements Account {
    private String IBAN;
    private String currency;
    private String alias;
    private double balance;
    private double minBalance;
    private double interestRate;
    private ArrayList<Card> cards;
    private String plan;
    Map<String, Boolean> coupons = new HashMap<>();

    /**
     * Registers an account, used for the AccountFactory
     * @param currency Currency of the account
     * @param interestRate Interest rate of the account(0.0 for classic accounts)
     */
    public void register(final String currency, final double interestRate) {
        IBAN = Utils.generateIBAN();
        balance = 0.0;
        this.currency = currency;
        this.interestRate = interestRate;
        cards = new ArrayList<>();
        coupons.put("2%FOOD", false);
        coupons.put("5%CLOTHES", false);
        coupons.put("10%TECH", false);
        coupons.put(">100RON", false);
        coupons.put(">300RON", false);
        coupons.put(">500RON", false);
    }
}
