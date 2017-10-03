package Hey;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.Exchanger;

import static Hey.MyControl.*;

public class FileTree extends JPanel  {
    JTree tree;
    public String path;
    DefaultMutableTreeNode root;
    Exchanger<String> exchanger = new Exchanger();

    public FileTree(String myPath) throws IOException {

        root = new DefaultMutableTreeNode("root", true);
        getList(root, new File(Settings.directory));
        if (root.getChildCount()==0) fireNothingFound();

        setLayout(new BorderLayout());
        tree = new JTree(root);
        tree.setRootVisible(false);

        JScrollPane myJScrollPane = new JScrollPane((JTree)tree);
        myJScrollPane.setSize(300,700);
                add(myJScrollPane,"Center");

        tree.addTreeSelectionListener(e -> {
                    if (tree.getSelectionPath().getLastPathComponent().toString().endsWith("txt")) {
                    Settings.temppath = tree.getSelectionPath().getLastPathComponent().toString();
                    fireaddTab(tree.getSelectionPath().getLastPathComponent().toString());
            }
        });

    }

    public Dimension getPreferredSize(){
        return new Dimension(300, 400);
    }

    public void getList(DefaultMutableTreeNode node, File f) throws IOException {
        if(!f.isDirectory()) {
            if (f.getName().endsWith(Settings.extension)) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);

                SearchTextInFile Searcher = new SearchTextInFile();

                Settings.exchanger = new MyExchanger();
                Searcher.currentpath=f.getAbsolutePath();
                Searcher.currentquery=Settings.request;
                Searcher.exchanger=Settings.exchanger.ex;

                Thread t = new Thread(Searcher,"new");
                String message="From FileTree";

                this.exchanger=Settings.exchanger.ex;
                t.start();
                try{
                    message=exchanger.exchange(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (message != null)
                     node.add(child);
            }
        }
        else {
            if (f.listFiles()!=null){
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
            node.add(child);

            File fList[] = f.listFiles();
            for (int i = 0; i < fList.length; i++)
                getList(child, fList[i]);

            Enumeration v = child.depthFirstEnumeration();
            boolean rightExtention = false;

            while (v.hasMoreElements()) {
                String temp = v.nextElement().toString();
                if (temp.endsWith(Settings.extension)) {
                    rightExtention = true;
                }

            }
            if (rightExtention == false) node.remove(child);
        }
        }
    }






}

class MyJFrame extends JFrame implements Runnable{
    FileTree panel;
    public String path;
    MyJFrame(String s) throws IOException {
        path=s;
    }

    public JPanel giveme(){
        return panel;
    }

    @Override
    public void run() {
        try {
            panel = new FileTree(path);
            fireEndLoading();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}