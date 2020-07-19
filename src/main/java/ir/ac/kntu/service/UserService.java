package ir.ac.kntu.service;

import ir.ac.kntu.data.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private List<User> users = new ArrayList<>();

    public List<User> list() {
        return Arrays.asList(new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"),
                new User("behnia"), new User("sepanta"), new User("mohammad"), new User("alireza"), new User("aria"));
    }

    public void add(User user) throws IOException {

    }
}
