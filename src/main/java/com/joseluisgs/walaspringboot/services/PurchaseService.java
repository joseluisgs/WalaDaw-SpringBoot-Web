package com.joseluisgs.walaspringboot.services;

import com.joseluisgs.walaspringboot.models.Purchase;
import com.joseluisgs.walaspringboot.models.Product;
import com.joseluisgs.walaspringboot.models.User;
import com.joseluisgs.walaspringboot.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository repositorio;

    @Autowired
    ProductService productoServicio;

    @CacheEvict(value = "compras", allEntries = true)
    public Purchase insertar(Purchase c, User u) {
        c.setPropietario(u);
        return repositorio.save(c);
    }

    @CacheEvict(value = "compras", allEntries = true)
    public Purchase insertar(Purchase c) {
        return repositorio.save(c);
    }

    public Product addProductoCompra(Product p, Purchase c) {
        p.setCompra(c);
        return productoServicio.editar(p);
    }

    @Cacheable(value = "compras", key = "#id")
    public Purchase buscarPorId(long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Cacheable(value = "compras")
    public List<Purchase> todas() {
        return repositorio.findAll();
    }

    @Cacheable(value = "compras", key = "'usuario_' + #u.id")
    public List<Purchase> porPropietario(User u) {
        return repositorio.findByPropietario(u);
    }

    @Cacheable(value = "compras")
    public List<Purchase> findAll() {
        return repositorio.findAll();
    }
}
