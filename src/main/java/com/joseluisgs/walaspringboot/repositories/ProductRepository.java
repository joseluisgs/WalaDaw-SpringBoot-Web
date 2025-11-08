package com.joseluisgs.walaspringboot.repositories;

import com.joseluisgs.walaspringboot.models.Purchase;
import com.joseluisgs.walaspringboot.models.Product;
import com.joseluisgs.walaspringboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPropietario(User propietario);
    List<Product> findByCompra(Purchase compra);
    List<Product> findByCompraIsNull();
    List<Product> findByNombreContainsIgnoreCaseAndCompraIsNull(String nombre);
    List<Product> findByNombreContainsIgnoreCaseAndPropietario(String nombre, User propietario);
    List<Product> findByCategoriaAndCompraIsNull(String categoria);
    List<Product> findByPrecioBetweenAndCompraIsNull(float minPrecio, float maxPrecio);
    List<Product> findByNombreContainsIgnoreCaseAndCategoriaAndCompraIsNull(String nombre, String categoria);
}
