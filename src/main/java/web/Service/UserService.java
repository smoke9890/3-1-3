package web.Service;


import web.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    void removeUserById(Long id);
    User getUserById(Long id);
    void edit(User user);
}
