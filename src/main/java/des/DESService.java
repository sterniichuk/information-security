package des;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static rsa.service.FileService.$;
import static rsa.service.FileService.PATH_TO_OUTPUT_FOLDER;

public class DESService {
    private static final String ENCRYPTED_FILE_EXTENSION = ".enc";
    private static final String PATH_TO_INPUT_FOLDER = "input";
    private final DESCipher cipher = new DESCipher();

    public String encryptFile(String fileName, String key) {
        String encryptedFileName = fileName + ENCRYPTED_FILE_EXTENSION;
        String inputPath = PATH_TO_INPUT_FOLDER + $ + fileName;
        try (var outputStream = new BufferedOutputStream(new FileOutputStream(PATH_TO_OUTPUT_FOLDER + $ + encryptedFileName))) {
            byte[] bytes = Files.readAllBytes(Path.of(inputPath));
            var outputBuffer = cipher.encrypt(bytes, key);
            outputStream.write(outputBuffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptedFileName;
    }

    public String decryptFile(String fileName, String key) {
        String decryptedFileName = "decrypted-file-" + fileName.split("\\.")[0] + ".txt";
        String inputPath = PATH_TO_OUTPUT_FOLDER + $ + fileName;
        try (var outputStream = new BufferedOutputStream(new FileOutputStream(PATH_TO_OUTPUT_FOLDER + $ + decryptedFileName))) {
            byte[] bytes = Files.readAllBytes(Path.of(inputPath));
            var outputBuffer = cipher.decrypt(bytes, key);
            outputStream.write(outputBuffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decryptedFileName;
    }
}
