package web.Service;

import web.model.Role;

import java.util.HashSet;
import java.util.List;

public interface RoleService {
    void addRole(Role role);
    Role getRoleByRoleName(String name);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
    Role getAuthByName(String role);
    HashSet<Role> getSetOfRoles(String[] roleNames);
}
