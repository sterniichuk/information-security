package des;

import java.util.Scanner;

public class View implements AutoCloseable {
    private final Scanner sc;
    public static final String DEFAULT_FILE = "target.txt";

    public View() {
        this.sc = new Scanner(System.in);
    }

    public String getKey() {
        System.out.print("Enter the key:");
        return sc.nextLine();
    }

    @Override
    public void close() {
        sc.close();
    }

    public void greeting() {
        System.out.println("DES algorithm");
    }

    public String getFileName() {
        System.out.print("Enter the name of the file:");
        String line = sc.nextLine();
        if(line.isBlank()){
            return DEFAULT_FILE;
        }
        return line;
    }

    public void successEncryptedFile(String file) {
        System.out.println("The encrypted file is " + file);
    }

    public void startDecryptFile(String encryptedFile) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Start decrypting the file " + encryptedFile);
    }

    public void successDecryptFile(String decryptFile) {
        System.out.println("The decrypted file is " + decryptFile);
    }
}
