package dev.joseluisgs.waladaw.listeners;

import dev.joseluisgs.waladaw.services.ProductService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarritoSessionListener implements HttpSessionListener {

    private final ProductService productService;

    @Autowired
    public CarritoSessionListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Si quieres inicializar algo al crear sesión
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Object carritoObj = se.getSession().getAttribute("carrito");
        if (carritoObj instanceof List<?>) {
            List<Long> productos = (List<Long>) carritoObj;
            for (Long id : productos) {
                productService.marcarComoReservado(id, false);
            }
        }
    }
}