package com.digis01.MMateoProgramacionNCapas.controller;

import com.digis01.MMateoProgramacionNCapas.DAO.ColoniaDAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.ColoniaJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.DireccionDAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.DireccionJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.EstadoDAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.EstadoJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.MunicipioDAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.MunicipioJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.PaisDAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.PaisJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.RolDAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.RolJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.MMateoProgramacionNCapas.DAO.UsuarioJPADAOImplementacion;
import com.digis01.MMateoProgramacionNCapas.ML.Colonia;
import com.digis01.MMateoProgramacionNCapas.ML.Direccion;
import com.digis01.MMateoProgramacionNCapas.ML.ErrorCarga;
import com.digis01.MMateoProgramacionNCapas.ML.Estado;
import com.digis01.MMateoProgramacionNCapas.ML.Result;
import com.digis01.MMateoProgramacionNCapas.ML.Rol;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.digis01.MMateoProgramacionNCapas.ML.Usuario;
import com.digis01.MMateoProgramacionNCapas.Service.ValidacionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("UsuarioIndex")
public class UsuarioIndex {

    @Autowired
    private ValidacionService validacionService;

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private RolDAOImplementacion rolDAOImplementacion;

    @Autowired
    private PaisDAOImplementacion paisDAOImplementacion;

    @Autowired
    private EstadoDAOImplementacion estadoDAOImplementacion;

    @Autowired
    private MunicipioDAOImplementacion municipioDAOImplementacion;

    @Autowired
    private ColoniaDAOImplementacion coloniaDAOImplementacion;

    @Autowired
    private DireccionDAOImplementacion direccionDAOImplementacion;

    @Autowired
    private UsuarioJPADAOImplementacion usuarioJPADAOImplementacion;

    @Autowired
    private DireccionJPADAOImplementacion direccionJPADAOImplementacion;

    @Autowired
    private PaisJPADAOImplementacion paisJPADAOImplementacion;
    
    @Autowired
    private EstadoJPADAOImplementacion estadoJPADAOImplementacion;
    
    @Autowired
    private MunicipioJPADAOImplementacion municipioJPADAOImplementacion;
    
    @Autowired
    private ColoniaJPADAOImplementacion coloniaJPADAOImplementacion;
    
    @Autowired
    private RolJPADAOImplementacion rolJPADAOImplementacion;

    private final List<Usuario> usuariosCargaMasiva = new ArrayList<>();

    @GetMapping
    public String UsuarioIndex(Model model) {
        //Result result = usuarioDAOImplementation.GetAll();
        Result roles = rolJPADAOImplementacion.GetAll();
        Result resultJPA = usuarioJPADAOImplementacion.GetAll();

        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles.objects);
        model.addAttribute("usuarios", resultJPA.objects);

