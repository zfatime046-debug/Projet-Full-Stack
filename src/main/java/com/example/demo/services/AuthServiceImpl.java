package com.example.demo.services;

import com.example.demo.config.CustomUserDetailsService;
import com.example.demo.config.JwtService;
import com.example.demo.dto.auth.AuthMeDTO;
import com.example.demo.dto.auth.ChangePasswordDTO;
import com.example.demo.dto.auth.LoginRequestDTO;
import com.example.demo.dto.auth.LoginResponseDTO;
import com.example.demo.entities.Employe;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final EmployeRepository employeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getLogin());
        String token = jwtService.generateToken(userDetails);

        Employe employe = employeRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new ResourceNotFoundException("Employé introuvable"));

        String role = "USER";
        if (employe.getProfil() != null && employe.getProfil().getCode() != null) {
            role = employe.getProfil().getCode();
        }

        return new LoginResponseDTO(
                token,
                "Bearer",
                employe.getLogin(),
                role
        );
    }

    @Override
    public AuthMeDTO me(String login) {
        Employe employe = getCurrentEmploye(login);

        String role = "USER";
        if (employe.getProfil() != null && employe.getProfil().getCode() != null) {
            role = employe.getProfil().getCode();
        }

        return new AuthMeDTO(
                employe.getId(),
                employe.getLogin(),
                employe.getEmail(),
                role
        );
    }

    @Override
    public void changePassword(String login, ChangePasswordDTO dto) {
        Employe employe = getCurrentEmploye(login);

        if (!passwordEncoder.matches(dto.getOldPassword(), employe.getPassword())) {
            throw new BadRequestException("Ancien mot de passe incorrect");
        }

        if (dto.getNewPassword() == null || dto.getNewPassword().length() < 6) {
            throw new BadRequestException("Le nouveau mot de passe doit contenir au moins 6 caractères");
        }

        employe.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        employeRepository.save(employe);
    }

    @Override
    public Employe getCurrentEmploye(String login) {
        return employeRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
    }
}