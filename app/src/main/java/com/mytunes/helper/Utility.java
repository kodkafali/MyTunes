package com.mytunes.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    private static Utility mUtility;
    private Context mContext;

    private Utility(Context context){
        this.mContext = context;
    }

    public static Utility getInstance(Context context){
        if (mUtility == null)
            mUtility = new Utility(context);

        return mUtility;
    }

    public String getSimpleDate(String strDate){
        String result;
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH);

            Date date = inputFormatter.parse(strDate);
            result = outputFormatter.format(date);
        }
        catch (Exception ex){
            result = "";
        }

        return result;
    }

    public void hideKeyboard(View view){
        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showToast(View view, String text){
        Snackbar snackbar = Snackbar.make(view,
                text,
                Snackbar.LENGTH_LONG);

        snackbar.show();

    }

}
