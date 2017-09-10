package vn.puresolutions.livestar.core.data.dao;

import java.util.List;

/**
 * Created by Phu Tran on 3/18/2016.
 */
public interface IDataDao<Model, Id> {
    boolean isExist(Id id);
    boolean insert(Model model);
    boolean update(Model model);
    Model getById(Id id);
    List<Model> getAll();
    boolean deleteById(Id id);
    boolean deleteAll();
}
