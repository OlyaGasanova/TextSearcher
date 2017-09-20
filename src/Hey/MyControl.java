package Hey;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 19.09.2017.
 */


interface MyControlListener {
    void onDataChanged(String path) throws IOException;
}

public class MyControl /*...*/ {
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
/* ...... код может вызывать fireListeners(count) когда требуется уведомить слушателей */
}