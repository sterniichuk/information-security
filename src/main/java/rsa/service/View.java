package rsa.service;

import rsa.domain.RSAKeys;
import rsa.domain.User;

import java.util.Scanner;

import static rsa.domain.User.ALICE;
import static rsa.domain.User.BOB;

public class View implements AutoCloseable {
    private final Scanner sc;

    public static final String STOP_MESSAGE = "bye";

    public View(Scanner sc) {
        this.sc = sc;
    }

    public boolean askUserDoesHeWantToUseExistingKeys() {
        System.out.print("""
                Do you want to use old keys from files?
                yes - 1
                no - anything
                Enter:""");
        return "1".equals(sc.nextLine());
    }

    public void showKeys(RSAKeys aliceKeys, RSAKeys bobKeys) {
        System.out.println(STR. """
                \{ ALICE } keys: \{ aliceKeys }
                \{ BOB } keys: \{ bobKeys }
                """ );
    }

    @Override
    public void close() {
        sc.close();
    }

    public int enterBitLength() {
        System.out.print("Enter bitLength of keys: ");
        return Integer.parseInt(sc.nextLine());
    }

    public String getUserMessage(String user) {
        System.out.print(STR. """
                \{ user } wants to send a message 👉👈
                Type '\{ STOP_MESSAGE }' to finish communication
                Enter message:""" );
        String msg = sc.nextLine();
        return msg == null || msg.isBlank() ? STOP_MESSAGE : msg;
    }

    public void userReceivedMsg(User user, String msg) {
        System.out.println(STR. """
                \nUser \{ user } received message: '\{ msg }'""" );
    }
}
