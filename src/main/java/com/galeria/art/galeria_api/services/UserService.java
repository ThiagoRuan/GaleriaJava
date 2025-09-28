package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.LoginDTO;
import com.galeria.art.galeria_api.dto.UserRegisterDTO;
import com.galeria.art.galeria_api.exceptions.EmailAlreadyExistsException;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public User autenticarUsuario(LoginDTO loginDto) {
        var usuario = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos."));

        if (!passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("Email ou senha inválidos.");
        }

        return usuario;
    }
}