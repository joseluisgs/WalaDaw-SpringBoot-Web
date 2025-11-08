package com.joseluisgs.walaspringboot.controladores;

import com.joseluisgs.walaspringboot.modelos.Usuario;
import com.joseluisgs.walaspringboot.servicios.UsuarioServicio;
import com.joseluisgs.walaspringboot.upload.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class LoginController {

    // Servicio de usuario
    @Autowired
    UsuarioServicio usuarioServicio;

    // Servicio de almacenamiento
    @Autowired
    StorageService storageService;

    // Si entra por defecto redirigimos a otro controlador a la ruta public
    @GetMapping("/")
    public String welcome() {
        return "redirect:/public/";
    }

    // Ruta de login, como también es registro, por eso le pasamos los campos
    // Para que pueda casarlos con la parte del registro, command object del usuario a crear

    @GetMapping("/auth/login")
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // No se hace el Post de Login-post, porque está dentro del circuito de Spring Security

    // Registro del usuario que obtenemos con ModelAttribute
    @PostMapping("/auth/register")
    public String register(@ModelAttribute Usuario usuario , @RequestParam("file") MultipartFile file) {
        //Subida de imagenes más adelante
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            usuario.setAvatar(MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "serveFile", imagen).build().toUriString());

        }

        usuarioServicio.registrar(usuario);
        // Le llevamos al login
        return "redirect:/auth/login";
    }
}
