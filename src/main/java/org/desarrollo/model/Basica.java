package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "basica")
@Entity(name = "Basica")
public class Basica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_basica")
    Integer idBasica;
    @Column(name = "nombre")
    String nombre;
    @Column(name = "activa", columnDefinition = "BOOLEAN DEFAULT TRUE")
    Boolean activa;


    public Integer getIdBasica() {
        return idBasica;
    }

    public void setIdBasica(Integer idBasica) {
        this.idBasica = idBasica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
