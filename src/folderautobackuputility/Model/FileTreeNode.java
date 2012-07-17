/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

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
        super(userFile.toString());
        aFile = new FileWrapper(userFile.toURI());
    }

    public FileTreeNode(FileWrapper userFile, boolean allowsChildren) {
        super(userFile.toString());
        this.setAllowsChildren(allowsChildren);
        aFile = new FileWrapper(userFile.toURI());
    }

    /**
     * Sorts the list using the passed comparator.
     * @param aComperator A comparator capable of comparing two DefaultMutableTreeNodes.
     */
    public void sortChildren(Comparator aComperator) {
        ArrayList<DefaultMutableTreeNode> tempList = new ArrayList<DefaultMutableTreeNode>();
        
        for (int x = 0; x < this.getChildCount(); x++) {
            
                tempList.add((DefaultMutableTreeNode)this.getChildAt(x));
        }
        Collections.sort(tempList, aComperator);
        this.removeAllChildren();

        for (DefaultMutableTreeNode a : tempList) {
            this.add(a);
        }

    }

    private static class CompareNodes implements Comparator<FileTreeNode> {

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
