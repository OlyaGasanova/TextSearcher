package Hey;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Hey.GuiTest;
import Hey.Settings;
import Hey.FileNavigator;
import static Hey.MyControl.fireChangeDirectory;
import static Hey.MyControl.fireGetData;
import static Hey.MyControl.fireWrong;

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
        fireGetData();
        //JButton mynew = (JButton) e.getSource();
        try {
            System.out.println("Такие дела "+Settings.directory);
            File f = new File(Settings.directory);
            if(!f.isDirectory())
            {System.out.println("mistaaaaaaaake!");
            fireWrong();
            return;
            }
            fireListeners(Settings.directory);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //System.out.println(mynew.getText());
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

class Next extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
            Settings.currentFile.next=true;
            Thread t = new Thread(Settings.currentFile,"new");
            String message="From FileTreeListener";
            //this.exchanger=Settings.exchanger.ex;
            t.start();
            //Settings.currentFile.NextPenetration();

    }
}

class Previous extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.currentFile.next=false;
        Thread t = new Thread(Settings.currentFile,"new");
        String message="From FileTreeListener";
        //this.exchanger=Settings.exchanger.ex;
        t.start();
        //Settings.currentFile.NextPenetration();

    }
}

class NextPage extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.currentFile.nextpage=true;
        Thread t = new Thread(Settings.currentFile,"new");
        System.out.println("nextpage");
        //this.exchanger=Settings.exchanger.ex;
        t.start();
        //Settings.currentFile.NextPenetration();

    }
}
class PreviousPage extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.currentFile.previouspage=true;
        Thread t = new Thread(Settings.currentFile,"new");
        System.out.println("previous page");
        //this.exchanger=Settings.exchanger.ex;
        t.start();
        //Settings.currentFile.NextPenetration();

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
           fireChangeDirectory();
            // try {
           //     fireListeners(newstring);
           // } catch (IOException e1) {
            //    e1.printStackTrace();
           // }
        }
        else {
            System.out.println("No Selection ");
        }
    }


 }



