package springBoot.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springBoot.web.model.User;
import springBoot.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/*")
public class RestUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/getUser", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUser(HttpSession session) {
        List<User> userList = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        userList.add(user);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
    public void addUser(@RequestParam String firstName, String password, String lastName, String email, int age, String role) {
        userService.addUser(new User(firstName, password, lastName, email, age), role);
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.PUT)
    public void updateUser(@RequestParam Long id, String firstName, String password, String lastName, String email, int age, String role) {
        password = userService.ifPasswordNull(id, password);
        userService.updateUser(new User(id, firstName, lastName, age, email, password), role);
    }

    @RequestMapping(value = "/admin/remove", method = RequestMethod.DELETE)
    public void removeUser(@RequestParam Long id) {
        userService.removeUser(id);
    }
}