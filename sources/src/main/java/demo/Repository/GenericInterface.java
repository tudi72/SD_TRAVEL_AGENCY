package demo.Repository;

import java.util.ArrayList;

/**
 * Interface used for data abstraction
 * @param <T>
 */
public interface GenericInterface<T> {

    public boolean delete(T obj);

    public void insert(T obj);

    public boolean update(T obj);

    public void executeQuery(String query, String[] param);
    public ArrayList<T> executeQueryAndGetList(String query, String[] param);

    public T executeQueryAndGetValue(String query,String[] param);
}
