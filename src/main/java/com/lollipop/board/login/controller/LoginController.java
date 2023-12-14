package com.lollipop.board.login.controller;

import com.lollipop.board.login.model.LoginDTO;
import com.lollipop.board.login.model.LoginParam;
import com.lollipop.board.login.service.LoginService;
import com.lollipop.board.user.model.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody @Valid LoginParam loginParam) {
        LoginDTO loginDTO = loginService.login(loginParam);
        return ResponseEntity.ok().body(loginDTO);
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO){
        loginService.signUp(userDTO);
        return ResponseEntity.ok().body(null);
    }

}
