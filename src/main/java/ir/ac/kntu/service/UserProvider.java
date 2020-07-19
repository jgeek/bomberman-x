package ir.ac.kntu.service;

import ir.ac.kntu.data.User;

import java.util.Arrays;
import java.util.List;

public class UserProvider {

    public List<User> list() {
        return Arrays.asList(new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"),
                new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"));
    }
}
