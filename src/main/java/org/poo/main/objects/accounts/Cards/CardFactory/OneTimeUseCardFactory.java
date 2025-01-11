package org.poo.main.objects.accounts.Cards.CardFactory;

import org.poo.main.objects.accounts.Cards.Card;
import org.poo.main.objects.accounts.Cards.OneTimeUseCard;

public class OneTimeUseCardFactory extends CardFactory {
    /**
     * Create a new OneTimeUseCard
     * @return OneTimeUseCard
     */
    @Override
    protected Card createCard() {
        return new OneTimeUseCard();
    }
}
