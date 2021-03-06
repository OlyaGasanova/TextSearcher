package Hey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MyJFileChooser extends JPanel
        implements ActionListener {
    JButton go;

    JFileChooser chooser;
    String choosertitle;

    public MyJFileChooser() {
        go = new JButton("Do it");
        go.addActionListener(this);
        add(go);
    }

    public void actionPerformed(ActionEvent e) {
        int result;

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
        }
        else {
            System.out.println("No Selection ");
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 400);
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("");
        MyJFileChooser panel = new MyJFileChooser();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}