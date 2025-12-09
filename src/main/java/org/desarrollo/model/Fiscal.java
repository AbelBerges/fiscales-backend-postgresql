package org.desarrollo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "fiscal")
@Entity(name = "Fiscal")
public class Fiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fiscal")
    private Integer idFiscal;
    @Column(name = "nombre_fiscal", nullable = false)
    private String nombreFiscal;
    @Column(name = "apellido_fiscal", nullable = false)
    private String apellidoFiscal;
    @Column(name = "edad_fiscal", nullable = false)
    private int edadFiscal;
    @Column(name = "correo_fiscal", nullable = false, unique = true)
    private String correoFiscal;
    @Column(name = "telefono", nullable = false, unique = true)
    private String telefono;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_fiscal", referencedColumnName = "id_tipo_fiscal", nullable = false)
    private TipoFiscal tipoFiscal;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean activo = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_establecimiento_votacion", referencedColumnName = "id_establecimiento")
    private Establecimiento establecimientoVoto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_establecimiento_asignado", referencedColumnName = "id_establecimiento")
    private Establecimiento establecimientoAsignado;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    private Direccion direccion;
    @OneToOne(mappedBy = "fiscal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FotoFiscal foto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jornada", referencedColumnName = "id_jornada")
    private Jornada jornada;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_mesa")
    private Mesa mesa;


    public Fiscal() {}

    public Fiscal(String nombreFiscal, String apellidoFiscal, int edadFiscal, String correoFiscal, String telefono,
                  TipoFiscal tipoFiscal, boolean activo,
                  Establecimiento establecimientoVoto, Establecimiento establecimientoAsignado, Direccion direccion, FotoFiscal foto) {
        this.nombreFiscal = nombreFiscal;
        this.apellidoFiscal = apellidoFiscal;
        this.edadFiscal = edadFiscal;
        this.correoFiscal = correoFiscal;
        this.telefono = telefono;
        this.tipoFiscal = tipoFiscal;
        this.activo = activo;
        this.establecimientoVoto = establecimientoVoto;
        this.establecimientoAsignado = establecimientoAsignado;
        this.direccion = direccion;
        this.foto = foto;
    }

    public Fiscal(Integer idFiscal, String nombreFiscal, String apellidoFiscal, int edadFiscal, String correoFiscal,
                  String telefono, TipoFiscal tipoFiscal, boolean activo,
                  Establecimiento establecimientoVoto, Establecimiento establecimientoAsignado, Direccion direccion, FotoFiscal foto) {
        this.idFiscal = idFiscal;
        this.nombreFiscal = nombreFiscal;
        this.apellidoFiscal = apellidoFiscal;
        this.edadFiscal = edadFiscal;
        this.correoFiscal = correoFiscal;
        this.telefono = telefono;
        this.tipoFiscal = tipoFiscal;
        this.activo = activo;
        this.establecimientoVoto = establecimientoVoto;
        this.establecimientoAsignado = establecimientoAsignado;
        this.direccion = direccion;
        this.foto = foto;
    }

    public Integer getIdFiscal() {
        return idFiscal;
    }

    public void setIdFiscal(Integer idFiscal) {
        this.idFiscal = idFiscal;
    }

    public String getNombreFiscal() {
        return nombreFiscal;
    }

    public void setNombreFiscal(String nombreFiscal) {
        this.nombreFiscal = nombreFiscal;
    }

    public String getApellidoFiscal() {
        return apellidoFiscal;
    }

    public void setApellidoFiscal(String apellidoFiscal) {
        this.apellidoFiscal = apellidoFiscal;
    }

    public int getEdadFiscal() {
        return edadFiscal;
    }

    public void setEdadFiscal(int edadFiscal) {
        this.edadFiscal = edadFiscal;
    }

    public String getCorreoFiscal() {
        return correoFiscal;
    }

    public void setCorreoFiscal(String correoFiscal) {
        this.correoFiscal = correoFiscal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoFiscal getTipoFiscal() {
        return tipoFiscal;
    }

    public void setTipoFiscal(TipoFiscal tipoFiscal) {
        this.tipoFiscal = tipoFiscal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Establecimiento getEstablecimientoVoto() {
        return establecimientoVoto;
    }

    public void setEstablecimientoVoto(Establecimiento establecimientoVoto) {
        this.establecimientoVoto = establecimientoVoto;
    }

    public Establecimiento getEstablecimientoAsignado() {
        return establecimientoAsignado;
    }

    public void setEstablecimientoAsignado(Establecimiento establecimientoAsignado) {
        this.establecimientoAsignado = establecimientoAsignado;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public FotoFiscal getFoto() {
        return this.foto;
    }

    public void setFoto(FotoFiscal foto) {
        this.foto = foto;
    }

    public Jornada getJornada() {
        return this.jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
}
