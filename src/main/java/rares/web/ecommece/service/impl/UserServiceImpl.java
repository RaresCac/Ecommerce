package rares.web.ecommece.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rares.web.ecommece.entities.Role;
import rares.web.ecommece.entities.User;
import rares.web.ecommece.exception.RegisterException;
import rares.web.ecommece.model.UserDTO;
import rares.web.ecommece.repository.RoleRepository;
import rares.web.ecommece.repository.UserRepository;
import rares.web.ecommece.service.UserService;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(UserDTO userDTO) throws RegisterException {
        if (usernameExists(userDTO.getUsername()) || emailExists(userDTO.getEmail()))
            throw new RegisterException("User exists with the same username or email address");

        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword()))
            throw new RegisterException("Passwords do not match");

        //Register the roles in the database if there are none
        if (roleRepository.count() == 0){
            Role admin = new Role("ADMIN");
            Role user = new Role("USER");
            roleRepository.save(user);
            roleRepository.save(admin);
        }

        Role userRole = roleRepository.findByName(userDTO.getRole());

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userRole);

        return userRepository.save(user);
    }

    private boolean usernameExists(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean emailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
