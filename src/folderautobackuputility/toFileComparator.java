/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.net.URI;
import java.util.Comparator;

/**
 *
 * @author Sweord
 */
public class toFileComparator implements Comparator<URI> {
  FileWrapper o1;
        FileWrapper o2;
    
    public int compare(URI URI1, URI URI2) {
            o1 = new FileWrapper(URI1);
            o2 = new FileWrapper(URI2);

            if (o1.isDirectory() == o2.isDirectory()) {
                return o1.getName().compareTo(o2.getName());
            } else {
                if (o1.isDirectory()) {
                    return -1;
                }
                if (o2.isDirectory()) {
                    return 1;
                }
            }
            return -1;
        }
    
    
}
