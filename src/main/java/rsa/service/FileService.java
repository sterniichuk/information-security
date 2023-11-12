package rsa.service;

import rsa.domain.Key;
import rsa.domain.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

import static rsa.domain.User.*;

public class FileService {
    private static final String PATH_TO_FOLDER = "output";
    private static final String $ = File.separator;
    private static final String fileType = ".txt";


    public void writeKey(Key key, String fileName) {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(PATH_TO_FOLDER + $ + fileName))) {
            outputStream.writeObject(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Key readKey(String fileName) {
        try (var outputStream = new ObjectInputStream(new FileInputStream(PATH_TO_FOLDER + $ + fileName))) {
            return (Key) outputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanFolder() {
        File dir = new File(PATH_TO_FOLDER);
        for (var f : Objects.requireNonNull(dir.listFiles())) {
            f.delete();
        }
    }

    public void cleanFolderExceptFiles(Set<String> skipFiles) {
        File dir = new File(PATH_TO_FOLDER);
        for (var f : Objects.requireNonNull(dir.listFiles())) {
            if (!skipFiles.contains(f.getName())) {
                f.delete();
            }
        }
    }

    private static final int length = pattern.length();

    public void saveDecryptedFile(byte[] msg, User user) {
        String path = STR. "\{ PATH_TO_FOLDER }\{ $ }\{ user.getReceiverDecryptedMsg() }-\{ LocalTime.now().format(formatter) }\{fileType}" ;
        try {
            Files.write(Path.of(path), msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveEncryptedFile(byte[] msg, User user) {
        String path = STR. "\{ PATH_TO_FOLDER }\{ $ }\{ user.getReceiverEncryptedMsg() }-\{ LocalTime.now().format(formatter) }\{fileType}" ;
        try {
            Files.write(Path.of(path), msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    record FileTime(File f, LocalTime time) {
    }

    public Optional<byte[]> getEncryptedMsg(User user) {
        File dir = new File(PATH_TO_FOLDER);
        String startWith = user.getOpposite().getReceiverEncryptedMsg();
        return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(f -> f.getName().startsWith(startWith))
                .map(this::toFileTime)
                .max(Comparator.comparing(o -> o.time))
                .map(FileTime::f)
                .map(f -> {
                    try {
                        return Files.readAllBytes(f.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private FileTime toFileTime(File file) {
        String time = file.getName().substring(file.getName().length() - length - fileType.length(), file.getName().length() - fileType.length());
        return new FileTime(file, LocalTime.parse(time, formatter));
    }
}
