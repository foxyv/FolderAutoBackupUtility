/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package folderautobackuputility;

import java.util.Comparator;

/**
 * Compares two objects using their toString() methods.
 * @author Sweord
 */
public class ObjectToStringComparator implements Comparator<Object>{

    public int compare(Object o1, Object o2) {
        return o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
        
    }

    

}
