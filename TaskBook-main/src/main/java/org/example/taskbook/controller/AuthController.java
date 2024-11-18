package org.example.taskbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.taskbook.dto.ReqResDto;
import org.example.taskbook.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Operation(summary = "Регистрация пользователя/выдача ролей", description = "Регистрация пользователя")
    @PostMapping("/signup")
    public ResponseEntity<ReqResDto> signUp(@RequestBody ReqResDto signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @Operation(summary = "Логин", description = "Аутентификация пользователя/выдача ролей")
    @PostMapping("/signin")
    public ResponseEntity<ReqResDto> signIn(@RequestBody ReqResDto signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @Operation(summary = "Обновление токена")
    @PostMapping("/refresh")
    public ResponseEntity<ReqResDto> refreshToken(@RequestBody ReqResDto refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }


}
