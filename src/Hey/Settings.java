package Hey;

import java.nio.file.Paths;
import Hey.FileNavigator;
import Hey.MyExchanger;
/**
 * Created by user on 19.09.2017.
 */
public class Settings {
    static String extension = "txt";
    static MyExchanger exchanger;
    static MyJFrame mytree1;
    static FileNavigator currentFile;
    static String request ="yandex";
    static String directory = Paths.get("").toAbsolutePath().toString();

}
