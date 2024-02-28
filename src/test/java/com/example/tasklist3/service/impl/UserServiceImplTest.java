package com.example.tasklist3.service.impl;

import com.example.tasklist3.config.TestConfig;
import com.example.tasklist3.domain.exception.ResourceNotFoundException;
import com.example.tasklist3.domain.user.Role;
import com.example.tasklist3.domain.user.User;
import com.example.tasklist3.repository.TaskRepository;
import com.example.tasklist3.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void getById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        User testUser = userService.getById(id);

        Mockito.verify(userRepository).findById(id);
        Assertions.assertEquals(user, testUser);
    }

    @Test
    void getByIdWithNotExistsId() {
        Long id = 1L;
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(id));
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    void getByUsername() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        User testUser = userService.getByUsername(username);

        Mockito.verify(userRepository).findByUsername(username);
        Assertions.assertEquals(user, testUser);
    }

    @Test
    void getByUsernameWithNotExistsUsername() {
        String username = "username";
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUsername(username));
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void update() {
        String password = "password";
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));

        userService.update(user);

        Mockito.verify(userRepository).save(user);
        Mockito.verify(passwordEncoder).encode(password);
    }

    @Test
    void isTakOwner() {
        Long userId = 1L;
        Long taskId = 1L;
        Mockito.when(userRepository.isTaskOwner(userId, taskId))
                .thenReturn(true);

        boolean isOwner = userService.isTaskOwner(userId, taskId);

        Mockito.verify(userRepository).isTaskOwner(userId, taskId);
        Assertions.assertTrue(isOwner);
    }

    @Test
    void create() {
        String username = "username";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        User testUser = userService.create(user);

        Mockito.verify(userRepository).save(user);
        Mockito.verify(passwordEncoder).encode(password);
        Assertions.assertEquals(Set.of(Role.ROLE_USER), testUser.getRoles());
    }

    @Test
    void createWithExistingUsername() {
        String username = "username";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThrows(IllegalStateException.class,
                () -> userService.create(user));

        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void createWithDifferentPasswords() {
        String username = "username";
        String password = "password";
        String passwordConfirmation = "passwordConfirmation";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(passwordConfirmation);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class,
                () -> userService.create(user));

        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void delete() {
        Long id = 1L;

        userService.delete(id);

        Mockito.verify(userRepository).deleteById(id);
    }

}
