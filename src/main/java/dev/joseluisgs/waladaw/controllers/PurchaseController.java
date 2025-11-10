package dev.joseluisgs.waladaw.controllers;

import dev.joseluisgs.waladaw.models.Product;
import dev.joseluisgs.waladaw.models.Purchase;
import dev.joseluisgs.waladaw.models.User;
import dev.joseluisgs.waladaw.services.EmailService;
import dev.joseluisgs.waladaw.services.ProductService;
import dev.joseluisgs.waladaw.services.PurchaseService;
import dev.joseluisgs.waladaw.services.UserService;
import dev.joseluisgs.waladaw.utils.GeneradorPDF;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/app") // Ruta por defecto base donde vamos a escuchar
public class PurchaseController {
    // Necesitamos los siguientes servicios
    // Para manejar la compra
    final
    PurchaseService compraServicio;

    // Para manejar los productos
    final
    ProductService productoServicio;

    // Para los usuarios
    final
    UserService usuarioServicio;

    // Para las sesiones
    final
    HttpSession session;

    // Para Email
    final
    EmailService emailService;


    // Para mapear el usuario identificado con lo que tenemos almacenado
    private User usuario;

    @Autowired
    public PurchaseController(PurchaseService compraServicio, ProductService productoServicio, UserService usuarioServicio, HttpSession session, EmailService emailService) {
        this.compraServicio = compraServicio;
        this.productoServicio = productoServicio;
        this.usuarioServicio = usuarioServicio;
        this.session = session;
        this.emailService = emailService;
    }

    // Los metodos etiquetados como ModelAtribute, pornen en el modelo el resultado de realizar esta operación
    // Luego lo podremos recuperar en la vista
    @ModelAttribute("carrito")
    public List<Product> productosCarrito() {
        // Obtengo una lista de id alacenados en la sesión como carrito
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        // Devulevo la lista de productos que tienen la id almacenada en la sesion
        return (contenido == null) ? null : productoServicio.variosPorId(contenido);
    }

    // Calcula el total del carrito
    @ModelAttribute("total_carrito")
    public Double totalCarrito() {
        List<Product> productosCarrito = productosCarrito(); // Aqui tenemos un ejemplo de como modelar
        if (productosCarrito != null)
            return productosCarrito.stream()
                    .mapToDouble(p -> p.getPrecio())
                    .sum();
        // Podríamos hacerlo con un foreach y no con una expresión lamda. En el fondo estamos recorriendo el array
        // y sumando los valores
        return 0.0;
    }

    // Devuelve el total de items que tiene el carrito. Podemos hacerlo así o con la sesión
    // Lo mostraré en navbar de las dos maneras
    @ModelAttribute("items_carrito")
    public String itemsCarrito() {
        List<Product> productosCarrito = productosCarrito(); // Aqui tenemos un ejemplo de como modelar
        if (productosCarrito != null)
            return Integer.toString(productosCarrito.size());
        return "";
    }

