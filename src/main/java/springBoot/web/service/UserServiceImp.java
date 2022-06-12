package springBoot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springBoot.web.dao.UserDao;
import springBoot.web.model.Role;
import springBoot.web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserDao dao;

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Transactional
    @Override
    public boolean addUser(User user, String role) {
        if (user.getUsername().trim().length() == 0 || user.getPassword().trim().length() == 0 || dao.isNotReg(user.getEmail()) ||
                user.getEmail().trim().length() == 0 || user.getLastName().trim().length() == 0 || role.trim().length() == 0) {
            return false;
        } else {
            user.setRoles(getRoleForUser(role));
            dao.addUser(user);
            return true;
        }
    }

    // Распределяем роли для пользователя, одна или две
    @Transactional
    @Override
    public Set<Role> getRoleForUser(String role) {
        Set<Role> roles = new HashSet<>();
        try {
            String[] partsRole = role.split(",");
            roles.add(new Role(partsRole[1]));
            roles.add(new Role(partsRole[0]));
            return roles;
        } catch (Exception e) {

        }
        roles.add(new Role(role));
        return roles;
    }


    @Transactional
    @Override
    public void removeUser(long id) {
        dao.removeUser(id);
    }

    @Transactional
    @Override
    public boolean updateUser(User user, String role) {
        if (user.getUsername().trim().length() == 0 || user.getPassword().trim().length() == 0 ||
                user.getEmail().trim().length() == 0 || user.getLastName().trim().length() == 0 || role.trim().length() == 0) {
            return false;
        } else {
            user.setRoles(getRoleForUser(role));
            dao.updateUser(user);
            return true;
        }
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return dao.getUserById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userMayBy = Optional.ofNullable(dao.getUserByName(email));
        return userMayBy.orElseThrow(IllegalAccessError::new);
    }

    // если поле пароля не заполнено, сохраняем пользователю старый пароль
    @Transactional
    @Override
    public String ifPasswordNull(Long id, String password) {
        if (password.trim().length() == 0) {
            return dao.getUserById(id).getPassword();
        } else {
            return password;
        }
    }

    // Создаем пользователя админ и юзер если еще не созданы
    // Админ имеет две роли и доступ ко всему
    // У юзера одна роль и ограниченость в доступе
    @Transactional
    @Override
    public void addAdminAndUserPanel() {
        if (!dao.isNotReg("admin@mail.com")) {

            Set<Role> admin = new HashSet<>();
            admin.add(new Role("ADMIN"));
            admin.add(new Role("USER"));
            dao.addUser(new User("Брюс", "admin", "Уэйн", "admin@mail.com", 30, admin));

            Set<Role> user = new HashSet<>();
            user.add(new Role("USER"));
            dao.addUser(new User("Джокер", "user", "Напьер", "user@mail.com", 28, user));
        }
    }
}
