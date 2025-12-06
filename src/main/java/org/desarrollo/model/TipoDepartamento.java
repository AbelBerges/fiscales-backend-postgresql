package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "tipo_departamento")
@Entity(name = "TipoDepartamento")
public class TipoDepartamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Integer idDepartamento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    public TipoDepartamento() {}

    public TipoDepartamento(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    public TipoDepartamento(Integer idDepartamento, String nombre, boolean activo) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
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
