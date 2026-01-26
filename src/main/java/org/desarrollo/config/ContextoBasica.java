package org.desarrollo.config;

import org.desarrollo.config.ContextoUsuario;

public class ContextoBasica {
    public static Integer get() {
        return ContextoUsuario.getBasica();
    }
}
