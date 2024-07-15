package pelusadev.ProyectoBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pelusadev.ProyectoBlog.model.Role;
import pelusadev.ProyectoBlog.model.UserSec;
import pelusadev.ProyectoBlog.repository.IRoleRepository;
import pelusadev.ProyectoBlog.repository.IUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserSec getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSec createUserSec(UserSec userSec) {
        Set<Role> roles = new HashSet<>();
        for (Role role : userSec.getRolesList()) {
            Role existingRole = roleRepository.findById(role.getId())
                    .orElseThrow(() -> new NoSuchElementException("Role not found with id " + role.getId()));
            roles.add(existingRole);
        }
        userSec.setRolesList(roles);
        return userRepository.save(userSec);
    }


    @Override
    public UserSec updateUserSec(UserSec userSec, Long id) {
        UserSec existingUserSec = this.getById(id);

        if (existingUserSec != null) {
            // Actualizar propiedades básicas del usuario
            existingUserSec.setUsername(userSec.getUsername());
            existingUserSec.setPassword(userSec.getPassword());
            existingUserSec.setEnabled(userSec.isEnabled());
            existingUserSec.setAccountNotExpired(userSec.isAccountNotExpired());
            existingUserSec.setAccountNotLocked(userSec.isAccountNotLocked());
            existingUserSec.setCredentialNotExpired(userSec.isCredentialNotExpired());

            // Actualizar roles del usuario
            Set<Role> updatedRoles = new HashSet<>();

            for (Role role : userSec.getRolesList()) {
                Role existingRole = roleRepository.getById(role.getId());
                if (existingRole != null) {
                    updatedRoles.add(existingRole); // Agregar roles existentes al usuario
                } else {
                    // Manejar el caso donde el rol no existe, si es necesario
                    // Aquí podrías decidir cómo manejar este escenario (por ejemplo, lanzar una excepción)
                }
            }

            existingUserSec.setRolesList(updatedRoles); // Asignar los roles actualizados al usuario

            return userRepository.save(existingUserSec); // Guardar usuario actualizado
        } else {
            return null; // Manejar caso donde el usuario no existe
        }
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


}
