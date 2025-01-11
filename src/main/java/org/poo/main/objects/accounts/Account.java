package org.poo.main.objects.accounts;

import org.poo.main.objects.accounts.Cards.Card;

import java.util.List;


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
}
