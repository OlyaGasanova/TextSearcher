package Hey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import Hey.Settings;

import Hey.SearchTextInFile;


public class GuiTest extends JFrame implements MyControlListener{

    MyJFrame mytree = new MyJFrame(Settings.directory);
    private JButton chooserbutton = new JButton("Press");
    private JButton restartButton = new JButton("Restart");
    private JTextField inputpath = new JTextField("H:\\Depeche mode", 20);
    private JTextField inputtext = new JTextField("yandex", 20);
    private JEditorPane testtest = new JEditorPane("text/html", "");
    private JLabel labelpath = new JLabel("Input path");
    private JLabel labeltext = new JLabel("Input query");
    private JButton search = new JButton("Search!");
    private JButton test = new JButton("ChooseDir");
    private JPanel last = new JPanel();
    public JTabbedPane tabbedPane = new JTabbedPane();
    JButton nextmatch = new JButton("match>");
    JButton select = new JButton("Select");
    JButton prevmatch = new JButton("<match");
    JButton prevpage = new JButton("<<page");
    JButton nextpage = new JButton("page>>");

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root", true);
    public JTree Mytree = new JTree(root);
    private JPanel foldertree = new JPanel();
    public JPanel boxThird= new JPanel();// = mytree.giveme("H:/JavaProjTest");
    Box another = new Box(BoxLayout.X_AXIS);
    Container container = this.getContentPane();


    String[] items = {
            "log",
            "txt",
            "dox"
    };
    private JComboBox comboBox = new JComboBox(items);
    private String settings="log";





