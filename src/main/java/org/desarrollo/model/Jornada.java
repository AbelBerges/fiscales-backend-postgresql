package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "jornada")
@Entity(name = "Jornada")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jornada")
    private Integer idJornada;
    @Column(name = "tipo_jornada")
    private String tipoJornada;

    public Jornada() {

    }
    public Jornada(Integer idJornada, String tipoJornada) {
        this.idJornada = idJornada;
        this.tipoJornada = tipoJornada;
    }

    public Integer getIdJornada() {
        return this.idJornada;
    }

    public void setIdJornada(Integer idJornada) {
        this.idJornada = idJornada;
    }

    public String getTipoJornada() {
        return this.tipoJornada;
    }

    public void setTipoJornada(String tipoJornada) {
        this.tipoJornada = tipoJornada;
    }
}
