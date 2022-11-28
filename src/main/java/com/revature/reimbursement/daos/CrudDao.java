package com.revature.reimbursement.daos;

import java.util.List;

public interface CrudDao <T> {
    void save();
    void delete();
    void update();
    T findById();
    List<T> findAll();

}
