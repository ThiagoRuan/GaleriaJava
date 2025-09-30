package com.galeria.art.galeria_api.services;

import com.galeria.art.galeria_api.dto.LoginDTO;
import com.galeria.art.galeria_api.dto.UserDTO;
import com.galeria.art.galeria_api.dto.UserRegisterDTO;
import com.galeria.art.galeria_api.exceptions.EmailAlreadyExistsException;
import com.galeria.art.galeria_api.models.User;
import com.galeria.art.galeria_api.repositories.UserRepository;
import com.galeria.art.galeria_api.securities.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//    }

    public UserDTO registrarUsuario(UserRegisterDTO userDTO) {

        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            throw new EmailAlreadyExistsException("O email '" + userDTO.getEmail() + "' já está em uso.");
        });

        User novoUsuario = new User();

        novoUsuario.setNome(userDTO.getUsuario());
        novoUsuario.setEmail(userDTO.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        User userCriado = userRepository.save(novoUsuario);
        return modelMapper.map(userCriado, UserDTO.class);
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