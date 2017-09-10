package vn.puresolutions.livestar.corev3.data.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import vn.puresolutions.livestar.corev3.data.DatabaseOpenHelper;

/**
 * Created by Phu Tran on 3/18/2016.
 */
public class DataDao <Model, Id> implements IDataDao<Model, Id> {
    protected Dao<Model, Id> dao;
    private Class<Model> modelClass;

    public DataDao(Context context, Class<Model> modelClass) {
        this.modelClass = modelClass;
        try {
            dao = OpenHelperManager
                    .getHelper(context, DatabaseOpenHelper.class)
                    .getDao(modelClass);
        } catch (SQLException e) {
            Log.e(getClass().getName(), "create DAO failed: " + e.getMessage());
        }
    }

    public Dao<Model, Id> getDao() {
        return dao;
    }

    @Override
    public boolean isExist(Id id) {
        try {
            return dao.idExists(id);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean insert(Model model) {
        try {
            dao.create(model);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getName(), "insert failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Model model) {
        try {
            dao.update(model);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getName(), "update failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Model getById(Id id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            Log.e(getClass().getName(), "get by id = " + id + " failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Model> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(getClass().getName(), "get all failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteById(Id id) {
        try {
            dao.deleteById(id);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getName(), "delete by id = " + id +" failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            TableUtils.clearTable(dao.getConnectionSource(), modelClass);
            return true;
        } catch (SQLException e) {
            Log.e(getClass().getName(), "delete all failed: " + e.getMessage());
            return false;
        }
    }
}

