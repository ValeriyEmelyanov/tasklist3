package com.example.tasklist3.service.impl;

import com.example.tasklist3.config.TestConfig;
import com.example.tasklist3.domain.exception.ResourceNotFoundException;
import com.example.tasklist3.domain.user.Role;
import com.example.tasklist3.domain.user.User;
import com.example.tasklist3.repository.TaskRepository;
import com.example.tasklist3.repository.UserRepository;
import com.example.tasklist3.service.UserService;
import com.example.tasklist3.web.dto.auth.JwtRequest;
import com.example.tasklist3.web.dto.auth.JwtResponse;
import com.example.tasklist3.web.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthServiceImpl authService;

    @Test
    void login() {
        Long id = 1L;
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setRoles(roles);
        Mockito.when(userService.getByUsername(username))
                .thenReturn(user);
        Mockito.when(tokenProvider.createAccessToken(id, username, roles))
                .thenReturn(accessToken);
        Mockito.when(tokenProvider.createRefreshToken(id, username))
                .thenReturn(refreshToken);

        JwtResponse response = authService.login(request);

        Mockito.verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        Assertions.assertEquals(username, response.getUsername());
        Assertions.assertEquals(id, response.getId());
        Assertions.assertNotNull(response.getAccessToken());
        Assertions.assertNotNull(response.getRefreshToken());
    }

    @Test
    void loginWithIncorrectedUserName() {
        String username = "username";
        String password = "password";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        Mockito.when(userService.getByUsername(username))
                .thenThrow(ResourceNotFoundException.class);

        Mockito.verifyNoInteractions(tokenProvider);
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> authService.login(request));
    }

    @Test
    void refresh() {
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        String newRefreshToken = "newRefreshToken";
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(newRefreshToken);
        Mockito.when(tokenProvider.refreshUserTokens(refreshToken))
                .thenReturn(response);

        JwtResponse testResponse = authService.refresh(refreshToken);

        Mockito.verify(tokenProvider).refreshUserTokens(refreshToken);
        Assertions.assertEquals(response, testResponse);
    }

}
