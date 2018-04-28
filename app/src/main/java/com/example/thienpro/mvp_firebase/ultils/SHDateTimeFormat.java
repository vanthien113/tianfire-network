package com.example.thienpro.mvp_firebase.ultils;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by TranHuuPhuc on 12/28/2017.
 */

public class SHDateTimeFormat {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa", Locale.ENGLISH);

    public static String getTimeCurrent() {
        sdf.setTimeZone(TimeZone.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        return sdf.format(currentTime);
    }

    public static long getTimeCurrentMilisecond() {
        return Calendar.getInstance().getTime().getTime();
    }

    @Nullable
    public static Date getDate(String time) {
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateStartFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return sdf.format(calendar.getTime());
    }

    public static String getDateEndFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return sdf.format(calendar.getTime());
    }

    public static String getHour(String time) throws ParseException {
        Date date = sdf.parse(time);
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        return hourFormat.format(date);
    }

    public static String getHourWithAPM(String time) throws ParseException {
        Date date = sdf.parse(time);
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return hourFormat.format(date);
    }

    public static String changeDateFormat(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        Date time;
        String timePost;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfChange = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            time = sdf.parse(date);
            timePost = sdfChange.format(time);

            return timePost;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date getDateCurrent() {
        return Calendar.getInstance().getTime();
    }

    public static String getPostCurrentTime() {
        SimpleDateFormat simpleDateFormat;

        Date today = new Date();
        today.getDate();
        simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        return simpleDateFormat.format(today);
    }
}
