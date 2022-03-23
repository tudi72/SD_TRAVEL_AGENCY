package Repository;

import javax.persistence.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic class used for database management of insert/update/delete
 * @author Zaharia Tudorita
 * @since March 13, 2022
 */
public class GenericRepository<T> implements GenericInterface<T>{
//    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private final Class<T> type;

    public GenericRepository() {
        try{
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * delete object from database
     * @param obj
     * @return
     */
    public boolean delete(T obj){
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean ok = true;
        try{

            Field field = type.getDeclaredFields()[0];
            field.setAccessible(true);
            String value = String.valueOf(field.get(obj));
            String query = "DELETE FROM " + type.getSimpleName() + " WHERE id =:objID";
            Query qq = manager.createNativeQuery(query);
            qq.setParameter("objID",value);
            et =  manager.getTransaction();
            et.begin();
            qq.executeUpdate();
            et.commit();
        }
        catch (Exception ex){
            if(et != null)
                et.rollback();
            ex.printStackTrace();
            ok = false;
        }
        finally {
            manager.close();
            return ok;
        }
    }

    /**
     * insert object of type T into database
     * @param obj
     */
    public void insert(T obj) {
      this.persist(obj);
    }

    /**
     * execute native query written in class annotations
     * @param query to be executed
     * @param param the parameter for the query
     * @return List of objects resulted from query
     */
    public ArrayList<T> executeQueryAndGetList(String query, String[] param){
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();
        Query q = manager.createNamedQuery(query);
        if(param != null){

            for(int i = 0;i < param.length;i++){
                q.setParameter(i+1,param[i]);
            }
        }
        ArrayList<T> list = (ArrayList<T>) q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return list;
    }
    public void executeQuery(String query, String[] param){
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();
        Query q = manager.createNamedQuery(query);
        if(param != null){

            for(int i = 0;i < param.length;i++){
                q.setParameter(i+1,param[i]);
            }
        }
        q.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    /**
     * similar with executeQuery however it returns only one value
     * @param query
     * @param param
     * @return one single value
     */
    public T executeQueryAndGetValue(String query, String[] param){
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        manager.getTransaction().begin();
        Query q = manager.createNamedQuery(query);
        if(param != null){

            for(int i = 0;i < param.length;i++){
                q.setParameter(i+1,param[i]);
            }
        }
        T obj = null;
        try{
            obj = (T) q.getSingleResult();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        manager.getTransaction().commit();
        manager.close();
        return obj;
    }

    /**
     * update the object with the values modified during java execution
     * @param obj the object to be updated
     * @return
     */
    public boolean update(T obj){
        String queryText = "UPDATE " + type.getSimpleName() + " SET ";
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        boolean ok = true;

        Field[] fields = type.getDeclaredFields();
        Class<List> newtype = List.class;

        try {
            String fieldName = null;
            String value = null;
            for(int i = 1;i < fields.length-1;i++){
                fields[i].setAccessible(true);
                fieldName = fields[i].getName();
                Object object = null;
                if(fieldName.matches("[a-z]*_id")){
                    object = fields[i].get(obj);
                    Field adjField = object.getClass().getDeclaredFields()[0];
                    adjField.setAccessible(true);
                    value = adjField.get(object).toString();
                    queryText = queryText  + fieldName + " = '" + value +"' ,";
                }
                else if(!fields[i].getType().equals(newtype))  queryText = queryText  + fields[i].getName() + " = '" + fields[i].get(obj) +"' ,";

            }
            fields[0].setAccessible(true);
            fields[fields.length-1].setAccessible(true);
            fields[fields.length-1].setAccessible(true);
            fieldName = fields[fields.length-1].getName();

            if(fieldName.matches("_id")){
                Object object = fields[fields.length-1].get(obj);
                Field adjField = object.getClass().getDeclaredFields()[0];
                adjField.setAccessible(true);
                value = adjField.get(object).toString();
                queryText += fieldName +" = '" + value + "' " + " WHERE id ='" + fields[0].get(obj) +"' ";
            }
            else if(!fields[fields.length-1].getType().equals(newtype)) queryText += fieldName + " = '" + fields[fields.length-1].get(obj) + "' " + " WHERE id ='" + fields[0].get(obj) +"' ";
            else {
                queryText = queryText.substring(0,queryText.length()-2);
               queryText += " WHERE id ='" + fields[0].get(obj) +"' ";
            }

            Query query = manager.createQuery(queryText);
            et = manager.getTransaction();
            et.begin();
            query.executeUpdate();
            et.commit();
            ok = true;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            if(et != null)
                et.rollback();
            ok = false;
        }
        finally {
            manager.close();
            return ok;
        }

    }

    /**
     *  extracted shared field for persisting object to database
     *  for a given object
     * @param obj of type T
     */
    public void persist(T obj){
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = manager.getTransaction();
            et.begin();
            manager.persist(obj);
            et.commit();

        }
        catch (Exception ex){
            if(et != null)
                et.rollback();
            ex.printStackTrace();
        }
        finally {
            manager.close();
        }
    }
}
