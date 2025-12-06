package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "direccion")
@Entity(name = "Direccion")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Integer idDireccion;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_calle", referencedColumnName = "id_calle")
    private Calle calle;
    private int altura;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_piso", referencedColumnName = "id_piso")
    private TipoPiso tipoPiso;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id_departamento")
    private TipoDepartamento tipoDepartamento;

    public Direccion() {

    }

    public Direccion(Calle calle, int altura ,TipoPiso tipoPiso, TipoDepartamento tipoDepartamento) {
        this.calle = calle;
        this.altura = altura;
        this.tipoPiso = tipoPiso;
        this.tipoDepartamento = tipoDepartamento;
    }

    public Direccion(Integer idDireccion, Calle calle, int altura, TipoPiso tipoPiso, TipoDepartamento tipoDepartamento) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.altura = altura;
        this.tipoPiso = tipoPiso;
        this.tipoDepartamento = tipoDepartamento;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Calle getCalle() {
        return calle;
    }

    public void setCalle(Calle calle) {
        this.calle = calle;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public TipoPiso getTipoPiso() {
        return tipoPiso;
    }

    public void setTipoPiso(TipoPiso tipoPiso) {
        this.tipoPiso = tipoPiso;
    }

    public TipoDepartamento getTipoDepartamento() {
        return tipoDepartamento;
    }

    public void setTipoDepartamento(TipoDepartamento tipoDepartamento) {
        this.tipoDepartamento = tipoDepartamento;
    }
}
