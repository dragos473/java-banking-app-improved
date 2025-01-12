package org.poo.main.objects.Actions;

import org.poo.fileio.CommandInput;

import java.util.HashMap;
import java.util.Map;

public class ActionMap {
    private Map<String, Action> map = new HashMap<>();

    public ActionMap() {
        map.put("addAccount", new AddAccount());
        map.put("printUsers", new PrintUsers());
        map.put("addFunds", new AddFunds());
        map.put("createCard", new AddCard());
        map.put("createOneTimeCard", new AddOneTimeCard());
        map.put("deleteAccount", new DeleteAccount());
        map.put("deleteCard", new DeleteCard());
        map.put("payOnline", new PayOnline());
        map.put("sendMoney", new SendMoney());
        map.put("setAlias", new SetAlias());
        map.put("printTransactions", new PrintTransactions());
        map.put("setMinimumBalance", new SetMinBalance());
        map.put("checkCardStatus", new CheckCardStatus());
        map.put("splitPayment", new SplitPayment());
        map.put("report", new Report());
        map.put("spendingsReport", new SpendingReport());
        map.put("changeInterestRate", new ChangeInterestRate());
        map.put("addInterest", new AddInterestRate());
        map.put("withdrawSavings", new WithdrawSavings());
        map.put("upgradePlan", new UpgradePlan());
    }
    /**
     * Finds and executes the action given by the command name
     * @param input the input needed for the action
     */
    public void execute(final CommandInput input) throws NoSuchMethodException {
        if (!map.containsKey(input.getCommand())) {
            throw new NoSuchMethodException();
        }
        map.get(input.getCommand()).execute(input);
    }
}
