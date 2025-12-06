package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "tipo_piso")
@Entity(name = "TipoPiso")
public class TipoPiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piso")
    private Integer idPiso;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    public TipoPiso() {}

    public TipoPiso(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    public TipoPiso(Integer idPiso, String nombre, boolean activo) {
        this.idPiso = idPiso;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Integer getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(Integer idPiso) {
        this.idPiso = idPiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
