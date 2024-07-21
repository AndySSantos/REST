package com.application.rest.persistence;

import com.application.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerDAO {

    Optional<Maker> findById(Long id);
    List<Maker> findAll();
    void save(Maker maker);

    void deleteById(Long id);
}
