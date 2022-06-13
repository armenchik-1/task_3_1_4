package springBoot.web.service;

import springBoot.web.model.Role;
import springBoot.web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    boolean addUser(User user, String role);

    void removeUser(long id);

    boolean updateUser(User user, String role);

    User getUserById(long id);

    void addAdminAndUserPanel();

    Set<Role> getRoleForUser(String role);
}
