package com.joseluisgs.walaspringboot.controllers;

import com.joseluisgs.walaspringboot.models.Product;
import com.joseluisgs.walaspringboot.services.ProductService;
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
    final
    ProductService productoServicio;

    @Autowired
    public ZonaPublicaController(ProductService productoServicio) {
        this.productoServicio = productoServicio;
    }

    // De esta manera siempre tenemos los productos no vendidos
    @ModelAttribute("productos")
    public List<Product> productosNoVendidos() {
        return productoServicio.productosSinVender();
    }

    // Escuchamos en las dos rutas por defecto
    // Tenemos una query, del buscador y no es obligatoria
    @GetMapping({"/", "/index", "/public", "/public/"})
    public String index(Model model,
                        @RequestParam(name = "q", required = false) String query,
                        @RequestParam(name = "categoria", required = false) String categoria,
                        @RequestParam(name = "minPrecio", required = false) Float minPrecio,
                        @RequestParam(name = "maxPrecio", required = false) Float maxPrecio) {

        List<Product> productos = productoServicio.productosSinVender();

        // Aplicar filtros
        if (query != null && !query.trim().isEmpty()) {
            productos = productoServicio.buscar(query);
        }

        if (categoria != null && !categoria.trim().isEmpty()) {
            productos = productos.stream()
                    .filter(p -> categoria.equals(p.getCategoria()))
                    .toList();
        }

        if (minPrecio != null && maxPrecio != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() >= minPrecio && p.getPrecio() <= maxPrecio)
                    .toList();
        } else if (minPrecio != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() >= minPrecio)
                    .toList();
        } else if (maxPrecio != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() <= maxPrecio)
                    .toList();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("q", query);
        model.addAttribute("categoria", categoria);
        model.addAttribute("minPrecio", minPrecio);
        model.addAttribute("maxPrecio", maxPrecio);

        return "index";
    }

    // Devolvemos el producto con id establecida
    @GetMapping("/producto/{id}")
    public String showProduct(Model model, @PathVariable Long id) {
        //Buscamos pro id
        Product result = productoServicio.findById(id);
        if (result != null) {
            // Si lo encotramos lo añadimos al modelo y se lo pasamos
            model.addAttribute("producto", result);
            return "producto";
        }
        // si no a public
        return "redirect:/public";
    }
}