    public GuiTest() throws IOException {

        super("Find what you want"); //Заголовок окна
        setBounds(100, 100, 700, 600); //Если не выставить
        //размер и положение
        //то окно будет мелкое и незаметное
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //это нужно для того чтобы при
        //Container container = this.getContentPane();
        Box boxZeroLevel = new Box(BoxLayout.Y_AXIS);
        //container.addLi
       // JPanel b = new JPanel();
        //b.setLayout(new BoxLayout(b, BoxLayout.X_AXIS));
        Box boxFirst = new Box(BoxLayout.X_AXIS);
        inputpath.setMaximumSize(new Dimension(100,20));
        inputpath.setMinimumSize(new Dimension(50,20));
       // boxFirst.setPreferredSize(new Dimension(container.getWidth(),10));
       // boxFirst.add(labelpath);boxFirst.add(inputpath);
        //boxFirst.setSize(new Dimension(container.getWidth(),40));
        //boxFirst.setMinimumSize(new Dimension(container.getWidth(),40));
       // b.setMaximumSize(new Dimension(container.getWidth(),40));
       // b.setBackground(Color.blue);
       // boxFirst.add(Box.createVerticalStrut(40));
       // boxFirst.add(Box.createHorizontalStrut(15));
        boxFirst.add(Box.createHorizontalStrut(15));
        boxFirst.add(labelpath);
        boxFirst.add(Box.createHorizontalStrut(15));
        boxFirst.add(inputpath);
        boxFirst.add(Box.createHorizontalStrut(15));
        boxFirst.add(chooserbutton);
        chooserbutton.addActionListener(new ChooseDirectory());
        boxFirst.add(Box.createHorizontalStrut(15));
        boxFirst.add(comboBox);
        //test.addActionListener(new ChooseDirectory());
       // boxFirst.add(test);
        comboBox.addActionListener(new ChooserButton());
        boxFirst.add(Box.createHorizontalStrut(15));
        boxFirst.add(Box.createVerticalStrut(20));
        //chooserbutton.addMouseListener(pressbutton);

        //JComboBox comboBox = new JComboBox(items);
        //comboBox.setEditable(true);
       // comboBox.addMouseListener(pressbutton);
        //b.add(boxFirst);

        Box boxSecond = new Box(BoxLayout.X_AXIS);
        inputtext.setMaximumSize(new Dimension(100,20));
        boxSecond.add(Box.createHorizontalStrut(15));
        boxSecond.add(labeltext);
        boxSecond.add(Box.createHorizontalStrut(15));
        boxSecond.add(inputtext);
        boxSecond.add(Box.createHorizontalStrut(15));
        boxSecond.add(search);
        boxSecond.add(Box.createHorizontalStrut(15));
        boxSecond.add(restartButton);
        restartButton.addMouseListener(new restartButton());
        search.addMouseListener(new SearchButton());
        boxSecond.add(Box.createVerticalStrut(40));


        nextmatch.setEnabled(false);prevmatch.setEnabled(false);
        prevpage.setEnabled(false);nextpage.setEnabled(false);
        select.setEnabled(false);
        select.addMouseListener(new Select());
        nextmatch.addMouseListener(new Next());
        nextmatch.setMaximumSize(new Dimension(30,30));
        prevmatch.addMouseListener(new Previous());
        prevpage.addMouseListener(new PreviousPage());

        nextpage.addMouseListener(new NextPage());
        prevmatch.setMaximumSize(new Dimension(30,30));

        last.setLayout(new BorderLayout());
        Box buttonsbox = new Box(BoxLayout.X_AXIS);
        buttonsbox.add(Box.createHorizontalGlue());
        buttonsbox.add(prevpage);
        buttonsbox.add(prevmatch);
        buttonsbox.add(Box.createHorizontalStrut(15));
        buttonsbox.add(select);
        buttonsbox.add(Box.createHorizontalStrut(15));
        buttonsbox.add(nextmatch);buttonsbox.add(nextpage);
        buttonsbox.add(Box.createHorizontalGlue());
        buttonsbox.setBorder(new EmptyBorder(10,10,10,10));
        testtest.setMinimumSize(new Dimension(600,200));
        testtest.setMaximumSize(new Dimension(600,200));
        last.setSize(new Dimension(600,container.getHeight()));
        //last.setSize(new Dimension(600,container.getHeight()));
        last.add(buttonsbox, BorderLayout.NORTH);
        //last.add(new JScrollPane((JEditorPane)testtest),"Center");

        last.add(tabbedPane);
       // tabbedPane.addMouseListener(new MouseAdapter() {
       //     @Override
       //     public void mouseClicked(MouseEvent e) {
       //        // super.mouseClicked(e);
       //        System.out.println( tabbedPane.getSelectedIndex());
        //        Settings.currentTab=tabbedPane.getSelectedIndex();
        //        Settings.currentFile=Settings.Navigators.get(tabbedPane.getSelectedIndex());


//            }
  //      });

        tabbedPane.addChangeListener(new changedTab());

        //last.add(testtest, BorderLayout.CENTER);
        last.setBorder(new EmptyBorder(10,10,10,10));

        //tabbedPane.add(new JScrollPane((JEditorPane)testtest));
        //foldertree.setLayout(new Box(BoxLayout.X_AXIS));
        foldertree.setBackground(Color.white);
        foldertree.setBorder(new TitledBorder("Folder Tree"));
        foldertree.setSize(new Dimension(300,1000));
        foldertree.setMinimumSize(new Dimension(300,1000));
        foldertree.setMaximumSize(new Dimension(300,1000));
        //tree.setBorder(new TitledBorder("rr"));


        Mytree.setSize(new Dimension(150,400));
        Mytree.setMinimumSize(new Dimension(150,400));
        Mytree.setMaximumSize(new Dimension(150,400));
        Mytree.setBackground(Color.WHITE);


        boxThird.setBorder(new TitledBorder("Folder Path"));
        //boxThird.setLayout(new BorderLayout());
       // boxThird.add(Box.createHorizontalStrut(150));
        //Mytree.setMaximumSize(new Dimension(100,700));
        //boxThird.setBorder(new TitledBorder("rr"));
        //boxThird.setBackground(Color.white);
        //boxThird.add(Mytree, BorderLayout.PAGE_START);
        boxThird.setMaximumSize(new Dimension(300, 1000));
        testtest.setPreferredSize(new Dimension(400,10));
        boxThird.setPreferredSize(new Dimension(300,10));

        another.add(boxThird);
        another.add(Box.createHorizontalStrut(15));
        another.add(Box.createHorizontalGlue());
        another.add(last);

        boxZeroLevel.add(boxFirst);
        boxZeroLevel.add(boxSecond);
        boxZeroLevel.add(another);
        container.add(boxZeroLevel);
        //pack();
    }








