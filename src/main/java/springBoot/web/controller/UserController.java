package springBoot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springBoot.web.service.UserService;

import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String loginPage() {
        userService.addAdminAndUserPanel();
        return "login";
    }
    @RequestMapping(value = "/panel", method = RequestMethod.GET)
    public String getTable(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "table";
    }
}

