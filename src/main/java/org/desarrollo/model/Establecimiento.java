package org.desarrollo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "establecimiento")
@Entity(name = "Establecimiento")
public class Establecimiento {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_establecimiento")
    private Integer idEstablecimiento;
    @Column(name = "nombre_establecimiento")
    private String nombreEstablecimiento;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mesa> mesas = new ArrayList<>();
    @OneToMany(mappedBy = "establecimientoAsignado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fiscal> fiscales = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    private Direccion direccion;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_tipo_establecimiento", referencedColumnName = "id_tipo_establecimiento")
    private TipoEstablecimiento tipoEstablecimiento;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    public Establecimiento() {}

    public Establecimiento(String nombreEstablecimiento, String descripcion, Direccion direccion,
                           TipoEstablecimiento tipoEstablecimiento, boolean activo) {
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.activo = activo;
    }

    public Establecimiento(Integer idEstablecimiento, String nombreEstablecimiento, String descripcion,
                           Direccion direccion, TipoEstablecimiento tipoEstablecimiento, boolean activo) {
        this.idEstablecimiento = idEstablecimiento;
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.activo = activo;
    }

    public Integer getIdEstablecimiento() {
        return idEstablecimiento;
    }

    public void setIdEstablecimiento(Integer idEstablecimiento) {
        this.idEstablecimiento = idEstablecimiento;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    public List<Fiscal> getFiscales() {
        return fiscales;
    }

    public void setFiscales(List<Fiscal> fiscales) {
        this.fiscales = fiscales;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public TipoEstablecimiento getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
