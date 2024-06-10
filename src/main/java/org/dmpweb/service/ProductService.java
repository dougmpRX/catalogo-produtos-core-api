package org.dmpweb.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.dmpweb.entity.Product;
import org.dmpweb.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Transactional
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public void persist(Product product) {
        productRepository.persist(product);
    }

    public Optional<Product> findByIdOptional(UUID id) {
        return productRepository.findByIdOptional(id);
    }

    public List<Product> findAll() {
        return productRepository.listAll();
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product) {
        return productRepository.getEntityManager().merge(product);
    }
}
