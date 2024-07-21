package com.application.rest.controllers;

import com.application.rest.controllers.dto.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.impl.MakerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/maker")
public class MakerController {
    @Autowired
    private MakerServiceImpl service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try{
            var result = service.findById(id).orElseThrow();
            //Mapeo entity a DTO
            var resulDTO = MakerDTO.builder()
                                    .id(result.getId())
                                    .name(result.getName())
                                    .product(result.getProduct())
                                    .build();

            return ResponseEntity.ok(resulDTO);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        var result = service.findAll();
        if (result.isEmpty())
            return ResponseEntity.ok("No records found");
        List<MakerDTO> resultDTO = result.stream()
                                        .map(e->MakerDTO.builder().product(e.getProduct()).id(e.getId()).name(e.getName()).build())
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(resultDTO);

    }


    @PostMapping("/save")
    public ResponseEntity<?> saveMaker(@RequestBody MakerDTO makerDTO){
        if(makerDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        //Mapeo DTO a Entity

        service.save(Maker.builder().name(makerDTO.getName()).build());
        try {
            return ResponseEntity.created(new URI("/rest-api/v1/maker/save")).build();
        } catch (URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable("id") Long id, @RequestBody MakerDTO makerDTO){
        try{
            var maker = service.findById(id).orElseThrow();
            maker.setName(makerDTO.getName());
            //maker.setProduct(makerDTO.getProduct());

            service.save(maker);
            return ResponseEntity.ok("Registro actualizado");

        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        try{
            service.findById(id).orElseThrow();
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
