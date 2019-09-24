package com.pmk.app.dao;

import java.util.List;

public interface Repository<T> {
    public List<T> findAll();
    public T findOne(String id);
    public void upsert(T object);
    public void delete(String id);
}
