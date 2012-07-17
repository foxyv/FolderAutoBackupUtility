/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility.Model;

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

    public DefaultTreeModel FolderTreeModel;
    public VernaFolder rootFolder = null;
    public FileTreeNode rootNode;
    public ThreadPoolExecutor overallThreadPool;
    private BlockingQueue<Runnable> threadPoolQueue;
    boolean currentlyPopulating = false;
    private boolean populateSubfolders = true;
    boolean changed = false;
    int maxDepth = 1000; //The maximum depth of subfolders to populate (maxDepth = 1 to only populate the root folder)

    /**
     * A folder traversing runnable.
     * This class is a runnable designed to traverse the current folder's subfolders and
     * subsequently populate the tree with the files and folders within. 
     * It will create a new thread for each subfolder and add it to a processing threadPoolQueue.
     * It will only traverse to a maximum depth as defined by the variable maxDepth.
     * 
     */
    private class FolderTraverseRunnable implements Runnable {

        FileTreeNode targetNode; //The node to populate
        VernaFolder targetFolder; //The folder to populate the node with
        int depth; //The depth of our current folder from the root node

        public void run() {

            FileWrapper tempFile;
            FileTreeNode tempNode;

            for (URI rawr : targetFolder.getFileSet()) {
                tempFile = new FileWrapper(rawr);
                tempNode = new FileTreeNode(tempFile);

                targetNode.add(tempNode);

                //If the target of the new node is a directory and we haven't reached the maxDepth, populate that node with the directory.
                if (tempFile.isDirectory() && populateSubfolders && depth < maxDepth) {
                    
                    
                    //Create a new folder traveral thread for the subfolder
                    FolderTraverseRunnable traverseSubfolder = new FolderTraverseRunnable();
                    traverseSubfolder.targetFolder = new VernaFolder(tempFile);
                    traverseSubfolder.targetNode = tempNode;
                    traverseSubfolder.depth = depth + 1;
                    Thread newThread = new Thread(traverseSubfolder);
                    
                    //Add it to the threadpool
                    overallThreadPool.execute(newThread);
                }
            }
            if (targetNode != null) {
                if (targetNode.getChildCount() == 0) {
                    targetNode.add(new DefaultMutableTreeNode("**EmptyFolder**"));
                }
            }
        } //End of run override
        
    }; //End of class FolderTraverseRunnable

    
    /**
     * Uses a threadPoolQueue of threads to traverse a directory's subfolders and index
     * them in a tree.
     *
     */
    private void populateTreeWithFolder() {
        if (checkFolderValidity()) {
            currentlyPopulating = true;
            rootNode = new FileTreeNode((FileWrapper) rootFolder.getFolderFile());
            threadPoolQueue = new LinkedBlockingQueue<Runnable>();

            overallThreadPool = new ThreadPoolExecutor(10, 1000, 5, TimeUnit.MINUTES, threadPoolQueue);

            FolderTraverseRunnable traverseFolders = new FolderTraverseRunnable();
            traverseFolders.targetFolder = rootFolder;
            traverseFolders.targetNode = rootNode;
            traverseFolders.depth = 1; //Start off at depth 1

            Thread newThread = new Thread(traverseFolders);
            overallThreadPool.execute(newThread);
            changed = true;
        }

    }

    
    /**
     * Checks to make sure the target Folder actually exists and isn't a null value.
     * @return Boolean indicating whether the folder is valid.
     */
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

    
    /**
     * Logs a severe error with the passed string as it's message.
     * @param Message Explanation of the error.
     */
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
    public FolderTree(VernaFolder rootFolder) {
        this.rootFolder = rootFolder;
        initialize();
    }

    public FolderTree(VernaFolder rootFolder, boolean populateSubfolders) {
        this.populateSubfolders = populateSubfolders;
        this.rootFolder = rootFolder;
        initialize();

    }
    //</editor-fold>
}
