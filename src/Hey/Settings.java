package Hey;

import java.nio.file.Paths;
import java.util.ArrayList;

import Hey.FileNavigator;
import Hey.MyExchanger;

import javax.swing.*;

/**
 * Created by user on 19.09.2017.
 */
public class Settings {
    static String extension = "txt";
    static MyExchanger exchanger;
    static MyJFrame mytree1;
    static FileNavigator currentFile;
    static String request ="yandex";
    static int currentTab;
    static String temppath;
    static ArrayList<FileNavigator> Navigators = new ArrayList<FileNavigator>();
    static ArrayList<JEditorPane> Editors = new ArrayList<JEditorPane>();
    static String directory = Paths.get("").toAbsolutePath().toString();

}
