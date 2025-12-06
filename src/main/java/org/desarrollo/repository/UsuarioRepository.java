package org.desarrollo.repository;

import org.desarrollo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByApellidoUsuarioContainingIgnoreCase(String apellidoUsuario);

    List<Usuario> findByNomUserContainingIgnoreCase(String nomUser);
}
