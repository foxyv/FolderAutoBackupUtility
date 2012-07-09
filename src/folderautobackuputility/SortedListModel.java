/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package folderautobackuputility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Sweord
 */
public class SortedListModel extends DefaultListModel {

    public SortedListModel() {
        super();
    }

    public void sortByString(){
        
        ArrayList<Object> tempObjectList = (ArrayList<Object>) Arrays.asList(this.toArray());
        
        ObjectToStringComparator OTSC = new ObjectToStringComparator();
        Collections.sort(tempObjectList, OTSC);
        this.clear();
        for(Object tempObject: tempObjectList){
            
            this.addElement(tempObject);
        }

    }
}
