package rares.web.ecommece.service;

import rares.web.ecommece.entities.User;
import rares.web.ecommece.model.UserDTO;

public interface UserService {
    User registerNewUser(UserDTO userDTO);
}
