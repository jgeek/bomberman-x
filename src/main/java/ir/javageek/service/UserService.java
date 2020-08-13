package ir.javageek.service;

import ir.javageek.Constants;
import ir.javageek.data.DatabaseStateAware;
import ir.javageek.data.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static int LAST_ID = 0;
    private List<User> users;
    private List<DatabaseStateAware> dataLovers = new ArrayList<>();

    public List<User> list() {
        if (users == null) {
            users = readAll();
        }
        return users;
//        return Arrays.asList(new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"),
//                new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"));
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
            notifyDataLovers();
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
//                oFile.flush();
//                oFile.close();
                System.out.println("db is created");
                users = new ArrayList<>();
                return users;
            } catch (Exception e2) {
                System.out.println("could not create file " + Constants.USER_DB_FILE);
                throw new RuntimeException(e2);
            }
        } catch (EOFException eof) {
            return users;
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

    public void subscribe(DatabaseStateAware dataLover) {
        dataLovers.add(dataLover);
    }

    private void notifyDataLovers() {
        dataLovers.forEach(o -> o.update(users));
    }
}
