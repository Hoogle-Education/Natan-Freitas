package com.natan.hello.service;

import com.natan.hello.domain.dto.NewUserDto;
import com.natan.hello.domain.dto.UserProfileDto;
import com.natan.hello.domain.model.User;
import com.natan.hello.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Page<UserProfileDto> getAll(Pageable pagination) {
        return userRepository.findAll(pagination)
                .map(UserProfileDto::new); // u -> new UserProfile(u)
    }

    public UserProfileDto getById(Long id) {
        var searchUserById = userRepository.findById(id);

        if(searchUserById.isEmpty()) {
            return null;
        }

        var user = searchUserById.get();
        return new UserProfileDto(user);
    }

    public UserProfileDto save(NewUserDto newUser) {

        var isUsernameOrEmailAlreadyUsed = userRepository.findAll()
                .stream()
                .anyMatch(u -> {
                    var isEmailUsed = u.getEmail().equalsIgnoreCase(newUser.getEmail());
                    var isUsernameUsed = u.getUsername().equalsIgnoreCase(newUser.getUsername());
                    return isEmailUsed || isUsernameUsed;
                });

        if(isUsernameOrEmailAlreadyUsed) {
            return null;
        }

        var user = toUser(newUser);
        var savedUser = userRepository.save(user);
        return new UserProfileDto(savedUser);
    }

    private User toUser(NewUserDto newUser) {
        var user = new User();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        return user;
    }
}
