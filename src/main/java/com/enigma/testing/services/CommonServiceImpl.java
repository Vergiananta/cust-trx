package com.enigma.testing.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class CommonServiceImpl<T,ID> implements CommonService<T, ID>{

    protected final JpaRepository<T, ID> repository;

    public CommonServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T removeById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            repository.deleteById(id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll(Sort.by("id"));
    }
}
