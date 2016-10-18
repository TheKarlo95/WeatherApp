package hr.karlovrbic.weatherapp.utils;

import android.content.Context;
import android.widget.Toast;


public class MessageUtils {

    private MessageUtils() {
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showMessage(Context context, int resId) {
        showMessage(context, context.getString(resId));
    }

}
