package com.application.rest.persistence.impl;

import com.application.rest.entities.Maker;
import com.application.rest.persistence.IMakerDAO;
import com.application.rest.repository.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MakerDAOImpl implements IMakerDAO {
    @Autowired
    private MakerRepository repository;


    @Override
    public Optional<Maker> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Maker> findAll() {
        return (List<Maker>) repository.findAll();
    }

    @Override
    public void save(Maker maker) {
        repository.save(maker);
    }

    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow();
        repository.deleteById(id);
    }
}
