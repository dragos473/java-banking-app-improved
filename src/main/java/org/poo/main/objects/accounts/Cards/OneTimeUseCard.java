package org.poo.main.objects.accounts.Cards;

import lombok.Getter;
import lombok.Setter;
import org.poo.utils.Utils;

@Setter
@Getter
public class OneTimeUseCard implements Card {
    private String cardNumber;
    private double balance;
    private boolean available;
    /**
     * Registers a card, used for the CardFactory
     */
    public void register() {
        cardNumber = Utils.generateCardNumber();
        available = true;
    }
    /**
     * Method to freeze the card
     */
    @Override
    public void freeze() {
        available = false;
    }
    /**
     * Method to notify the card that it has paid.
     * Throws an error that signals the card is a One Time Pay Card
     */
    public void payed() {
        throw new UnsupportedOperationException("OneTimeUseCard");
    }
}
