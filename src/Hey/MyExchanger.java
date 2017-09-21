package Hey;

import java.util.concurrent.Exchanger;

/**
 * Created by user on 20.09.2017.
 */
public class MyExchanger {
    public Exchanger<String> ex;
    public MyExchanger(){
       ex = new Exchanger();


    }
}
