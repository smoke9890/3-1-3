package web;

import web.model.Role;
import web.model.User;
import web.Service.RoleService;
import web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application  extends SpringBootServletInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public Application(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public static void main(String[] args) {



        SpringApplication.run(Application.class, args);


    }

    public void run(String... args) throws Exception {

        roleService.addRole(new Role(1L,"ADMIN"));
        roleService.addRole(new Role(2L,"USER"));

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getRoleByRoleName("ADMIN"));
        roleSet.add(roleService.getRoleByRoleName("USER"));

        Set<Role> roleSetUser = new HashSet<>();
        roleSetUser.add(roleService.getRoleByRoleName("USER"));

        User admin = new User("Admin","Admin", (byte) 23,"Admin","Admin",roleSet);
        admin.setRoleSetTemp(new String[]{"ADMIN", "USER"});
        User user = new User("User", "User", (byte) 23, "User","Admin", roleSetUser);
        user.setRoleSetTemp(new String[]{"USER"});

        userService.saveUser(admin);
        userService.saveUser(user);
        openLoginPage();
    }

    private static void openLoginPage() {
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:8080/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(Application.class);
    }
}
