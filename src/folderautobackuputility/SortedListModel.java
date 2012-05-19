/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package folderautobackuputility;

import java.util.Collections;
import java.util.LinkedList;
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
        LinkedList<Object> tempObjectList = new LinkedList<Object>();
        for(int x = 0; x < this.getSize(); x++){
            tempObjectList.add(this.get(x));
        }
        ObjectToStringComparator OTSC = new ObjectToStringComparator();
        Collections.sort(tempObjectList, OTSC);
        this.clear();
        for(Object tempObject: tempObjectList){
            this.addElement(tempObject);
        }

    }
}
