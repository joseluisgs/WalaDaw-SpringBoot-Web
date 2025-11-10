package dev.joseluisgs.waladaw.services;

import dev.joseluisgs.waladaw.models.Product;
import dev.joseluisgs.waladaw.repositories.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaCleanupService {

    private final ProductRepository productRepository;

    public ReservaCleanupService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Se ejecuta cada 5 minutos (ajusta según tus necesidades)
    @Scheduled(fixedRateString = "#{${reservacleanup.interval.minutes} * 60 * 1000}")
    public void liberarReservasCaducadas() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Product> reservados = productRepository.findByReservadoTrue();
        for (Product producto : reservados) {
            if (producto.getReservaExpira() != null && producto.getReservaExpira().isBefore(ahora)) {
                producto.setReservado(false);
                producto.setReservaExpira(null);
                productRepository.save(producto);
            }
        }
    }
}