package org.poo.main.objects.accounts.Cards;

public interface Card {
    //Getters

    /**
     * Getter for the Card Number
     * @return the card number of the card
     */
    String getCardNumber();

    /**
     * Getter for the status of the card
     * @return the status of the card(true if it is available, false if it is frozen)
     */
    boolean isAvailable();
    //Setters
    /**
     * Setter for the balance of the card
     * @param balance the new balance of the card
     */
    void setBalance(double balance);
    /**
     * Setter for the card number
     * @param cardNumber the new card number
     */
    void setCardNumber(String cardNumber);
    //Card methods
    /**
     * Registers a card, used for the CardFactory
     */
    void register();
    /**
     * Method to freeze the card
     */
    void freeze();
    /**
     * Method to notify the card that it has paid.
     * Throws an error if the card is a One Time Pay Card
     */
    void payed();
}
