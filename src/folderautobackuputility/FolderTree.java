/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Sweord
 */
public class FolderTree {

    DefaultTreeModel FolderTreeModel;
    VernaFolder rootFolder = null;
    FileTreeNode rootNode;
    ThreadPoolExecutor overallThreadPool;
    BlockingQueue<Runnable> queue;
    boolean currentlyPopulating = false;
    private boolean populateSubfolders = true;
    boolean changed = false;
    int maxDepth = 1000;

    private void setNewFolder(VernaFolder newFolder) {
        if (!newFolder.equals(rootFolder)) {
            rootFolder = newFolder;
            initialize();
        }
    }

    private class FolderTraverseRunnable implements Runnable {

        FileTreeNode targetNode;
        VernaFolder targetFolder;
        int depth;

        public void run() {

            FileWrapper tempFile;
            FileTreeNode tempNode;

            for (URI rawr : targetFolder.getFileSet()) {
                tempFile = new FileWrapper(rawr);
                tempNode = new FileTreeNode(tempFile);

                targetNode.add(tempNode);

                if (tempFile.isDirectory() && populateSubfolders && depth <= maxDepth) {
                    FolderTraverseRunnable traverseSubfolder = new FolderTraverseRunnable();
                    traverseSubfolder.targetFolder = new VernaFolder(tempFile);
                    traverseSubfolder.targetNode = tempNode;
                    traverseSubfolder.depth = depth + 1;
                    Thread newThread = new Thread(traverseSubfolder);
                    overallThreadPool.execute(newThread);
                }
            }
            if (targetNode != null) {
                if (targetNode.getChildCount() == 0) {
                    targetNode.add(new DefaultMutableTreeNode("**EmptyFolder**"));
                }

            }

        }
    };

    /**
     * Uses a queue of threads to traverse a directory's subfolders and index
     * them in a tree.
     *
     */
    private void populateTreeWithFolder() {
        if (checkFolderValidity()) {
            currentlyPopulating = true;
            rootNode = new FileTreeNode((FileWrapper) rootFolder.getFolderFile());
            queue = new LinkedBlockingQueue<Runnable>();

            overallThreadPool = new ThreadPoolExecutor(10, 1000, 5, TimeUnit.MINUTES, queue);

            FolderTraverseRunnable traverseFolders = new FolderTraverseRunnable();
            traverseFolders.targetFolder = rootFolder;
            traverseFolders.targetNode = rootNode;
            traverseFolders.depth = 1;

            Thread newThread = new Thread(traverseFolders);
            overallThreadPool.execute(newThread);
            changed = true;
        }

    }

    public void generateDefaultTreeModel() {
        FolderTreeModel = new DefaultTreeModel(rootNode);


    }

    private boolean checkFolderValidity() {
        if (rootFolder == null) {
            LogSevereError("Null pointer to rootFolder.");
            return false;
        }
        if (rootFolder.getFolderURI() == null) {
            LogSevereError("No folder in VernaFolder.");
            return false;
        }
        return true;
    }

    private static void LogSevereError(String Message) {
        Logger.getLogger(FolderTree.class.getName()).log(Level.SEVERE, Message);
    }

    /**
     * Function called by all constructors, rebuilds the tree using the
     * information from rootFolder
     */
    private void initialize() {

        if (checkFolderValidity()) {
            rootNode = new FileTreeNode(new FileWrapper(rootFolder.getFolderFile()));
            populateTreeWithFolder();
            FolderTreeModel = new DefaultTreeModel(rootNode);
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    FolderTree(VernaFolder rootFolder) {
        this.rootFolder = rootFolder;
        initialize();
    }

    FolderTree(VernaFolder rootFolder, boolean populateSubfolders) {
        this.populateSubfolders = populateSubfolders;
        this.rootFolder = rootFolder;
        initialize();

    }
    //</editor-fold>
}
