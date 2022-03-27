package demo.Controller.TableGenerator;

import javax.swing.*;
import java.util.ArrayList;

public interface GeneratorInterface<T> {
    /**
     * method used in Pages to generate from a list -> table to be displayed
     * @param obj : list of objects
     * @return JTable : table with the values of the list
     */
    public JTable generateTable(ArrayList<T> obj);
}
