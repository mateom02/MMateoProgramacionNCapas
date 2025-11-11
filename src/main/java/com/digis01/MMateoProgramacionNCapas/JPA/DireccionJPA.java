package com.digis01.MMateoProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DIRECCION")
public class DireccionJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddirecion")
    private int IdDireccion;

    @Column(name = "calle")
    private String Calle;

    @Column(name = "numerointerior")
    private String NumeroInterior;

    @Column(name = "numeroexterior")
    private String NumeroExterior;

    @ManyToOne
    @JoinColumn(name="idcolonia")
    public ColoniaJPA Colonia;
    
    @ManyToOne
    @JoinColumn(name = "idusuario")
    public UsuarioJPA Usuario;

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public UsuarioJPA getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioJPA Usuario) {
        this.Usuario = Usuario;
    }

    public ColoniaJPA getColonia() {
        return Colonia;
    }

    public void setColonia(ColoniaJPA Colonia) {
        this.Colonia = Colonia;
    }

  

}
