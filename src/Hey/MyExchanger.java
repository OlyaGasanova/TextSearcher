package Hey;

import java.util.concurrent.Exchanger;

/**
 * Created by user on 20.09.2017.
 */
public class MyExchanger {
    static public Exchanger<String> ex = new Exchanger();
    public MyExchanger(){
       ex = new Exchanger();


    }
}
