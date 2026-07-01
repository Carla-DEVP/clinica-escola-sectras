package com.entec.clinicaescola.service;

import com.entec.clinicaescola.domain.Usuario;
import com.entec.clinicaescola.dto.LoginRequestDTO;
import com.entec.clinicaescola.dto.LoginResponseDTO;
import com.entec.clinicaescola.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        // busca o usuário pelo EMAIL
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário Não Encontrado"));

        // verifica se o usuário está ativo
        if (!usuario.isAtivo()) {
            throw new RuntimeException("Usuário Inativo");
        }
        // verificando a senha (por enquanto simples, depois vira BCrypt)
        if (!usuario.getSenha().equals(request.getSenha())) {
            throw new RuntimeException("Senha Inválida");
        }
        // gera o token (por enquanto placeholder, depois vira JWT)
        String token = "token-provisório";

        return new LoginResponseDTO(token, usuario.getRole().name());
    }
}
