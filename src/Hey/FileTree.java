package Hey;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import Hey.GuiTest;
import Hey.Settings;
import Hey.SearchTextInFile;
import java.util.concurrent.Exchanger;
import Hey.MyExchanger;
import Hey.FileNavigator;
import static Hey.MyControl.fireEndLoading;
import static Hey.MyControl.fireaddTab;

public class FileTree extends JPanel  {
    JTree tree;
    public String path;
    DefaultMutableTreeNode root;
    Exchanger<String> exchanger = new Exchanger();

    public FileTree(String myPath) throws IOException {
        System.out.println("строим в "+ myPath+ " "+Settings.directory);
        root = new DefaultMutableTreeNode("root", true);
        getList(root, new File(Settings.directory));
        setLayout(new BorderLayout());
        tree = new JTree(root);
        //tree =
        tree.setRootVisible(false);
        JScrollPane myJScrollPane = new JScrollPane((JTree)tree);
        myJScrollPane.setSize(300,700);
                add(myJScrollPane,"Center");

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                System.out.println(tree.getSelectionPath().getLastPathComponent());
                if (tree.getSelectionPath().getLastPathComponent().toString().endsWith("txt")) {
                    System.out.println("Приветики");
                        FileNavigator current = new FileNavigator();
                        current.currentpath=tree.getSelectionPath().getLastPathComponent().toString();
                        Settings.currentFile = current;
                        fireaddTab(tree.getSelectionPath().getLastPathComponent().toString());
                        Thread t = new Thread(Settings.currentFile,"new");
                        String message="From FileTreeListener";
                        //this.exchanger=Settings.exchanger.ex;
                        t.start();
                }
            }
        });

        tree.addMouseListener(new MouseListener() {
                                  @Override
                                  public void mouseClicked(MouseEvent e) {
                                    //  if (e.getClickCount() == 2) {System.out.println(tree.getSelectionPath().getLastPathComponent());}
                                  }

                                  @Override
                                  public void mousePressed(MouseEvent e) {

                                  }

                                  @Override
                                  public void mouseReleased(MouseEvent e) {

                                  }

                                  @Override
                                  public void mouseEntered(MouseEvent e) {

                                  }

                                  @Override
                                  public void mouseExited(MouseEvent e) {

                                  }
                              }


        );

        tree.addTreeExpansionListener(
                new TreeExpansionListener() {
                    public void treeExpanded(TreeExpansionEvent event) {
                        // эти слушатели извещаются когда операция была завершена. Здесь раскрытие узла.
                        //JOptionPane.showMessageDialog(tree, event.getPath().getLastPathComponent());
                       // System.out.println(event.getPath().getLastPathComponent().toString().endsWith("txt"));
                    }

                    public void treeCollapsed(TreeExpansionEvent event) {
                        // эти слушатели извещаются когда операция была завершена. Здесь свертывание узла.
                        JOptionPane.showMessageDialog(tree, event.getPath().getLastPathComponent());
                       // System.out.println(event.getPath().getLastPathComponent().toString());
                    }
                }
        );

    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 400);
    }

    public void getList(DefaultMutableTreeNode node, File f) throws IOException {
        if(!f.isDirectory()) {
            // We keep only JAVA source file for display in this HowTo
            if (f.getName().endsWith(Settings.extension)) {
                //System.out.println("FILE  -  " + f.getName());
                //System.out.println(f.getAbsolutePath());
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
                SearchTextInFile Searcher = new SearchTextInFile();
               // MyExchanger ex = new MyExchanger();
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
                    System.out.println("FileTree получил: " + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (message != null)
                     node.add(child);
            }
        }
        else {
            //System.out.println("DIRECTORY  -  " + f.getName());
            if (f.listFiles()!=null){
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
            node.add(child);
            File fList[] = f.listFiles();
            for (int i = 0; i < fList.length; i++)
                getList(child, fList[i]);
            Enumeration v = child.depthFirstEnumeration();
            boolean flag = false;
            while (v.hasMoreElements()) {
                String temp = v.nextElement().toString();
                //System.out.println(temp);
                if (temp.endsWith("txt")) {

                    //if (SearchTextInFile.searchUsingBufferedReader(temp, Settings.request) != null)
                    flag = true;
                }

            }
            if (flag == false) node.remove(child);
        }
        }
    }

    public static void hello() throws IOException {
        MyJFrame frame = new MyJFrame("Directory explorer");
    }





}

class WindowCloser extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        Window win = e.getWindow();
        win.setVisible(false);
        System.exit(0);
    }
}

class MyJFrame extends JFrame implements Runnable{
    JButton b1, b2, b3;
    FileTree panel;
    public String path;
    MyJFrame(String s) throws IOException {
        path=s;
    }

    public JPanel giveme(String s){

        //MyJFrame(s);
        return panel;
    }

    @Override
    public void run() {
        try {
            System.out.println("продолжение в ран");
            panel = new FileTree(path);
            System.out.println("конец загрузки");
            fireEndLoading();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}