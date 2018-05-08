package b135.rowbot_p2;

import android.content.Context;
import android.widget.Toast;

public abstract class Utility {

    // just a shortcut to make toasts
    public static void doToast(Context cont,String message){
        Toast.makeText(cont,message,Toast.LENGTH_SHORT).show();
    }
}
