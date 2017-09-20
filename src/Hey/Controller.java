package Hey;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import Hey.GuiTest;
import Hey.Settings;

import static Hey.MyControl.fireListeners;

/**
 * Created by user on 19.09.2017.
 */
public class Controller{
    Controller(){

    }

}

class SearchButton extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        // super.mouseClicked(e);
        System.out.println("привет из другого класса");
        JButton mynew = (JButton) e.getSource();
        System.out.println(mynew.getText());
    }
}
class PressButton extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            //super.mouseClicked(e);
            JComboBox mychek = (JComboBox)e.getSource();
            String item = (String)mychek.getSelectedItem();
            Settings.extension=item;
        }
    }

class ChooserButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        JComboBox box = (JComboBox) e.getSource();
        String item = (String) box.getSelectedItem();
        System.out.println(item);
        Settings.extension=item;
        System.out.println("Heloooooo");
    }
}



 class ChooseDirectory  extends JPanel implements ActionListener{



    public void actionPerformed(ActionEvent e) {
        int result;
        JFileChooser chooser;
        String choosertitle;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           // System.out.println("getCurrentDirectory(): "
             //       +  chooser.getCurrentDirectory());
            //System.out.println("getSelectedFile() : "
             //       +  chooser.getSelectedFile());
            String newstring =  chooser.getSelectedFile().toString();
           // newstring.replaceAll("\\\\","/");
            Settings.directory=newstring;
            try {
                fireListeners(newstring);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else {
            System.out.println("No Selection ");
        }
    }


 }



