package web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import web.repository.RoleRepository;
import web.model.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    final RoleRepository roleDao;
@Autowired
    public RoleServiceImpl(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByRoleName(String name) {
        return roleDao.getAuthByName(name);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role getAuthByName(String role) {
        return roleDao.getAuthByName(role);
    }

    @Override
    public HashSet<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roleNames) {
            roleSet.add(getRoleByRoleName(role));
        }
        return (HashSet) roleSet;
    }

    @Override
    public void addRole(Role role){
        roleDao.save(role);
    }
}
