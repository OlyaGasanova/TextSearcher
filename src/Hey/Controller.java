package Hey;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static Hey.MyControl.*;

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
        fireGetData();
        try {
            File f = new File(Settings.directory);
            if(!f.isDirectory()){
                fireWrong();
                return;
            }
            fireListeners(Settings.directory);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}


class Next extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
            Settings.currentFile.next=true;
            Thread t = new Thread(Settings.currentFile,"new");
            t.start();

    }
}

class Previous extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.currentFile.next=false;
        Thread t = new Thread(Settings.currentFile,"new");
        t.start();

    }
}

class NextPage extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.currentFile.nextPage=true;
        Thread t = new Thread(Settings.currentFile,"new");
        t.start();

    }
}
class PreviousPage extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.currentFile.previousPage=true;
        Thread t = new Thread(Settings.currentFile,"new");
        t.start();

    }
}

class Select extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {

        fireSelect();
    }
}

class restartButton extends MouseAdapter{
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.Editors = new ArrayList<JEditorPane>();
        Settings.Navigators = new ArrayList<FileNavigator>();
        Settings.currentTab=0;
        Settings.currentFile = null;
        Settings.directory=null;
        Settings.extension = "log";
        Settings.exchanger = new MyExchanger();
        Settings.mytree1=null;
        Settings.request =null;
        Settings.temppath=null;
        Settings.directory = Paths.get("").toAbsolutePath().toString();
        fireReset();


    }
}

class changedTab implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            JTabbedPane source =(JTabbedPane) e.getSource();
            Settings.currentTab=source.getSelectedIndex();
            Settings.currentFile=Settings.Navigators.get(source.getSelectedIndex());
        }
    }



class ChooserButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        JComboBox box = (JComboBox) e.getSource();
        String item = (String) box.getSelectedItem();
        Settings.extension=item;
    }
}



 class ChooseDirectory  extends JPanel implements ActionListener{



    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String newstring =  chooser.getSelectedFile().toString();
            Settings.directory=newstring;
           fireChangeDirectory();
        }
        else {
            System.out.println("No Selection ");
        }
    }


 }



