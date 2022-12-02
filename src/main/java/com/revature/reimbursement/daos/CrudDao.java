package com.revature.reimbursement.daos;

import java.util.List;

public interface CrudDao <T> {
    void save(T obj);
    void delete(T obj);
    void update(T obj);
    T findById(String id);
    List<T> findAll();

}
