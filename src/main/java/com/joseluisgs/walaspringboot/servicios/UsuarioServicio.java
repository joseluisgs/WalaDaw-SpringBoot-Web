package com.joseluisgs.walaspringboot.servicios;

import com.joseluisgs.walaspringboot.modelos.Usuario;
import com.joseluisgs.walaspringboot.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Usamos el Servicio de Usuario para mapear los métdos que vamos a usar, con otro nombre
 * O deseamos implementarlos y no formen parte de los repositorios
 */
@Service
public class UsuarioServicio {

    // Indicamos el repositorio a usar
    @Autowired
    UsuarioRepository repositorio;

    // Para el password
    @Autowired
    BCryptPasswordEncoder passwordEncoder;





    // Usamos el metodos registrar para salvar a un usuario con el password encriptado
    // Por defecto el Repositorio lo salva plano, por eso lo mapeamos
    public Usuario registrar(Usuario u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repositorio.save(u);
    }

    // Mapeamos el findById para que devuelve el usuario o nulo
    public Usuario findById(long id) {
        return repositorio.findById(id).orElse(null);
    }

    //Mapeamos el método para facilidad de sintaxis
    public Usuario buscarPorEmail(String email) {
        return repositorio.findFirstByEmail(email);
    }

}
