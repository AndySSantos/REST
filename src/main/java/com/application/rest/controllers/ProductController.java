package com.application.rest.controllers;

import com.application.rest.controllers.dto.ProductDTO;
import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        var result = service.findAll();
        if(result.isEmpty())
            return ResponseEntity.ok("Not records product");

        List<ProductDTO> resultDTO = result.stream()
                                            .map(e->ProductDTO.builder().id(e.getId()).name(e.getName()).price(e.getPrice()).maker(e.getMaker()).build())
                                            .toList();

        return ResponseEntity.ok(resultDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        if(productDTO.getName().isBlank() || productDTO.getPrice()==null||productDTO.getMaker()==null) {
            return ResponseEntity.badRequest().build();
        }
        //Mapeo DTO a Entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setMaker(productDTO.getMaker());
        service.save(product);
        try {
            return ResponseEntity.created(new URI("/rest-api/v1/product/save")).build();
        } catch (URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        try {
            var result = service.findById(id).orElseThrow();
            //Mapeo entity to DTO
            ProductDTO resultDTO = ProductDTO.builder().id(result.getId()).price(result.getPrice()).name(result.getName()).maker(result.getMaker()).build();
            return ResponseEntity.ok(resultDTO);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id) {
        try {
            service.findById(id).orElseThrow();
            service.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id")Long id , @RequestBody ProductDTO dto ) {
        try {
            var result = service.findById(id).orElseThrow();
            //Mapeo DTO to entity
            result.setPrice(dto.getPrice());
            //result.setMaker(dto.getMaker());
            result.setName(dto.getName());
            service.save(result);
            return ResponseEntity.ok("Producto actualizado");
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/price-range")
    public ResponseEntity<?> getProductPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {

        if(min==null || max==null) {
            return ResponseEntity.badRequest().build();
        }

        var products = service.findByPriceInRange(min,max);
        if (products.isEmpty())
            return ResponseEntity.ok("No products found in the range");

        //Mapeo entity to dto
        List<ProductDTO> result = products.stream()
                .map(e->ProductDTO.builder().id(e.getId()).name(e.getName()).price(e.getPrice()).maker(e.getMaker()).build())
                .toList();

        return ResponseEntity.ok(result);
    }


}
