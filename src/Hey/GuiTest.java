package Hey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import Hey.SearchTextInFile;


public class GuiTest extends JFrame implements MyControlListener{

    MyJFrame mytree = new MyJFrame(Settings.directory);
    private JButton chooserbutton = new JButton("Press");
    private JButton restartButton = new JButton("Restart");
    private JTextField inputpath = new JTextField("input path", 20);
    private JTextField inputtext = new JTextField("input text", 20);
    private JTextArea testtest = new JTextArea("ghghghghg");
    private JLabel labelpath = new JLabel("Input Path:");
    private JLabel labeltext = new JLabel("Input Text:");
    private JButton search = new JButton("Search!");
    private JButton test = new JButton("ChooseDir");
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root", true);
    public JTree Mytree = new JTree(root);
    private JPanel foldertree = new JPanel();
    public JPanel boxThird = mytree.giveme("H:/JavaProjTest");
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
        setBounds(100, 100, 400, 800); //Если не выставить
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
        restartButton.addMouseListener(new SearchButton());
        search.addMouseListener(new SearchButton());
        boxSecond.add(Box.createVerticalStrut(40));

        JButton aza = new JButton(">");
        aza.setMaximumSize(new Dimension(30,30));
        JButton azaza = new JButton("<");
        azaza.setMaximumSize(new Dimension(30,30));
        JPanel last = new JPanel();
        last.setLayout(new BorderLayout());
        Box buttonsbox = new Box(BoxLayout.X_AXIS);
        buttonsbox.add(Box.createHorizontalGlue());
        buttonsbox.add(azaza);
        buttonsbox.add(Box.createHorizontalStrut(15));buttonsbox.add(aza);
        buttonsbox.add(Box.createHorizontalGlue());
        buttonsbox.setBorder(new EmptyBorder(10,10,10,10));
        testtest.setMinimumSize(new Dimension(200,200));
        last.add(buttonsbox, BorderLayout.NORTH);
        last.add(testtest, BorderLayout.CENTER);
        last.setBorder(new EmptyBorder(10,10,10,10));


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
        boxThird.setMaximumSize(new Dimension(200, 1000));



        another.add(boxThird);
        another.add(Box.createHorizontalStrut(15));
        another.add(last);

        boxZeroLevel.add(boxFirst);
        boxZeroLevel.add(boxSecond);
        boxZeroLevel.add(another);
        container.add(boxZeroLevel);
        pack();
    }








    public static void main(String[] args) throws IOException {


        //String s[]={""};
        //mytree.hello();
        GuiTest app = new GuiTest();
        //app.addListener(new MyController());
        //Создаем экземпляр нашего приложения
        app.setVisible(true);
        MyControl ctl = new MyControl();
        ctl.addListener(app);
        System.out.println("Hello World!");
    }




    @Override
    public void onDataChanged(String path) throws IOException {
        System.out.println("Hello Stupid World!");
        MyJFrame mytree1 = new MyJFrame(path);
       // another.remove(boxThird);
        //boxThird = mytree1.giveme("1");

        //another.remove(boxThird);
        boxThird.remove(boxThird.getComponent(0));
        boxThird.setBorder(new TitledBorder("Folder Path"));
        boxThird.add(mytree1.giveme("H:/Depeche Mode"));
        //another.add(boxThird, 0);
        boxThird.revalidate();
        boxThird.repaint();

        System.out.println("Hello Stupid World!");


    }


}




