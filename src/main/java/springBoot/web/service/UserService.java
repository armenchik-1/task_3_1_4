package springBoot.web.service;

import springBoot.web.model.Role;
import springBoot.web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public List<User> getAllUsers();

    public boolean addUser(User user, String role);

    public void removeUser(long id);

    public boolean updateUser(User user, String role);

    public User getUserById(long id);

    public void addAdminAndUserPanel();

    public String ifPasswordNull(Long id, String password);

    public Set<Role> getRoleForUser(String role);
}
