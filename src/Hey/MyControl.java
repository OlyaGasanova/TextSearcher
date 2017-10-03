package Hey;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 19.09.2017.
 */


interface MyControlListener {
    void onDataChanged(String path) throws IOException;
    void textChanged(String text);
    void EndOfFile();
    void EndLoading();
    void ChangeDirectory();
    void ChangeQuery();
    void GetData();
    void Wrong();
    void addTab(String name);
    void Reset();
    void Select();
    void NothingFound();
}

public class MyControl {
    private static ArrayList<MyControlListener> listeners = new ArrayList<MyControlListener>();

    public void addListener(MyControlListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MyControlListener listener) {
        listeners.remove(listener);
    }

    public static void fireListeners(String path) throws IOException {
        for(MyControlListener listener : listeners) {
            listener.onDataChanged(path);
        }
    }

    public static void fireFirstTextAdded(String text) throws IOException {
        for (MyControlListener listener : listeners) {
            listener.textChanged(text);
        }
    }

    public static void fireEndofFile(){
        for(MyControlListener listener : listeners) {
            listener.EndOfFile();
        }
    }

    public static void fireEndLoading(){
        for(MyControlListener listener : listeners) {
            listener.EndLoading();
        }
    }

    public static void fireChangeDirectory(){
        for(MyControlListener listener : listeners) {
            listener.ChangeDirectory();
        }
    }
    public static void fireWrong(){
        for(MyControlListener listener : listeners) {
            listener.Wrong();
        }
    }
    public static void fireGetData(){
        for(MyControlListener listener : listeners) {
            listener.GetData();
        }
    }

    public  static void fireaddTab(String name){
        for(MyControlListener listener : listeners) {
            listener.addTab(name);
        }
    }

    public  static void fireReset(){
        for(MyControlListener listener : listeners) {
            listener.Reset();
        }
    }
    public  static void fireSelect(){
        for(MyControlListener listener : listeners) {
            listener.Select();
        }
    }

    public  static void fireNothingFound(){
        for(MyControlListener listener : listeners) {
            listener.NothingFound();
        }
    }

}