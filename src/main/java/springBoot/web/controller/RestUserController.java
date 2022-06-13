package springBoot.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springBoot.web.model.User;
import springBoot.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/*")
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/getUser")
    public ResponseEntity<List<User>> getUser(HttpSession session) {
        List<User> userList = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        userList.add(user);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/admin/addUser")
    public void addUser(@RequestParam String firstName, String password, String lastName, String email, int age, String role) {
        userService.addUser(new User(firstName, password, lastName, email, age), role);
    }

    @PutMapping("/admin/update")
    public void updateUser(@RequestParam Long id, String firstName, String password, String lastName, String email, int age, String role) {
        userService.updateUser(new User(id, firstName, lastName, age, email, password), role);
    }

    @DeleteMapping("/admin/remove")
    public void removeUser(@RequestParam Long id) {
        userService.removeUser(id);
    }
}