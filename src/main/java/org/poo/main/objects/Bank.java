package org.poo.main.objects;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;

import java.util.ArrayList;

@Setter
@Getter
public final class Bank {
    private static Bank instance;
    private ArrayList<User> users;
    private Exchange exchange;

    private Bank() {
        users = new ArrayList<>();
        UserInput[] get = Input.getInstance(new ObjectInput()).inputData.getUsers();
        for (UserInput ui : get) {
            users.add(new User(ui.getFirstName(), ui.getLastName(), ui.getEmail()));
        }
        ExchangeInput[] commands = Input.getInstance(new ObjectInput())
                .inputData.getExchangeRates();
        exchange = new Exchange();
        for (ExchangeInput ei : commands) {
            getExchange().addRate(ei.getFrom(), ei.getTo(), ei.getRate());
        }
    }

    /**
     * Singleton initializer, that only allows one instance of Bank
     * @return the instance of bank
     */
    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    /**
     * Method to delete the instance of the Bank, preventing tests from overlapping
     */
    public static void deleteInstance() {
        instance = null;
    }
    /**
     * Method to add a user to the bank
     * @param email the email of the user that needs to be found
     * @return the user that was found
     * @exception  NoSuchMethodException if the user is not found
     */
    public User getUser(final String email) throws NoSuchMethodException {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        throw new NoSuchMethodException("No User Found");
    }
}
