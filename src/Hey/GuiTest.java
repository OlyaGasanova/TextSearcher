package Hey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.IOException;


public class GuiTest extends JFrame implements MyControlListener{

    MyJFrame mytree = new MyJFrame(Settings.directory);
    private JButton chooserbutton = new JButton("Press");
    private JButton restartButton = new JButton("Restart");
    private JTextField inputpath = new JTextField("C:\\", 20);
    private JTextField inputtext = new JTextField("query", 20);
    private JLabel labelpath = new JLabel("Input path");
    private JLabel labeltext = new JLabel("Input query");
    private JButton search = new JButton("Search!");
    private JPanel textBox = new JPanel();
    public JTabbedPane tabbedPane = new JTabbedPane();
    JButton nextmatch = new JButton("match>");
    JButton select = new JButton("Select");
    JButton prevmatch = new JButton("<match");
    JButton prevPage = new JButton("<<Page");
    JButton nextPage = new JButton("Page>>");

    Box resultBox = new Box(BoxLayout.X_AXIS);
    private Box mainBox = new Box(BoxLayout.Y_AXIS);
    private Box pathBox = new Box(BoxLayout.X_AXIS);
    Container container = this.getContentPane();

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root", true);
    public JTree Mytree = new JTree(root);
    public JPanel treeBox= new JPanel();
    private Box buttonsbox = new Box(BoxLayout.X_AXIS);


    String[] items = {
            "log",
            "txt",
            "dox"
    };
    private JComboBox comboBox = new JComboBox(items);


