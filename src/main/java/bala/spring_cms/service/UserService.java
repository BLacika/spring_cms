package bala.spring_cms.service;

import bala.spring_cms.model.User;
import bala.spring_cms.repository.RoleRepository;
import bala.spring_cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Get all user
     */
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Get one user by id
     */
    public User getOneById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Get user by name
     */
    public User getOneByName(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Create a user
     */
    public void createUser(User newUser, MultipartFile image) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());

        byte[] file;
        try {
            file = image.getBytes();
            newUser.setFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userRepository.save(newUser);
    }

    /**
     * Update user
     */
    public void updateUser(Long id, User updatedUser, MultipartFile image) {
        var userToUpdate = userRepository.findById(id);

        updatedUser.setUpdatedAt(new Date());

        if (image.isEmpty() || image == null) {
            updatedUser.setFile(userToUpdate.get().getFileInBytes());

            userRepository.save(updatedUser);
        } else {
            try {
                updatedUser.setFile(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userRepository.save(updatedUser);
        }
    }
}
