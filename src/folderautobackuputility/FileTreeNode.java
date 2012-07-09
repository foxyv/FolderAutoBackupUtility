/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Sweord
 */
public class FileTreeNode extends DefaultMutableTreeNode {

    FileWrapper aFile;

    public FileTreeNode() {
        super();
    }

    public FileTreeNode(FileWrapper userFile) {
        super(userFile);
        aFile = new FileWrapper(userFile.toURI());
    }

    public FileTreeNode(FileWrapper userFile, boolean allowsChildren) {
        super(userFile, allowsChildren);
        aFile = new FileWrapper(userFile.toURI());
    }

    public void sortChildren() {
        ArrayList<FileTreeNode> tempList = new ArrayList<FileTreeNode>();

        for (int x = 0; x < this.getChildCount(); x++) {
            tempList.add((FileTreeNode) this.getChildAt(x));
        }
        CompareNodes comparator = new CompareNodes();
        Collections.sort(tempList, comparator);

        this.removeAllChildren();

        for (FileTreeNode a : tempList) {
            this.add(a);
        }

    }

    private class CompareNodes implements Comparator<FileTreeNode> {

        public int compare(FileTreeNode o1, FileTreeNode o2) {
            if(o1.aFile.isDirectory() == o2.aFile.isDirectory()){
                return o1.aFile.getName().compareTo(o2.aFile.getName());
            }
            else{
                if(o1.aFile.isDirectory()) return -1;
                if(o2.aFile.isDirectory()) return 1;
            }
            return -1;
        }
    }
}
