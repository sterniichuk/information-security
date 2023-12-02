package des;

import rsa.service.FileService;

public class DESBootstrap {
    public static void main(String[] args) {
        FileService.cleanFolder();
        try (View view = new View()) {
            view.greeting();
            String key = view.getKey();
            String fileName = view.getFileName();
            DESService service = new DESService();
            String encryptedFile = service.encryptFile(fileName, key);
            view.successEncryptedFile(encryptedFile);
            view.startDecryptFile(encryptedFile);
            var decryptFile = service.decryptFile(encryptedFile, key);
            view.successDecryptFile(decryptFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
