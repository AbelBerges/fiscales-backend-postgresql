package org.desarrollo.config;

import org.desarrollo.enumeradores.Rol;
import org.desarrollo.model.Usuario;

public class ContextoUsuario {

    private static final ThreadLocal<UsuarioActual> ACTUAL = new ThreadLocal<>();

    public record UsuarioActual(Integer idUsuario, Rol rol, Integer idBasica) {}

    public static void set(UsuarioActual usuarioActual) {
        ACTUAL.set(usuarioActual);
    }

    public static UsuarioActual getUsuarioActual() {
        return ACTUAL.get();
    }

    public static Integer getBasica() {
        if (esAdminGlobal()) return null;
        return ACTUAL.get() != null ? ACTUAL.get().idBasica() : null;
    }

    public static Integer getUsuario() {
        return ACTUAL.get() != null ? ACTUAL.get().idUsuario() :null;
    }

    public static boolean esAdminGlobal() {
        UsuarioActual u = ACTUAL.get();
        return u != null && u.rol() == Rol.ADMIN_GLOBAL;
    }

    public static void clear() {
        ACTUAL.remove();
    }
}
