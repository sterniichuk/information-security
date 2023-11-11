package bootstrap;

import rsa.domain.RSAKeys;
import rsa.service.RSAKeyGenerator;

public class BootStrap {
    public static void main(String[] args) {
        RSAKeyGenerator generator = new RSAKeyGenerator();
        RSAKeys rsaKeys = generator.generateKeys(512);
        System.out.println(rsaKeys);
    }
}
