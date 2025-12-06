package org.desarrollo.model;

import jakarta.persistence.*;

import java.awt.*;
import java.util.Date;

@Table(name = "usuario")
@Entity(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "nom_user")
    private String nomUser;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;
    @Column(name = "clave")
    private String clave;
    @Column(name = "edad")
    private int edad;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;
    @Column(name = "fecha_acceso")
    private Date fechaAcceso;

    public Usuario() {}

    public Usuario(String nomUser, String nombreUsuario, String apellidoUsuario, String clave, int edad, String correo,
                   String telefono, boolean activo, Date fechaAcceso) {
        this.nomUser = nomUser;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.clave = clave;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo;
        this.fechaAcceso = fechaAcceso;
    }

    public Usuario(Integer idUsuario, String nomUser, String nombreUsuario, String apellidoUsuario, String clave,
                   int edad, String correo, String telefono, boolean activo, Date fechaAcceso) {
        this.idUsuario = idUsuario;
        this.nomUser = nomUser;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.clave = clave;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo;
        this.fechaAcceso = fechaAcceso;
    }


    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return this.activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaAcceso() {
        return this.fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }


}
