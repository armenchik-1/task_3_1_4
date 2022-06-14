package springBoot.web.service;

import org.springframework.context.annotation.Lazy;
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

    private final UserDao userDao;
    private final SecurityService securityService;

    public UserServiceImp(UserDao userDao, @Lazy SecurityService securityService) {
        this.userDao = userDao;
        this.securityService = securityService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean addUser(User user, String role) {
        if (user.getUsername().trim().length() == 0 || securityService.getCrypt(user.getPassword()).trim().length() == 0 || userDao.isExists(user.getEmail()) ||
                user.getEmail().trim().length() == 0 || user.getLastName().trim().length() == 0 || role.trim().length() == 0) {
            return false;
        } else {
            user.setRoles(getRoleForUser(role));
            user.setPassword(securityService.getCrypt(user.getPassword()));
            userDao.addUser(user);
            return true;
        }
    }

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


    @Override
    public void removeUser(long id) {
        userDao.removeUser(id);
    }

    @Override
    public boolean updateUser(User user, String role) {
        if (user.getUsername().trim().length() == 0 || securityService.getCrypt(user.getPassword().trim()).length() == 0 ||
                user.getEmail().trim().length() == 0 || user.getLastName().trim().length() == 0 || role.trim().length() == 0) {
            return false;
        } else {
            user.setRoles(getRoleForUser(role));
            user.setPassword(securityService.getCrypt(user.getPassword()));
            userDao.updateUser(user);
            return true;
        }
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userOptional = Optional.ofNullable(userDao.getUserByName(email));
        return userOptional.orElseThrow(IllegalAccessError::new);
    }

    @Transactional
    @Override
    public void addAdminAndUserPanel() {
        if (!userDao.isExists("admin@mail.com")) {

            Set<Role> admin = new HashSet<>();
            admin.add(new Role("ADMIN"));
            admin.add(new Role("USER"));
            userDao.addUser(new User("Уолтер",
                    "$2a$12$rN65NXEwU5eZCvC5LkRiQOnGp1PH1EFAhOXdYym142ZaWocGu1TLa",
                    "Уайт", "admin@mail.com", 30, admin));

            Set<Role> user = new HashSet<>();
            user.add(new Role("USER"));
            userDao.addUser(new User("Кристиан",
                    "$2a$12$6Rer8fNE1GyIMZgahQuReOnLkspgvC/Cbw52J.uOy3gdLdGQQRPr2",
                    "Эриксен", "user@mail.com", 28, user));
        }
    }
}
