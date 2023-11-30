package bootstrap;

import rsa.domain.Key;
import rsa.domain.RSAKeys;
import rsa.domain.User;
import rsa.service.RSACipher;
import rsa.service.FileService;
import rsa.service.RSAKeyGenerator;
import rsa.service.View;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import static rsa.domain.User.ALICE;
import static rsa.domain.User.BOB;
import static rsa.service.View.STOP_MESSAGE;

public class BootStrap {

    public static void main(String[] args) {
        var fileService = new FileService();
        try (var view = new View(new Scanner(System.in))) {
            UserKeys userKeys = getResult(view, fileService);
            RSAKeys aliceKeys = userKeys.aliceKeys();
            RSAKeys bobKeys = userKeys.bobKeys();
            view.showKeys(aliceKeys, bobKeys);
            User user = ALICE;
            var cipher = new RSACipher();
            while (true) {
                Key ownKey = switch (user) {
                    case ALICE -> aliceKeys.privateKey();
                    case BOB -> bobKeys.privateKey();
                };
                Key receiverKey = switch (user) {
                    case ALICE -> bobKeys.publicKey();
                    case BOB -> aliceKeys.publicKey();
                };
                Optional<byte[]> receivedMsg = fileService
                        .getEncryptedMsg(user)
                        .map(msg -> cipher.decrypt(msg, ownKey));
                if (receivedMsg.isPresent()) {
                    var msg = receivedMsg.get();
                    fileService.saveDecryptedFile(msg, user);
                    String msgAsString = new String(msg);
                    view.userReceivedMsg(user, msgAsString);
                    if (STOP_MESSAGE.equals(msgAsString)) {
                        return;
                    }
                }
                String userMessage = view.getUserMessage(user.name);
                byte[] encrypted = cipher.encrypt(userMessage.getBytes(), receiverKey);
                fileService.saveEncryptedFile(encrypted, user);
                user = user.getOpposite();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static UserKeys getResult(View view, FileService fileService) {
        RSAKeys aliceKeys;
        RSAKeys bobKeys;
        if (view.askUserDoesHeWantToUseExistingKeys()) {
            fileService.cleanFolderExceptFiles(Set.of(BOB.privateKey, BOB.publicKey, ALICE.privateKey, ALICE.publicKey));
            aliceKeys = new RSAKeys(fileService.readKey(ALICE.publicKey), fileService.readKey(ALICE.privateKey));
            bobKeys = new RSAKeys(fileService.readKey(BOB.publicKey), fileService.readKey(BOB.privateKey));
        } else {
            fileService.cleanFolder();
            int bitLength = view.enterBitLength();
            RSAKeyGenerator generator = new RSAKeyGenerator();
            aliceKeys = generator.generateKeys(bitLength);
            bobKeys = generator.generateKeys(bitLength);
            fileService.writeKey(aliceKeys.publicKey(), ALICE.publicKey);
            fileService.writeKey(aliceKeys.privateKey(), ALICE.privateKey);
            fileService.writeKey(bobKeys.publicKey(), BOB.publicKey);
            fileService.writeKey(bobKeys.privateKey(), BOB.privateKey);
        }
        return new UserKeys(aliceKeys, bobKeys);
    }

    private record UserKeys(RSAKeys aliceKeys, RSAKeys bobKeys) {
    }
}
