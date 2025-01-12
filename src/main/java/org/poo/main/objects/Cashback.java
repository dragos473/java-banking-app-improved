package org.poo.main.objects;

import org.poo.main.objects.accounts.Account;

import java.lang.management.MonitorInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cashback {
    Map<Account, Integer> threshold = new HashMap<>();
    Map<Account, Double> moneySpent = new HashMap<>();

    public void subscribe(Account acc) {
        threshold.put(acc, 2);
        moneySpent.put(acc, 0.0);

    }
    public void unsubscribe(Account acc) {
        threshold.remove(acc);
    }
    public void notify(Account acc, Commerciant c, double amount) {
        if (c == null) {
            return;
        }
        if(!c.payments.containsKey(acc)) {
           c.payments.put(acc, 1);
           threshold.put(acc, 2);
        }
        if(c.getCashbackStrategy().equals("nrOfTransactions")) {
            c.payments.put(acc, c.payments.get(acc) + 1);
            if (Objects.equals(c.payments.get(acc), threshold.get(acc))) {
                switch (threshold.get(acc)) {
                    case 2:
                        acc.getCoupons().put("2%FOOD", true);
                        threshold.put(acc, 5);
                        break;
                    case 5:
                        acc.getCoupons().put("5%CLOTHES", true);
                        threshold.put(acc, 10);
                        break;
                    case 10:
                        acc.getCoupons().put("10%TECH", true);
                        unsubscribe(acc);
                        break;
                    default:
                        return;
                }
            }
        } else {
            try {
                double rate = Bank.getInstance().getExchange().getExchangeRate("RON", acc.getCurrency());
                moneySpent.put(acc, moneySpent.get(acc) + amount);
                if (moneySpent.get(acc) >= 500 * rate) {
                    acc.getCoupons().put(">500RON", true);
                } else if (moneySpent.get(acc) >= 300 * rate) {
                    acc.getCoupons().put(">300RON", true);
                } else if (moneySpent.get(acc) >= 100 * rate) {
                    acc.getCoupons().put(">100RON", true);
                }
            } catch (Exception e) {
                return;
            }
        }
    }
}
