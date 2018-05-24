package b135.rowbot_p2;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public abstract class Utility {

    public final String TAG = "Utility";

    // just a shortcut to make toasts
    public static void doToast(Context cont,String message){
        Toast.makeText(cont,message,Toast.LENGTH_SHORT).show();
    }


    // write a string to file
    public static void writeToFile(String data, String target, Context context){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(target, Context.MODE_PRIVATE ));
            osw.write(data);
            osw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    // returns the string previously saved
    public static String readFromFile(String target, Context context){
        String resultText = "";

        try{
            InputStream is = context.openFileInput(target);

            if(is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = br.readLine()) != null){
                    stringBuilder.append(receiveString);
                }

                is.close();
                resultText = stringBuilder.toString();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultText;
    }

    // returns the leftmost part of a string, divided by ":"
    // used to extract minutes from sessionTimer
    public static String divideString(String weekday) {
        String returnString;

        String[] result = weekday.split(":");
        returnString = result[0];
        return returnString;
    }

}
