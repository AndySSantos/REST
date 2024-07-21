package com.application.rest.service;

import com.application.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerService {

    Optional<Maker> findById(Long id);
    List<Maker> findAll();
    void save(Maker maker);

    void deleteById(Long id);
}