    // Muestro las compras asociadas al email que se ha registrado en la sesión
    @ModelAttribute("miscompras")
    public List<Purchase> misCompras() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        usuario = usuarioServicio.buscarPorEmail(email);
        return compraServicio.findByPropietarioWithProducts(usuario);
    }

    // Como ya tenemos el modelo carrito y total_carrito definido, solo debemos ir a la platilla
    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        return "/app/compra/carrito";
    }

    // AÑadimos un producto al carrito
    @GetMapping("/carrito/add/{id}")
    public String addCarrito(Model model, @PathVariable Long id) {
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        if (contenido == null)
            contenido = new ArrayList<>();

        if (!contenido.contains(id)) {
            // Realiza la reserva atómica, SOLO desde el servicio
            boolean reservado = productoServicio.marcarComoReservado(id);
            if (reservado) {
                contenido.add(id);
            } else {
                session.setAttribute("compra_error", "El producto ya está reservado o vendido, no es posible añadirlo.");
                return "redirect:/app/carrito";
            }
        }

        session.setAttribute("carrito", contenido);
        session.setAttribute("items_carrito", contenido.size());
        return "redirect:/app/carrito";
    }

    // Elimina un elemento del carrito
    @GetMapping("/carrito/eliminar/{id}")
    public String borrarDeCarrito(Model model, @PathVariable Long id) {
        // Recuperamos el carrito
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        // Si es nulo, lo mandamos a public
        if (contenido == null)
            return "redirect:/public";
        // Borramos el contenido
        contenido.remove(id);
        // Desmarcamos como reservado
        productoServicio.marcarComoReservado(id, false);
        // Si está vacío, elimamos carrito de la sesión
        if (contenido.isEmpty()) {
            session.removeAttribute("carrito");
            session.removeAttribute("items_carrito");
        } else {
            // Si no lo añadimos
            session.setAttribute("carrito", contenido);
            session.setAttribute("items_carrito", contenido.size());
        }
        return "redirect:/app/carrito";

    }

    // Mostrar confirmación antes de finalizar compra
    @GetMapping("/carrito/comprar")
    public String confirmarCompra(Model model) {
        // Recuperamos el carrito
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        if (contenido == null || contenido.isEmpty())
            return "redirect:/public";

        // Los productos ya están en el modelo via @ModelAttribute
        return "/app/compra/confirmar";
    }

    // Finaliza una compra
    @GetMapping("/carrito/finalizar")
    public String checkout() {
        // Recuperamos el carrito
        List<Long> contenido = (List<Long>) session.getAttribute("carrito");
        if (contenido == null) // Es es nulo a public
            return "redirect:/public";

        // Obtenemos la lista de productos
        List<Product> productos = productosCarrito();

        // Validación de reservas: solo se puede comprar si siguen reservados y la reserva no ha caducado
        LocalDateTime now = LocalDateTime.now();
        for (Product producto : productos) {
            if (!producto.isReservado() || producto.getReservaExpira() == null || producto.getReservaExpira().isBefore(now)) {
                // Puedes notificar al usuario el problema, aquí solo redirigimos por ejemplo
                session.setAttribute("compra_error", "El producto '" + producto.getNombre() + "' ya no está disponible. Su reserva ha caducado o ha sido vendido.");
                return "redirect:/app/carrito"; // O a una página de error específica
            }
        }

        // Los insertamos en la compra
        Purchase c = compraServicio.insertar(new Purchase(), usuario);
        // Sitaxis de java nueva por cada producto p, ejecutamos compra servicio y asociamos p a la compra c
        productos.forEach(p -> compraServicio.addProductoCompra(p, c));
        // Elimanos de la sesión el carrito
        session.removeAttribute("carrito");
        session.removeAttribute("items_carrito");

        // Elimina la reserva de los productos comprados
        productos.forEach(p -> {
            productoServicio.marcarComoReservado(p.getId(), false);
        });

        // Enviar email de confirmación de manera asíncrona para no bloquear
        new Thread(() -> {
            try {
                emailService.enviarEmailConfirmacionCompra(c);
            } catch (Exception e) {
                // Log error but don't fail the purchase
                System.err.println("Error enviando email: " + e.getMessage());
            }
        }).start();

        // Abrimos la factura
        return "redirect:/app/miscompras/factura/" + c.getId();

    }

    // Mustra las compras e un listado
    @GetMapping("/miscompras")
    public String verMisCompras(Model model) {
        // Ya no necesitamos enviar productos por separado
        // Los productos están cargados en cada compra mediante JOIN FETCH
        return "/app/compra/listado";
    }

    //Obtiene la factura con un id
    @GetMapping("/miscompras/factura/{id}")
    public String factura(Model model, @PathVariable Long id) {
        // Recupero la compra mediante su ID con productos cargados
        Purchase c = compraServicio.findByIdWithProducts(id);
        // Los productos ya están cargados en c.productos
        model.addAttribute("productos", c.getProductos());
        model.addAttribute("compra", c);
        // El total se calcula automáticamente
        model.addAttribute("total_compra", c.getTotal());
        model.addAttribute("total", c.getTotal());
        // Devolvemos la factura
        return "/app/compra/factura";
    }

    // Saco una factura en PDF usando itex - Simple and working version
    @RequestMapping(value = "/miscompras/factura/{id}/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> facturaPDF(@PathVariable Long id) {
        // Recupero la compra mediante su ID con productos cargados
        Purchase compra = compraServicio.findByIdWithProducts(id);
        // Los productos ya están cargados
        List<Product> productos = compra.getProductos();
        // Total calculado automáticamente
        Double total = compra.getTotal();

        ByteArrayInputStream bis = GeneradorPDF.factura2PDF(compra, productos, total);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura_" + compra.getId() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
