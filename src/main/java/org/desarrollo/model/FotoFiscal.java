package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "foto_fiscal")
@Entity(name = "FotoFiscal")
public class FotoFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Integer idFoto;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @OneToOne
    @JoinColumn(name = "id_fiscal", referencedColumnName = "id_fiscal")
    private Fiscal fiscal;

    public FotoFiscal() {}

    public FotoFiscal(byte[] imagen, Fiscal fiscal) {
        this.imagen = imagen;
        this.fiscal = fiscal;
    }

    public FotoFiscal(Integer idFoto, byte[] imagen, Fiscal fiscal) {
        this.idFoto = idFoto;
        this.imagen = imagen;
        this.fiscal = fiscal;
    }



    public Integer getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Fiscal getFiscal() {
        return fiscal;
    }

    public void setFiscal(Fiscal fiscal) {
        this.fiscal = fiscal;
    }
}
