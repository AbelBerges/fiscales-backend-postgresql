package org.desarrollo.model;


import jakarta.persistence.*;

@Table(name = "calle")
@Entity(name = "Calle")
public class Calle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calle")
    private Integer idCalle;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activa", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activa = true;


    public Calle() {

    }
    public Calle(String nombre, boolean activa) {
        this.nombre = nombre;
        this.activa = activa;
    }

    public Calle(Integer idCalle, String nombre, boolean activa) {
        this.idCalle = idCalle;
        this.nombre = nombre;
        this.activa = activa;
    }

    public Integer getIdCalle() {
        return idCalle;
    }

    public void setIdCalle(Integer idCalle) {
        this.idCalle = idCalle;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activo) {
        this.activa = activo;
    }
}
