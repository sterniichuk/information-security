package rsa.domain;

import java.time.format.DateTimeFormatter;

public enum User {

    ALICE("Alice"),
    BOB("Bob");
    public final String name;
    public final String publicKey;
    public final String privateKey;
    public static final String pattern = "HH-mm-ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);


    User(String name) {
        this.name = name;
        this.privateKey = name + "PrivateKey";
        this.publicKey = name + "PublicKey";
    }

    public String getReceiverEncryptedMsg() {
        return STR. "\{ name }-to-\{ getOpposite() }-encrypted-msg" ;
    }

    public User getOpposite() {
        return switch (this) {
            case ALICE -> BOB;
            case BOB -> ALICE;
        };
    }

    public String getReceiverDecryptedMsg() {
        return STR. "\{ getOpposite() }-to-\{ name }-decrypted-msg" ;
    }

    @Override
    public String toString() {
        return name;
    }
}
