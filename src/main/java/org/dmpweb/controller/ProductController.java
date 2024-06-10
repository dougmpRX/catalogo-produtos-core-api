package org.dmpweb.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.dmpweb.dto.request.ProductRequest;
import org.dmpweb.dto.response.ProductResponse;
import org.dmpweb.entity.Product;
import org.dmpweb.service.ProductService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Path("/v1/products")
@Produces("application/json")
@Consumes("application/json")
public class ProductController {

    @Inject
    ProductService productService;

    @POST
    public Response create(@RequestBody ProductRequest request) {
        Product product = request.toEntity();
        productService.persist(product);
        ProductResponse response = product.toResponse();
        return Response.created(URI.create("/v1/products/" + response.getId())).build();
    }

    @GET
    public Response findAll() {
        List<Product> products = productService.findAll();
        if (products.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(products.stream().map(Product::toResponse).toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        Product product = productService.findByIdOptional(id).orElse(null);
        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(product.toResponse()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        Product product = productService.findByIdOptional(id).orElse(null);
        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        productService.deleteById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") UUID id, Product product) {
        Optional<Product> existingProduct = productService.findByIdOptional(id);
        if (existingProduct.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingProduct.get().setName(product.getName());
        existingProduct.get().setDescription(product.getDescription());
        existingProduct.get().setCategory(product.getCategory());
        existingProduct.get().setPrice(product.getPrice());

        return Response.ok(productService.updateProduct(existingProduct.get()).toResponse()).build();
    }
}
