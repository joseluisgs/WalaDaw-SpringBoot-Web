package com.joseluisgs.walaspringboot.controladores;

import com.joseluisgs.walaspringboot.servicios.CompraServicio;
import com.joseluisgs.walaspringboot.servicios.ProductoServicio;
import com.joseluisgs.walaspringboot.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CompraServicio compraServicio;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Estadísticas generales
        long totalProductos = productoServicio.findAll().size();
        long totalUsuarios = usuarioServicio.findAll().size();
        long totalCompras = compraServicio.findAll().size();

        // Productos recientes (últimos 5)
        var productosRecientes = productoServicio.findAll().stream()
                .sorted((p1, p2) -> Long.compare(p2.getId(), p1.getId()))
                .limit(5)
                .toList();

        // Usuarios recientes (últimos 5)
        var usuariosRecientes = usuarioServicio.findAll().stream()
                .sorted((u1, u2) -> u2.getFechaAlta().compareTo(u1.getFechaAlta()))
                .limit(5)
                .toList();

        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalCompras", totalCompras);
        model.addAttribute("productosRecientes", productosRecientes);
        model.addAttribute("usuariosRecientes", usuariosRecientes);

        return "admin/dashboard";
    }

    @GetMapping("/usuarios")
    public String gestionUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "admin/usuarios";
    }

    @GetMapping("/productos")
    public String gestionProductos(Model model) {
        model.addAttribute("productos", productoServicio.findAll());
        return "admin/productos";
    }
}
