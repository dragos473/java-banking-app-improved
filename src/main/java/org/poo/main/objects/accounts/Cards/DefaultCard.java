package org.poo.main.objects.accounts.Cards;

import lombok.Getter;
import lombok.Setter;
import org.poo.utils.Utils;

@Setter
@Getter
public class DefaultCard implements Card {
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
    public void freeze() {
        available = false;
    }
    /**
     * Method to notify the card that it has paid.
     */
    @Override
    public void payed() {
        available = true;
    }
}