        return "UsuarioIndex";
    }

    @GetMapping("usuario-detalles/{IdUsuario}")
    public String UsuarioDetalles(@PathVariable("IdUsuario") int idUsuario, Model model) {
        //Result result = usuarioDAOImplementation.GetUsuarioDireccionesById(idUsuario);
        Result result = usuarioJPADAOImplementacion.GetById(idUsuario);

        model.addAttribute("usuario", result.object);
        model.addAttribute("Direccion", new Direccion());
        model.addAttribute("roles", rolJPADAOImplementacion.GetAll().objects);
        model.addAttribute("paises", paisJPADAOImplementacion.GetAll().objects);

        return "UsuarioDetalles";
    }

    //Carga masiva
    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("CargaMasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, HttpSession session) {

        //Revisar tipo de archivo correcto
        String extension = archivo.getOriginalFilename().split("\\.")[1];

        String pathBase = System.getProperty("user.dir");
        String pathArchivo = "src/main/resources/archivosCarga";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String pathDefinitvo = pathBase + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        try {
            archivo.transferTo(new File(pathDefinitvo));

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        //Inicializar lista de usuarios para guardar
        List<Usuario> usuarios = new ArrayList<>();

        if (extension.equals("txt")) {
            usuarios = LecturaArchivoTXT(pathDefinitvo);
        } else if (extension.equals("xlsx")) {
            usuarios = LecturaArchivoXLSX(pathDefinitvo);
        } else {
            //Mensaje de error
        }

        //Validar lista de usuarios
        List<ErrorCarga> listaErrores = ValidarDatosArchivo(usuarios);

        if (listaErrores.isEmpty()) {
            model.addAttribute("correcto", true);
            session.setAttribute("archivoCargaMasiva", pathDefinitvo);
            //
            usuariosCargaMasiva.addAll(usuarios);
        } else {
            model.addAttribute("listaErrores", listaErrores);
        }

        return "CargaMasiva";

    }

    @GetMapping("CargaMasiva/procesar")
    public String CargaUsuarios(HttpSession session) {

        System.out.println("Mensaje");

        //Validar que usuarios carga masiva no sea null
        String path = session.getAttribute("archivoCargaMasiva").toString();
        session.removeAttribute("archivoCargaMasiva");

        File file = new File(path);
        String extension = path.split("\\.")[1];

        //Inicializar lista de usuarios para guardar
        List<Usuario> usuarios = new ArrayList<>();

        if (extension.equals("txt")) {
            usuarios = LecturaArchivoTXT(path);
        } else if (extension.equals("xlsx")) {
            usuarios = LecturaArchivoXLSX(path);
        } else {
            //Mensaje de error
        }

        Result result = usuarioDAOImplementation.SaveAll(usuariosCargaMasiva);

        return "CargaMasiva";
    }

    public List<Usuario> LecturaArchivoTXT(String path) {

        List<Usuario> usuarios = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(path); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {

            String linea = "";

            while ((linea = bufferedReader.readLine()) != null) {
                //Extraer los datos
                String[] campos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setUserName(campos[0]);
                usuario.setNombre(campos[1]);
                usuario.setApellidoPaterno(campos[2]);
                usuario.setApellidoMaterno(campos[3]);
                usuario.setEmail(campos[4]);
                usuario.setPassword(campos[5]);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String fechaIngresada = campos[6];
                Date fechaFormateda = formatter.parse(fechaIngresada);
                usuario.setFechaNacimiento(fechaFormateda);
                usuario.setSexo(campos[7]);
                usuario.setTelefono(campos[8]);
                usuario.setCelular(campos[9]);
                usuario.setCurp(campos[10]);
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(campos[11]));

                usuarios.add(usuario);

            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }

        System.out.println(usuarios.isEmpty());
        return usuarios;
    }

    public List<Usuario> LecturaArchivoXLSX(String pathDefinitvo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(pathDefinitvo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.setUserName(row.getCell(0).toString().trim());
                usuario.setNombre(row.getCell(1).toString().trim());
                usuario.setApellidoPaterno(row.getCell(2).toString().trim());
                usuario.setApellidoMaterno(row.getCell(3).toString().trim());
                usuario.setEmail(row.getCell(4).toString().trim());
                usuario.setPassword(row.getCell(5).toString().trim());
                usuario.setFechaNacimiento(row.getCell(6).getDateCellValue());
                usuario.setSexo(row.getCell(7).toString().trim());
                DataFormatter formatter = new DataFormatter();
                Cell cellPhone = row.getCell(8);
                Cell cellCelular = row.getCell(9);

                String phone = formatter.formatCellValue(cellPhone);
                String celular = formatter.formatCellValue(cellCelular);

                usuario.setTelefono(phone);
                usuario.setCelular(celular);
                usuario.setCurp(row.getCell(10).toString().trim());
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol((int) row.getCell(11).getNumericCellValue());
                usuarios.add(usuario);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            usuarios = null;
        }
        return usuarios;
    }

    public List<ErrorCarga> ValidarDatosArchivo(List<Usuario> usuarios) {

        List<ErrorCarga> errores = new ArrayList<>();

        int linea = 1;
        for (Usuario usuario : usuarios) {

            BindingResult bindingResult = validacionService.validateObject(usuario);
            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.campo = fieldError.getField();
                errorCarga.descripcion = fieldError.getDefaultMessage();
                errorCarga.linea = linea;
                errores.add(errorCarga);
            }
            linea++;
        }
        return errores;
    }

    @GetMapping("add")
    public String Add(Model model) {

        model.addAttribute("roles", rolJPADAOImplementacion.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementacion.GetAll().objects);
        Usuario usuario = new Usuario();

        usuario.setNombre("JCole");
        usuario.setApellidoPaterno("Mateo");
        usuario.setApellidoMaterno("Martinez");
        usuario.setUserName("Miguel");
        usuario.setCurp("012345678912345678");
        usuario.setSexo("F");
        usuario.setFechaNacimiento(new Date());
        usuario.setEmail("jcolexxxx@xx.xx");
        usuario.setTelefono("2261164021");
        usuario.setCelular("2261164021");
        usuario.setPassword("1Ahfasaskfas");
        usuario.Direcciones = new ArrayList<>();
        Direccion direccion = new Direccion();
        direccion.setCalle("Av DIaz Miron");
        direccion.setNumeroExterior("158");
        direccion.setNumeroInterior("185");
        direccion.Colonia = new Colonia();
        direccion.Colonia.setIdColonia(1000);
        usuario.Direcciones.add(direccion);
        model.addAttribute("usuario", usuario);

        return "UsuarioFormulario";
    }

    @GetMapping("Estado/{idPais}")
    @ResponseBody
    public Result EstadosByIdPais(
            @PathVariable(value = "idPais", required = true) int idPais) {

        //Result result = estadoDAOImplementacion.EstadosByIdPais(idPais);
        Result result = estadoJPADAOImplementacion.GetByIdPais(idPais);

        return result;
    }

    @GetMapping("Municipio/{idEstado}")
    @ResponseBody
    public Result MunicipiosByIdEstado(@PathVariable(value = "idEstado") int idEstado) {
        Result result = municipioJPADAOImplementacion.GetByIdEstado(idEstado);
        return result;
    }

    @GetMapping("Colonia/{idMunicipio}")
    @ResponseBody
    public Result ColoniasByIdMunicipio(@PathVariable(value = "idMunicipio") int idMunicipio) {
        Result result = coloniaJPADAOImplementacion.GetByIdMunicipio(idMunicipio);
        
        return result;
    }

    @GetMapping("Direccion/{codigoPostal}")
    @ResponseBody
    public Result DireccionByCodigoPostal(@PathVariable(value = "codigoPostal") String codigoPostal) {
        Result result = coloniaDAOImplementacion.GetByCodigoPostal(codigoPostal);
        return result;
    }

    @GetMapping("DireccionById/{idDireccion}")
    @ResponseBody
    public Result DireccionById(@PathVariable(value = "idDireccion") int idDireccion) {
        Result result = direccionDAOImplementacion.GetById(idDireccion);
        return result;
    }

    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, @RequestParam("imagenFile") MultipartFile imagenFile) {

        //BindingResult es para regresar el fomulario cond datos en caso de que algo salga mal
        if (bindingResult.hasErrors()) {

            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolJPADAOImplementacion.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementacion.GetAll().objects);
            if (usuario.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais() > 0) {
                model.addAttribute("estados", estadoDAOImplementacion.EstadosByIdPais(usuario.Direcciones.get(0).Colonia.Municipio.Estado.Pais.getIdPais()).objects);
                if (usuario.Direcciones.get(0).Colonia.Municipio.Estado.getIdEstado() > 0) {
                    model.addAttribute("municipios", municipioDAOImplementacion.GetByIdEstado(usuario.Direcciones.get(0).Colonia.Municipio.Estado.getIdEstado()).objects);
                }
            }

            return "UsuarioFormulario";
        }
        if (imagenFile != null) {

            try {

                //vuelvo a asegurarme que es jpg o png
                String extension = imagenFile.getOriginalFilename().split("\\.")[1];

                if (extension.equals("jpg") || extension.equals("png")) {

                    byte[] byteImagen = imagenFile.getBytes();

                    String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);

                    usuario.setImagen(imagenBase64);

                }

            } catch (IOException ex) {

                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
        //Result result = usuarioDAOImplementation.Add(usuario);
        Result result = usuarioJPADAOImplementacion.Add(usuario);
        redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + " se creo con exito.");
        return "redirect:/UsuarioIndex";
    }

    @PostMapping("update")
    public String Update(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {

//        Result result = usuarioDAOImplementation.Update(usuario);
        Result result = usuarioJPADAOImplementacion.Update(usuario);
        redirectAttributes.addFlashAttribute("msgUsuarioEditado", "El usuario " + usuario.getUserName() + " se edito con exito");
        return "redirect:/UsuarioIndex/usuario-detalles/" + usuario.getIdUsuario();

    }

    @PostMapping
    public String UsuarioIndex(@ModelAttribute("usuario") Usuario usuario, Model model) {

        Result result = usuarioJPADAOImplementacion.GetAllDinamico(usuario);
        model.addAttribute("usuarios", result.objects);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolJPADAOImplementacion.GetAll().objects);
        return "UsuarioIndex";

    }

    @PostMapping("addDireccion")
    public String AddDireccion(@ModelAttribute("Direccion") Direccion direccion, @ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {

        //Agregar lo de BindingResult
        //Result result = direccionDAOImplementacion.AddByIdUsario(direccion, usuario.getIdUsuario());
        Result result = direccionJPADAOImplementacion.AddByIdUsario(direccion, usuario.getIdUsuario());

        //RedirectCon mensaje
        redirectAttributes.addFlashAttribute("msgDireccionAgregada", "La direccion se agregó correctamente");
        return "redirect:/UsuarioIndex/usuario-detalles/" + usuario.getIdUsuario();
    }

    @PostMapping("updateDireccion/{idUsuario}")
    public String UpdateDireccion(@ModelAttribute("Direccion") Direccion direccion, @PathVariable("idUsuario") int idUsuario, RedirectAttributes redirectAttributes) {
        //Result result = direccionDAOImplementacion.Update(direccion);
        Result result = direccionJPADAOImplementacion.Update(direccion);

        redirectAttributes.addFlashAttribute("msgDireccionEditada", "La direccion se editó correctamente");
        return "redirect:/UsuarioIndex/usuario-detalles/" + idUsuario;
    }

    @PostMapping("updateImage/{idUsuario}")
    public String UpdateImage(@RequestParam("imagenFile") MultipartFile imagenFile, @PathVariable("idUsuario") int idUsuario, RedirectAttributes redirectAttributes) {

        String imagen = "";

        if (imagenFile != null) {
            try {
                //vuelvo a asegurarme que es jpg o png
                String extension = imagenFile.getOriginalFilename().split("\\.")[1];
                if (extension.equals("jpg") || extension.equals("png")) {
                    byte[] byteImagen = imagenFile.getBytes();
                    String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);
                    imagen = imagenBase64;
                }
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //Result result = usuarioDAOImplementation.UpdateImage(idUsuario, imagen);
        Result result = usuarioJPADAOImplementacion.UpdateImagen(idUsuario, imagen);

        redirectAttributes.addFlashAttribute("msgImagenEditada", "La imagen se editó correctamente");
        return "redirect:/UsuarioIndex/usuario-detalles/" + idUsuario;
    }

    @GetMapping("deleteDireccion/{idDireccion}/{idUsuario}")
    @ResponseBody
    public Result DeleteDireccion(@PathVariable("idDireccion") int idDireccion, @PathVariable("idUsuario") int idUsuario, RedirectAttributes redirectAttributes
    ) {

        //Result result = direccionDAOImplementacion.Delete(idDireccion);
        Result result = direccionJPADAOImplementacion.Delete(idDireccion);

        return result;

    }

    @GetMapping("delete/{idUsuario}")
    @ResponseBody
    public Result Delete(@PathVariable("idUsuario") int idUsuario) {

        Result result = usuarioJPADAOImplementacion.Delete(idUsuario);

        return result;
    }

//    @GetMapping("buscar")
//    public String BuscarDinamicamente(@RequestParam("nombre") String nombre,
//            @RequestParam("apellidoPaterno") String apellidoPaterno, @RequestParam("apellidoMaterno") String apellidoMaterno,
//            @RequestParam("nombreRol") String nombreRol, Model model) {
//
//        Result result = usuarioDAOImplementation.GetAllDinamico(nombre, apellidoPaterno, apellidoMaterno, nombreRol);
//
//        model.addAttribute("usuarios", result.objects);
//
//        return "UsuarioIndex";
//
//    }
}
