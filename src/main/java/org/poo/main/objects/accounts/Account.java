package org.poo.main.objects.accounts;

import org.poo.main.objects.Bank;
import org.poo.main.objects.Commerciant;
import org.poo.main.objects.Commerciants;
import org.poo.main.objects.accounts.Cards.Card;

import java.util.List;
import java.util.Map;


public interface Account {

    //Setters

    /**
     * Setter for the alias of the account
     * @param alias Alias to be set
     */
    void setAlias(String alias);
    /**
     * Setter for the balance of the account
     * @param balance balance to be set
     */
    void setBalance(double balance);
    /**
     * Setter for the minimum balance of the account
     * @param minBalance minimum balance to be set
     */
    void setMinBalance(double minBalance);

    /**
     * Setter for the user's plan
     * @param plan
     */
    void setPlan(String plan);

    //Getters

    /**
     * Getter for the IBAN of the account
     * @return IBAN of the account
     */
    String getIBAN();
    /**
     * Getter for the currency of the account
     * @return Currency of the account
     */
    String getCurrency();
    /**
     * Getter for the alias of the account
     * @return Alias of the account
     */
    String getAlias();
    /**
     * Getter for the balance of the account
     * @return Balance of the account
     */
    double getBalance();
    /**
     * Getter for the minimum balance of the account
     * @return Minimum balance of the account
     */
    double getMinBalance();
    /**
     * Getter for the interest rate of the account
     * @return Interest rate of the account
     */
    double getInterestRate();
    /**
     * Getter for the cards of the account
     * @return Cards of the account
     */
    List<Card> getCards();
    /**
     * Getter for the user's plan
     * @return
     */
    String getPlan();

    Map<String, Boolean> getCoupons();

    //Card methods

    /**
     * Adds a card to the account
     * @param card Card to be added
     */
    void addCard(Card card);
    /**
     * Finds a card by its card number
     * @param cardNumber Card number to be found
     * @return The card that was found, or null if it doesn't exist
     */
    Card getCard(String cardNumber);
    /**
     * Removes a card from the account
     * @param cardNumber Card number to be removed
     */
    void removeCard(String cardNumber);

    //Account methods

    default double planModifier(double amount) {
        try {
            double rate = Bank.getInstance().getExchange().getExchangeRate("RON", getCurrency());
            switch (getPlan()) {
                case "standard":
                    return amount * 1.002;
                case "silver":
                    if (amount >= 500 * rate) {
                        return amount * 1.001;
                    }
                default:
                    return amount;
            }
        }
        catch (Exception e) {
            return amount;
        }
    }

    /**
     * Pays an amount from the account
     * @param amount Amount to be paid
     * @throws Exception The account does not have enough balance
     */
    void pay(double amount) throws Exception;
    /**
     * Deposits an amount to the account
     * @param amount Amount to be deposited
     */
    void deposit(double amount);
    /**
     * Registers an account, used for the AccountFactory
     * @param currency Currency of the account
     * @param interestRate Interest rate of the account(0.0 for classic accounts)
     */
    void register(String currency, double interestRate);

    default void Cashback(double amount, Commerciant commer) {
        if(commer == null) {
            return;
        }
        if (commer.getCashbackStrategy().equals("nrOfTransactions")) {
            if(getCoupons().get("2%FOOD") && commer.getType().equals("Food")) {
                setBalance(getBalance() + amount * 0.02);
                getCoupons().put("2%FOOD", false);
            } else if (getCoupons().get("5%CLOTHES") && commer.getType().equals("Clothes")) {
                setBalance(getBalance() + amount * 0.05);
                getCoupons().put("5%CLOTHES", false);
            } else if (getCoupons().get("10%TECH") && commer.getType().equals("Tech")) {
                setBalance(getBalance() + amount * 0.1);
                getCoupons().put("10%TECH", false);
            }
            return;
        }
        if (getCoupons().get(">500RON")) {
            switch (getPlan()){
                case "gold":
                    setBalance(getBalance() + amount * 0.007);
                    break;
                case "silver":
                    setBalance(getBalance() + amount * 0.005);
                    break;
                default:
                    setBalance(getBalance() + amount * 0.0025);
                    break;
            }
            return;
        }
        if (getCoupons().get(">300RON")) {
            switch (getPlan()){
                case "gold":
                    setBalance(getBalance() + amount * 0.0055);
                    break;
                case "silver":
                    setBalance(getBalance() + amount * 0.004);
                    break;
                default:
                    setBalance(getBalance() + amount * 0.002);
                    break;
            }
            return;
        }
        if (getCoupons().get(">100RON")) {
            switch (getPlan()){
                case "gold":
                    setBalance(getBalance() + amount * 0.005);
                    break;
                case "silver":
                    setBalance(getBalance() + amount * 0.003);
                    break;
                default:
                    setBalance(getBalance() + amount * 0.001);
                    break;
            }
        }
    }
}
