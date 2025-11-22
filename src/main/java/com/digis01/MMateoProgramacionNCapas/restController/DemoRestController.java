package com.digis01.MMateoProgramacionNCapas.restController;

import com.digis01.MMateoProgramacionNCapas.JPA.Result;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {

    @GetMapping
    public String Bienvenida() {
        return "Pagina";
    }

    //Hola, saludo a alguien
    @GetMapping("saludo")
    public ResponseEntity Saludo(@RequestParam("nombre") String nombre) {
        Result result = new Result();

        try {
            if (!nombre.matches("^[A-Za-zñáéíóúÁÉÍÓÚ]+(\\s[A-Za-zñáéíóúÁÉÍÓÚ]+)*$")) {
                result.correct = false;
                result.errorMessage = "Solo se admiten letras";
                result.status = 400;
            } else {
                result.correct = true;
                result.object = "Hola " + nombre;
                result.status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    //División
    @GetMapping("division")
    public ResponseEntity Division(@RequestParam("numeroUno")  int numeroUno, @RequestParam(name = "numeroDos", required = false)  int numeroDos) {
        Result result = new Result();
        try {
            if (numeroDos == 0) {
                result.correct = false;
                result.errorMessage = "No se puede dividir entre 0";
                result.status = 400;
            } else {
                String n1 = String.valueOf(numeroUno);
                String n2 = String.valueOf(numeroDos);
                if (!n1.matches("\\d+") || !n2.matches("\\d+")) {
                    result.correct = false;
                    result.errorMessage = "Solo se admiten digitos";
                    result.status = 400;
                } else {
                    int division = numeroUno / numeroDos;
                    result.correct = true;
                    result.object = division;
                    result.status = 200;
                }
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("multiplicacion")
    public ResponseEntity Multiplicacion(@RequestParam("numeroUno") int numeroUno, @RequestParam("numeroDos") int numeroDos) {
        Result result = new Result();

        try {
            String n1 = String.valueOf(numeroUno);
            String n2 = String.valueOf(numeroDos);
            if (!n1.matches("\\d+") || !n2.matches("\\d+")) {
                result.correct = false;
                result.errorMessage = "Solo se admiten digitos";
                result.status = 400;
            } else {
                int mult = numeroUno * numeroDos;
                result.correct = true;
                result.object = mult;
                result.status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    //4. Suma de n elementos de un arreglo 
    @GetMapping("suma")
    public ResponseEntity Suma(@RequestBody int[] numeros) {

        Result result = new Result();

        try {
            if (numeros.length == 0) {
                result.correct = false;
                result.errorMessage = "El arreglo de numeros no puede estar vacio";
                result.status = 400;
            }else {
                int suma = 0;
                for (int numero : numeros) {
                    suma+= numero;
                }
                result.correct = true;
                result.object = suma;
                result.status = 200;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);

    }

}
