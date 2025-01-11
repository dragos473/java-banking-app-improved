package org.poo.main.objects.accounts.Cards.CardFactory;

import org.poo.main.objects.accounts.Cards.Card;

public abstract class CardFactory {
    /**
     * Create a card with the balance of it's owner
     * @param balance the balance of the owner
     * @return the created card
     */
    public Card create(final double balance) {
        Card card = createCard();
        card.register();
        return card;
    }
    protected abstract Card createCard();
}
