package com.example.thienpro.mvp_firebase.ultils;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hung.nguyendk on 11/7/17.
 */

public final class SHStringHelper {
    SHStringHelper() {
    }

    public static boolean nullOrEmpty(CharSequence input) {
        return input == null || input.toString().trim().length() == 0;
    }

    public static boolean notEqual(CharSequence one, CharSequence two) {
        return !one.toString().equals(two.toString());
    }

    public static boolean equal(CharSequence one, CharSequence two) {
        return one.toString().equals(two.toString());
    }

    public static void hashTag(String tag, int color, TextView textView) {
        boolean hashing = false;

        SpannableString hashText = new SpannableString(tag);
        Matcher matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(hashText);
        while (matcher.find()) {
            hashing = true;
            hashText.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), 0);
            textView.setText(hashText);
        }

        if(!hashing){
            textView.setText(tag);
        }

        hashing = false;
    }

    public static boolean isEmail(CharSequence input) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    public static boolean isPhoneNumber(CharSequence input) {
        return input.toString().trim().length() < 10;
    }
}