    public GuiTest() throws IOException {

        super("Find what you want"); //Заголовок окна
        setBounds(100, 100, 900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputpath.setMaximumSize(new Dimension(100,20));
        inputpath.setMinimumSize(new Dimension(50,20));
        pathBox.add(Box.createHorizontalStrut(15));
        pathBox.add(labelpath);
        pathBox.add(Box.createHorizontalStrut(15));
        pathBox.add(inputpath);
        pathBox.add(Box.createHorizontalStrut(15));
        pathBox.add(chooserbutton);
        chooserbutton.addActionListener(new ChooseDirectory());
        pathBox.add(Box.createHorizontalStrut(15));
        pathBox.add(comboBox);

        comboBox.addActionListener(new ChooserButton());
        pathBox.add(Box.createHorizontalStrut(15));
        pathBox.add(Box.createVerticalStrut(20));

        Box queryBox = new Box(BoxLayout.X_AXIS);
        inputtext.setMaximumSize(new Dimension(100,20));
        queryBox.add(Box.createHorizontalStrut(15));
        queryBox.add(labeltext);
        queryBox.add(Box.createHorizontalStrut(15));
        queryBox.add(inputtext);
        queryBox.add(Box.createHorizontalStrut(15));
        queryBox.add(search);
        queryBox.add(Box.createHorizontalStrut(15));
        queryBox.add(restartButton);
        restartButton.addMouseListener(new restartButton());
        search.addMouseListener(new SearchButton());
        queryBox.add(Box.createVerticalStrut(40));


        nextmatch.setEnabled(false);prevmatch.setEnabled(false);
        prevPage.setEnabled(false);nextPage.setEnabled(false);
        select.setEnabled(false);
        select.addMouseListener(new Select());
        nextmatch.addMouseListener(new Next());
        nextmatch.setMaximumSize(new Dimension(30,30));
        prevmatch.addMouseListener(new Previous());
        prevPage.addMouseListener(new PreviousPage());

        nextPage.addMouseListener(new NextPage());
        prevmatch.setMaximumSize(new Dimension(30,30));

        textBox.setLayout(new BorderLayout());

        buttonsbox.add(Box.createHorizontalGlue());
        buttonsbox.add(prevPage);
        buttonsbox.add(prevmatch);
        buttonsbox.add(Box.createHorizontalStrut(15));
        buttonsbox.add(select);
        buttonsbox.add(Box.createHorizontalStrut(15));
        buttonsbox.add(nextmatch);buttonsbox.add(nextPage);
        buttonsbox.add(Box.createHorizontalGlue());
        buttonsbox.setBorder(new EmptyBorder(10,10,10,10));
        textBox.setSize(new Dimension(600,container.getHeight()));
        textBox.add(buttonsbox, BorderLayout.NORTH);
        textBox.add(tabbedPane);
        tabbedPane.addChangeListener(new changedTab());
        textBox.setBorder(new EmptyBorder(10,10,10,10));

        Mytree.setSize(new Dimension(150,400));
        Mytree.setMinimumSize(new Dimension(150,400));
        Mytree.setMaximumSize(new Dimension(150,400));
        Mytree.setBackground(Color.WHITE);


        treeBox.setBorder(new TitledBorder("Folder Path"));
        treeBox.setMaximumSize(new Dimension(300, 1000));
        treeBox.setMinimumSize(new Dimension(300, 1000));
        treeBox.setPreferredSize(new Dimension(300,10));

        resultBox.add(treeBox);
        resultBox.add(Box.createHorizontalStrut(15));
        resultBox.add(Box.createHorizontalGlue());
        resultBox.add(textBox);

        mainBox.add(pathBox);
        mainBox.add(queryBox);
        mainBox.add(resultBox);
        container.add(mainBox);
    }








    public static void main(String[] args) throws IOException {

       GuiTest app = new GuiTest();
       app.setVisible(true);
        MyControl ctl = new MyControl();
       ctl.addListener(app);
    }




    @Override
    public void onDataChanged(String path) throws IOException {
        search.setEnabled(false);
        search.setText("Loading");
        Settings.mytree1 = new MyJFrame(path);
        Thread t =new Thread(Settings.mytree1, "tree");
        t.start();

    }



    @Override
    public void textChanged(String text) {

        Settings.Editors.get(Settings.currentTab).setText(text);
        textBox.revalidate();
        textBox.repaint();
        textBox.revalidate();
        textBox.repaint();

    }

    @Override
    public void EndOfFile() {
        JOptionPane.showMessageDialog(this,"End of File" );
    }

    @Override
    public void EndLoading() {
        treeBox.removeAll();
        treeBox.setBorder(new TitledBorder("Folder Path"));
        treeBox.add(Settings.mytree1.giveme());
        resultBox.add(treeBox, 0);
        treeBox.revalidate();
        treeBox.repaint();
        search.setEnabled(true);
        search.setText("Search!");
    }

    @Override
    public void ChangeDirectory() {
        inputpath.setText(Settings.directory);
    }

    @Override
    public void Reset() {

        inputpath.setText("");
        inputtext.setText("");

        textBox.remove(1);
        tabbedPane=new JTabbedPane();
        comboBox.setSelectedItem("log");
        textBox.add(tabbedPane);
        treeBox.removeAll();
        treeBox.revalidate();
        treeBox.repaint();
        resultBox.revalidate();
        resultBox.repaint();
        container.revalidate();
        container.repaint();
    }

    @Override
    public void Select() {
        Settings.Editors.get(Settings.currentTab).requestFocusInWindow();
        Settings.Editors.get(Settings.currentTab).selectAll();
    }

    @Override
    public void ChangeQuery() {
        inputtext.setText(Settings.request);
    }

    @Override
    public void GetData() {
        Settings.directory=inputpath.getText();
        Settings.request=inputtext.getText();
    }

    @Override
    public void Wrong() {
        JOptionPane.showMessageDialog(this,"No such Directory!" );
    }

    @Override
    public void NothingFound() {
        JOptionPane.showMessageDialog(this,"Nothing found! :(((\nTry changing the query." );
    }

    @Override
    public void addTab(String name) {
        int count=0;
        boolean alreadyhas=false;
        if (tabbedPane.getTabCount()==0) {

            JEditorPane currentEditor = new JEditorPane("text/html","");
            currentEditor.setEditable(false);
            Settings.currentTab=0;
            FileNavigator current = new FileNavigator();
            current.currentpath=Settings.temppath;
            Settings.currentFile = current;
            Settings.Navigators.add(current);
            Settings.Editors.add(currentEditor);
            Settings.currentTab=count;
            tabbedPane.addTab(name,new JScrollPane((currentEditor)));
            Thread t = new Thread(Settings.currentFile,"new");
            t.start();
            count++;
            alreadyhas=true;

            nextmatch.setEnabled(true);prevmatch.setEnabled(true);
            prevPage.setEnabled(true);nextPage.setEnabled(true);
            select.setEnabled(true);

        }
        while (count<tabbedPane.getTabCount()) {
            if (tabbedPane.getTitleAt(count)==name) {
            alreadyhas=true;
            tabbedPane.setSelectedIndex(count);
            }
        count++;
        }
        if (!alreadyhas) {
            JEditorPane currentEditor = new JEditorPane("text/html","");
            currentEditor.setEditable(false);
            Settings.Editors.add(currentEditor);
            FileNavigator current = new FileNavigator();
            current.currentpath=Settings.temppath;
            Settings.currentFile = current;
            Settings.Navigators.add(current);
            Settings.currentTab=count;
            tabbedPane.addTab(name,new JScrollPane((currentEditor)));
            Thread t = new Thread(Settings.currentFile,"new");
            t.start();
            tabbedPane.setSelectedIndex(count);

        }
        tabbedPane.revalidate();
        tabbedPane.repaint();
        textBox.revalidate();
        textBox.repaint();
    }

}





