package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "tipo_establecimiento")
@Entity(name = "TipoEstablecimiento")
public class TipoEstablecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_establecimiento")
    private Integer idTipoEstablecimiento;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    public TipoEstablecimiento() {}

    public TipoEstablecimiento(String tipo, boolean activo) {
        this.tipo = tipo;
        this.activo = activo;
    }

    public TipoEstablecimiento(Integer idTipoEstablecimiento, String tipo, boolean activo) {
        this.idTipoEstablecimiento = idTipoEstablecimiento;
        this.tipo = tipo;
        this.activo = activo;
    }

    public Integer getIdTipoEstablecimiento() {
        return idTipoEstablecimiento;
    }

    public void setIdTipoEstablecimiento(Integer idTipoEstablecimiento) {
        this.idTipoEstablecimiento = idTipoEstablecimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
