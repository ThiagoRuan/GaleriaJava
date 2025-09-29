package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.LoginDTO;
import com.galeria.art.galeria_api.dto.UserRegisterDTO;
import com.galeria.art.galeria_api.exceptions.EmailAlreadyExistsException;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.repositories.UserRepository;
import com.galeria.art.galeria_api.securities.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registrarUsuario(UserRegisterDTO userDTO) {
        Optional<User> userExistente = userRepository.findByEmail(userDTO.getEmail());

        if (userExistente.isPresent()) {
            throw new EmailAlreadyExistsException("O email '" + userDTO.getEmail() + "' já está em uso.");
        }

        User novoUsuario = new User();

        novoUsuario.setNome(userDTO.getUsuario());
        novoUsuario.setEmail(userDTO.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        userRepository.save(novoUsuario);
    }

    public String autenticarUsuario(LoginDTO loginDto) {
        var usuario = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email e/ou Senha inválido(s)."));

        if (!passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("Email e/ou senha inválido(s).");
        }

        return jwtService.generateToken(usuario);
    }
}