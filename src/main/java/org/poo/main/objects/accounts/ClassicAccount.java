package org.poo.main.objects.accounts;
import lombok.Getter;
import lombok.Setter;
import org.poo.main.objects.accounts.Cards.Card;
import org.poo.utils.Utils;

import java.util.ArrayList;

@Setter
@Getter
public class ClassicAccount implements Account {
    private String IBAN;
    private String currency;
    private String alias;
    private double balance;
    private double minBalance;
    private double interestRate;
    private ArrayList<Card> cards;

    /**
     * Adds a card to the account
     * @param card Card to be added
     */
    public void addCard(final Card card) {
        cards.add(card);
        cards.getLast().setBalance(balance);
    }
    /**
     * Finds a card by its card number
     * @param cardNumber Card number to be found
     * @return The card that was found, or null if it doesn't exist
     */
    public Card getCard(final String cardNumber) {
        for (Card c : cards) {
            if (c.getCardNumber().equals(cardNumber)) {
                return c;
            }
        }
        return null;
    }
    /**
     * Removes a card from the account
     * @param cardNumber Card number to be removed
     */
    @Override
    public void removeCard(final String cardNumber) {
        for (Card c : cards) {
            if (c.getCardNumber().equals(cardNumber)) {
                cards.remove(c);
                return;
            }
        }
        throw new IllegalArgumentException("Card not found");
    }
    /**
     * Pays an amount from the account
     * @param amount Amount to be paid
     * @throws Exception The account does not have enough balance
     */
    public void pay(final double amount) throws Exception {

        if (balance - amount > 0) {
            balance -= amount;
        } else {
            if (balance < amount) {
                throw new Exception("Insufficient funds");
            }
            throw new Exception("Funds below minimum balance");
        }
    }
    /**
     * Deposits an amount to the account
     * @param amount Amount to be deposited
     */
    @Override
    public void deposit(final double amount) {
        balance += amount;
    }

    /**
     * Registers an account, used for the AccountFactory
     * @param currency Currency of the account
     * @param interestRate Interest rate of the account(0.0 for classic accounts)
     */
    public void register(final String currency, final double interestRate) {
        IBAN = Utils.generateIBAN();
        balance = 0.0;
        minBalance = 0.0;
        this.currency = currency;
        this.interestRate = interestRate;
        cards = new ArrayList<>();
    }
}
