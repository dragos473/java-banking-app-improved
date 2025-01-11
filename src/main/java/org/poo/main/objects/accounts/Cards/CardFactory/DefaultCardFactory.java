package org.poo.main.objects.accounts.Cards.CardFactory;

import org.poo.main.objects.accounts.Cards.Card;
import org.poo.main.objects.accounts.Cards.DefaultCard;

public class DefaultCardFactory extends CardFactory {
    /**
     * Create a new DefaultCard
     * @return DefaultCard
     */
    @Override
    protected Card createCard() {
        return new DefaultCard();
    }
}
