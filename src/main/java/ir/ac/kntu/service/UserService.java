package ir.ac.kntu.service;

import ir.ac.kntu.Constants;
import ir.ac.kntu.data.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserService {

    private static int LAST_ID = 0;
    private List<User> users = new ArrayList<>();

    public List<User> list() {
        return Arrays.asList(new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"),
                new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"));
    }

    public void add(User user) {
        user.setId(getNewId());
        users.add(user);
        System.out.println(String.format("%s inserted to db", user));
        saveAll();
    }

    private int getNewId() {
        return ++LAST_ID;
    }

    public void saveAll() {
        FileOutputStream out = null;
        ObjectOutputStream oos = null;
        try {
            out = new FileOutputStream(Constants.USER_DB_FILE);
            oos = new ObjectOutputStream(out);
            oos.writeObject(users);
            oos.flush();
            System.out.println("db updated successfully");
        } catch (Exception e) {
            System.out.println("could not save users to db");
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<User> readAll() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(Constants.USER_DB_FILE);
            ois = new ObjectInputStream(fis);
            users = (List<User>) ois.readObject();
            calculateLastId();
            System.out.println("users loaded from db");
            return users;
        } catch (FileNotFoundException e) {
            try {
                File db = new File(Constants.USER_DB_FILE);
                File parent = db.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IllegalStateException("Couldn't create dir: " + parent);
                }
//                db.createNewFile();
                FileOutputStream oFile = new FileOutputStream(db, false);
                System.out.println("db is created");
                return Collections.emptyList();
            } catch (Exception e2) {
                System.out.println("could not create file " + Constants.USER_DB_FILE);
                throw new RuntimeException(e2);
            }
        } catch (Exception e) {
            System.out.println("could not load users from db");
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void calculateLastId() {
        LAST_ID = users.stream().mapToInt(User::getId).max().getAsInt();
    }
}
