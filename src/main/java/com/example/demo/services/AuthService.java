package com.example.demo.services;

import com.example.demo.dto.auth.AuthMeDTO;
import com.example.demo.dto.auth.ChangePasswordDTO;
import com.example.demo.dto.auth.LoginRequestDTO;
import com.example.demo.dto.auth.LoginResponseDTO;
import com.example.demo.entities.Employe;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

    AuthMeDTO me(String login);

    void changePassword(String login, ChangePasswordDTO dto);

    Employe getCurrentEmploye(String login);
}