package org.desarrollo.repository;

import org.desarrollo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomUser(String nomUser);
    List<Usuario> findByApellidoUsuarioContainingIgnoreCase(String apellidoUsuario);
    List<Usuario> findByNomUserContainingIgnoreCase(String nomUser);
}
