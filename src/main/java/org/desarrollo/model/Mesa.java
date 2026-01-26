package org.desarrollo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "mesa")
@Entity(name = "Mesa")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Integer idMesa;
    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_establecimiento", nullable = true)
    private Establecimiento establecimiento;
    @OneToMany(mappedBy = "mesa")
    private List<Fiscal> fiscales = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_basica", referencedColumnName = "id_basica", nullable = true)
    private Basica basica;

    public Mesa() {}

    public Mesa(Integer numeroMesa, Establecimiento establecimiento, Fiscal fiscal) {
        this.numeroMesa = numeroMesa;
        this.establecimiento = establecimiento;
    }

    public Integer getIdMesa() {
        return this.idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumeroMesa() {
        return this.numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Establecimiento getEstablecimiento() {
        return this.establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public List<Fiscal> getFiscales() {
        return fiscales;
    }

    public void setFiscales(List<Fiscal> fiscales) {
        this.fiscales = fiscales;
    }

    public Basica getBasica() {
        return basica;
    }

    public void setBasica(Basica basica) {
        this.basica = basica;
    }
}
