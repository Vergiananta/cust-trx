package com.enigma.testing.services;

import java.util.List;

public interface CommonService<T,ID> {

    public T save(T entity);

    public T removeById(ID id);

    public T findById(ID id);

    public List<T> findAll();
}
