package springBoot.web.dao;


import springBoot.web.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    void addUser(User user);

    void removeUser(long id);

    void updateUser(User user);

    boolean isExists(String name);

    User getUserById(long id);

    User getUserByName(String name);

}

