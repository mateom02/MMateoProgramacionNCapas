package com.digis01.MMateoProgramacionNCapas.ML;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Direccion {

    private int IdDireccion;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ\\. ]+ ?[A-Za-zñÑáéíóúÁÉÍÓÚ\\. ]+$", message = "No se permite caracteres especiales, solo letras")
    private String Calle;

    private String NumeroInterior;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @Pattern(regexp = "^[a-zA-Z#\\d ]+$", message = "Ingrese el numero de la calle")
    private String NumeroExterior;

    public Colonia Colonia;


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

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }

}