    public static void main(String[] args) throws IOException {


        //String s[]={""};
        //mytree.hello();
       GuiTest app = new GuiTest();
        //app.addListener(new MyController());
        //Создаем экземпляр нашего приложения
       app.setVisible(true);
       // setContentPane(rootPanel);
        MyControl ctl = new MyControl();
       ctl.addListener(app);
        System.out.println("Hello World!");
    }




    @Override
    public void onDataChanged(String path) throws IOException {
        System.out.println("Hello Stupid World!");
        search.setEnabled(false);
        search.setText("Loading");
        Settings.mytree1 = new MyJFrame(path);
        //another.remove(boxThird);
        //boxThird = mytree1.giveme("1");
        Thread t =new Thread(Settings.mytree1, "tree");
        t.start();
        System.out.println("начало в ондатачейндж");
       /* try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //another.remove(boxThird);



    }



    @Override
    public void textChanged(String text) {

        testtest.setText("");
        //testtest.removeAll();
        //for (String line:text) {testtest.setText(line);
       // }

        //tabbedPane.getSelectedComponent()
        testtest.setText(text);
       // System.out.println("Hello Stupid World!"+text);
        Settings.Editors.get(Settings.currentTab).setText(text);
        last.revalidate();
        last.repaint();
       // last.setMinimumSize(new Dimension(400,600));
        //last.setMaximumSize(new Dimension(400,600));
       // last.setSize(400,600);
        last.revalidate();
        last.repaint();

    }

    @Override
    public void EndOfFile() {
        JOptionPane.showMessageDialog(this,"End of File" );
    }

    @Override
    public void EndLoading() {
        boxThird.removeAll();
        boxThird.setBorder(new TitledBorder("Folder Path"));
        boxThird.add(Settings.mytree1.giveme("H:/Depeche Mode"));
        //boxThird.setSize(300,400);
        another.add(boxThird, 0);
        boxThird.revalidate();
        boxThird.repaint();
        search.setEnabled(true);
        search.setText("Search!");
        System.out.println("Hello Stupid World!");
    }

    @Override
    public void ChangeDirectory() {
        inputpath.setText(Settings.directory);
    }

    @Override
    public void Reset() {

        inputpath.setText("");
        inputtext.setText("");

        last.remove(1);
        tabbedPane=new JTabbedPane();
        comboBox.setSelectedItem("log");
     //   tabbedPane.removeAll();
        last.add(tabbedPane);
        boxThird.removeAll();
        boxThird.revalidate();
        boxThird.repaint();
        another.revalidate();
        another.repaint();
        container.revalidate();
        container.repaint();
        System.out.println("Reset");
    }

    @Override
    public void Select() {
        System.out.println("Select");
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
        //Settings.extension=(String) comboBox.getSelectedItem();
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
            System.out.println(Settings.currentTab+" Добавлена вкладка. Это при каунт==0" );
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
            prevpage.setEnabled(true);nextpage.setEnabled(true);
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
            System.out.println("Добавляем навигатор № "+Settings.Navigators.size());
            Settings.currentTab=count;
            System.out.println(Settings.currentTab+" Добавлена вкладка. Это при каунт=="+count );
            tabbedPane.addTab(name,new JScrollPane((currentEditor)));
            Thread t = new Thread(Settings.currentFile,"new");
            t.start();
            tabbedPane.setSelectedIndex(count);
            System.out.println("Навигаторов у нас "+Settings.Navigators.size());

        }
        tabbedPane.revalidate();
        tabbedPane.repaint();
        last.revalidate();
        last.repaint();
    }

}





