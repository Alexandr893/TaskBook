package org.example.taskbook.service;

import org.example.taskbook.dao.entity.User;
import org.example.taskbook.dao.repository.UserRepository;
import org.example.taskbook.dto.ReqResDto;
import org.example.taskbook.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private ReqResDto req;

    @BeforeEach
    void setUp() {
        req = new ReqResDto();
        req.setEmail("test@example.com");
        req.setPassword("password");
        req.setRole("USER");
    }

    @Test
    public void testSignUp_Success() {
        User user = new User();
        user.setId(1L);

        when(passwordEncoder.encode(req.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ReqResDto response = authService.signUp(req);

        assertEquals(200, response.getStatusCode());
        assertEquals("Пользователь сохранен", response.getMessage());

        // Ensure that save was called exactly once
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
   public void testSignIn_Success() {
        User user = new User();
        user.setEmail(req.getEmail());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(req.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtils.generateToken(user)).thenReturn("jwtToken");
        when(jwtUtils.generateRefreshToken(any(), eq(user))).thenReturn("refreshToken");

        ReqResDto response = authService.signIn(req);

        assertEquals(200, response.getStatusCode());
        assertEquals("jwtToken", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("Пользователь вошел", response.getMessage());
    }



}