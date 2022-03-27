package demo.Controller.TableGenerator;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GenericGenerator<T> implements GeneratorInterface<T>{
    private Class<T> type;
    public GenericGenerator() {
        //type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }
    public JTable generateTable(ArrayList<T> objects){
        type = (Class<T>) objects.get(0).getClass();
        String[] columns = new String[type.getDeclaredFields().length];
        String[][] data = new String[objects.size()][];
        int index = 0;

        for(Field field:type.getDeclaredFields()){
            columns[index++] = field.getName();
        }
        int objectIndex = 0;
        for(T object:objects){
            String[] objectFields = new String[type.getDeclaredFields().length];

            index = 0;
            for(Field field:type.getDeclaredFields()){
                field.setAccessible(true);
                try {
                    objectFields[index] = String.valueOf(field.get(object));
                } catch (Exception e) {
                    e.printStackTrace();
                    objectFields[index] = "";
                }
                index++;
            }
            data[objectIndex++] = objectFields;
        }

        JTable table = new JTable(data, columns);
        table.setVisible(true);
        return table;
    }



}
