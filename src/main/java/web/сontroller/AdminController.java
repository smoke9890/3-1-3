package web.сontroller;

import web.model.Role;
import web.model.User;
import web.Service.RoleService;
import web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Ignore
@RequestMapping(value = "/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView allUsers() {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin");
        modelAndView.addObject("admin",admin);
        return modelAndView;
    }

    @GetMapping(value = "/api/users")
    public ResponseEntity<List<User>> AllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/api/users")
    public ResponseEntity<User> add(@RequestBody User user){
        user.setRoleSet(roleService.getSetOfRoles(user.getRoleSetTemp()));
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/api/users")
    public ResponseEntity<User> edit(@RequestBody User user){
        user.setRoleSet(roleService.getSetOfRoles(user.getRoleSetTemp()));
        userService.edit(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    private void userManipulation(User user) {
//        Set<Role> roleSet = new HashSet<>();
//        if (user.getRoleSetTemp().length == 2){
//            roleSet.add(roleService.getRoleByRoleName("User"));
//            roleSet.add(roleService.getRoleByRoleName("Admin"));
//        } else {
//            if (user.getRoleSetTemp()[0].equals("Admin")) roleSet.add(roleService.getRoleByRoleName("Admin"));
//            else roleSet.add(roleService.getRoleByRoleName("User"));
//        }
//
//        user.setRoleSet(roleSet);
//        userService.saveUser(user);
//    }
//    private void userManipulations(User user) {
//        Set<Role> roleSet = new HashSet<>();
//        if (user.getRoleSetTemp().length == 2){
//            roleSet.add(roleService.getRoleByRoleName("User"));
//            roleSet.add(roleService.getRoleByRoleName("Admin"));
//        } else {
//            if (user.getRoleSetTemp()[0].equals("Admin")) roleSet.add(roleService.getRoleByRoleName("Admin"));
//            else roleSet.add(roleService.getRoleByRoleName("User"));
//        }
//
//        user.setRoleSet(roleSet);
//
//        userService.edit(user);
//    }

    @DeleteMapping(value = "/api/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        userService.removeUserById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
