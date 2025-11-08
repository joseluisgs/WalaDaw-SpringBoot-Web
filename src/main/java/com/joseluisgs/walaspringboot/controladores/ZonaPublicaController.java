package com.joseluisgs.walaspringboot.controladores;

import com.joseluisgs.walaspringboot.modelos.Producto;
import com.joseluisgs.walaspringboot.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
// Fijamos ya de por si una ruta por defecto para escuchar en este controlador
@RequestMapping("/public")
public class ZonaPublicaController {

    // Vamos a usar el servicio de producto
    @Autowired
    ProductoServicio productoServicio;

    // De esta manera siempre tenemos los productos no vendidos
    @ModelAttribute("productos")
    public List<Producto> productosNoVendidos() {
        return productoServicio.productosSinVender();
    }

    // Escuchamos en las dos rutas por defecto
    // Tenemos una query, dels buscador y no es obligatoria
    @GetMapping({"/", "/index"})
    public String index(Model model, @RequestParam(name="q", required=false) String query) {
        if (query != null)
            // La buscamos y se lo mandamos a los index en el campo producto con su modelo
            model.addAttribute("productos", productoServicio.buscar(query));
        return "index";
    }

    // Devolvemos el producto con id establecida
    @GetMapping("/producto/{id}")
    public String showProduct(Model model, @PathVariable Long id) {
        //Buscamos pro id
        Producto result = productoServicio.findById(id);
        if (result != null) {
            // Si lo encotramos lo añadimos al modelo y se lo pasamos
            model.addAttribute("producto", result);
            return "producto";
        }
        // si no a public
        return "redirect:/public";
    }
}
