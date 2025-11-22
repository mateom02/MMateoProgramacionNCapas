package com.digis01.MMateoProgramacionNCapas.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras")
    @Pattern(regexp = "^[A-Za-z_Ññ]+$", message = "Solo se permiten letras, numeros y _")
    @Size(min = 2, max = 50, message = "El username debe tener entre 2 y 50 caracteres")
    private String UserName;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-zñáéíóúÁÉÍÓÚ]+(\\s[A-Za-zñáéíóúÁÉÍÓÚ]+)*$", message = "No se permite caracteres especiales, solo letras")
    private String Nombre;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras")
    @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-zñáéíóúÁÉÍÓÚ]+(\\s[A-Za-zñáéíóúÁÉÍÓÚ]+)*$", message = "No se permite caracteres especiales, solo letras")
    private String ApellidoPaterno;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras")//repetitivo?
    @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-zñáéíóúÁÉÍÓÚ]+(\\s[A-Za-zñáéíóúÁÉÍÓÚ]+)*$", message = "No se permite caracteres especiales, solo letras")
    private String ApellidoMaterno;

    @Email(message = "El email puede tener letras, numeros y _ con el sigueinte formato xxxx@xxxx.xxxx", regexp = "^[A-Za-zÑñ\\d_]+@[A-Za-zÑñ]+.[a-zA-ZÑñ]+$")
    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    private String Email;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras")//repetitivo?
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Z+\\d+a-z]{8,50}$", message = "La contraseña debe tener al menos una mayuscula, un numero y al menos 8 de longitud")
    private String Password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "El campo no puede estar vacio")
    @Past(message = "Debe ser una fecha pasada")
    private Date FechaNacimiento;

    @NotNull(message = "El campo no puede estar vacio")
    private String Sexo;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @Pattern(regexp = "\\+*([0-9]{2,3})*\\s*[0-9]{10}$", message = "Solo se permiten numeros")
    private String Telefono;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @Pattern(regexp = "\\+*([0-9]{2,3})*\\s*[0-9]{10}", message = "Solo se permiten numeros")
    private String Celular;

    @NotNull(message = "El campo no puede ser nulo")
    @NotEmpty(message = "El campo no puede estar vacio ")
    @NotBlank(message = "El campo debe contener solo letras y números")
    @Pattern(regexp = "^[A-Za-zÑñ\\d]{18}$", message = "Solo se permiten 18 caracteres (letras y numeros)")
    private String Curp;

    private String Imagen;
    
    @Valid
    public Rol Rol; //Propiedad de navegación

    public List<@Valid Direccion> Direcciones;

    public Usuario() {
    }

    public Usuario(String UserName, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Email, String Password, Date FechaNacimiento, String Sexo, String Telefono, String Celular, String Curp) {
        this.UserName = UserName;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Email = Email;
        this.Password = Password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.Curp = Curp;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    @Override
    public String toString() {
        return "Usuario{" + "IdUsuario=" + IdUsuario + ", UserName=" + UserName + ", Nombre="
                + Nombre + ", ApellidoPaterno=" + ApellidoPaterno + ", ApellidoMaterno=" + ApellidoMaterno
                + ", Email=" + Email + ", Password=" + Password + ", FechaNacimiento=" + FechaNacimiento + ", "
                + "Sexo=" + Sexo + ", Telefono=" + Telefono + ", Celular=" + Celular + ", Curp=" + Curp + ", Rol=" + Rol + ", Direcciones=" + Direcciones + '}';
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

}
