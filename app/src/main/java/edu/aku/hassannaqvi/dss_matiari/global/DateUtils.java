package edu.aku.hassannaqvi.dss_matiari.global;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.edittextpicker.aliazaz.EditTextPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import edu.aku.hassannaqvi.dss_matiari.R;

public class DateUtils {

    // Get formatted Current Date Time
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.APP_DATE_TIME_FORMAT,
                Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Get Current Date Time in specified format
    public static String getCurrentDateTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // DateTime formatter with Date Object
    public static String getFormattedDateTime(Date dateTime, String outputFormat) {
        SimpleDateFormat cFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        return cFormat.format(dateTime);
    }

    // DateTime formatter with String
    public static String getFormattedDateTime(String dateTime, String outputFormat) {
        SimpleDateFormat cFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        return cFormat.format(dateTime);
    }

    // DateTime formatter with String
    public static String getFormattedDateTime(String dateTime, String inputFormat, String outputFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(inputFormat, Locale.getDefault());
            Date newDate = format.parse(dateTime);

            format = new SimpleDateFormat(outputFormat, Locale.getDefault());
            assert newDate != null;
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return AppConstants._EMPTY_;
    }

    // Check and Get server and device date
    public static List<String> checkServerAndDeviceDate(String date, String format) {
        List<String> list = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            Date strDate = sdf.parse(date);
            SimpleDateFormat sdf1 = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.getDefault());
            String serverDate = sdf1.format(Objects.requireNonNull(strDate));
            String systemDate = sdf1.format(new Date());
            if (!serverDate.equals(systemDate)) {
                // '0' index = Server Date
                // '1' index = System Date
                list.add(serverDate);
                list.add(systemDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    // For showing incorrect device date error popup
    public static void showDeviceDateErrorAlert(Activity activity, String serverDate, String systemDate) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_date_error, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        TextView txtDia = view.findViewById(R.id.dateTV);
        Button btnYes = view.findViewById(R.id.btnYes);
        txtDia.setText(String.format(activity.getString(R.string.incorrect_device_date_desc), serverDate, systemDate));

        // To prevent error on show dialog after activity is finishing or destroyed
        if (activity.isFinishing() || activity.isDestroyed()) return;

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);

        btnYes.setOnClickListener(view1 -> {
            alertDialog.dismiss();
            activity.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
        });
    }

    /**
     * START - For adding or subtracting days or months from the date
     */

    // For adding/subtract Days in specified DateTime in DateTimePicker
    public static String addSubDays(String specifiedDate, int addSubDays) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdfIn = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
            Date inputDate = sdfIn.parse(specifiedDate);
            cal.setTime(Objects.requireNonNull(inputDate));
            cal.add(Calendar.DAY_OF_MONTH, addSubDays);
            SimpleDateFormat sdfOut = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
            return sdfOut.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return AppConstants._EMPTY_;
        }
    }

    // For adding/subtract months in specified DateTime in DateTimePicker
    public static String addSubMonths(String specifiedDate, int addSubMonths) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdfIn = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
            Date inputDate = sdfIn.parse(specifiedDate);
            cal.setTime(Objects.requireNonNull(inputDate));
            cal.add(Calendar.MONTH, addSubMonths);
            SimpleDateFormat sdfOut = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
            return sdfOut.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return AppConstants._EMPTY_;
        }
    }

    // For adding/subtract both days and months in specified DateTime in DateTimePicker
    public static String addSubDaysMonths(String specifiedDate, int addSubDays, int addSubMonths) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdfIn = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
            Date inputDate = sdfIn.parse(specifiedDate);
            cal.setTime(Objects.requireNonNull(inputDate));
            cal.add(Calendar.DAY_OF_MONTH, addSubDays);
            if (addSubMonths > 0) cal.add(Calendar.MONTH, addSubMonths);
            SimpleDateFormat sdfOut = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
            return sdfOut.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return AppConstants._EMPTY_;
        }
    }

    /**
     * END - For adding or subtracting days or months from the date
     */

    // Calculate difference between two dates
    // Forward Date = Larger Date
    // backwardDate = Smaller Date
    public static String calculateDiffInDates(String forwardDate, String backwardDate, int diffType) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT,
                    Locale.getDefault());
            Date date1 = sdf.parse(forwardDate);
            Date date2 = sdf.parse(backwardDate);
            int diff;
            if (diffType == AppConstants.DATE_DIFF_IN_DAYS) {
                // Difference in days
                diff = Math.toIntExact((Objects.requireNonNull(date1).getTime() -
                        Objects.requireNonNull(date2).getTime()) / (1000 * 60 * 60 * 24));
            } else if (diffType == AppConstants.DATE_DIFF_IN_HOURS) {
                // Difference in hours
                diff = Math.toIntExact((Objects.requireNonNull(date1).getTime() -
                        Objects.requireNonNull(date2).getTime()) / (1000L * 60 * 60 * 24 * 60));
            } else {
                // Difference in minutes
                diff = Math.toIntExact((Objects.requireNonNull(date1).getTime() -
                        Objects.requireNonNull(date2).getTime()) / (1000L * 60 * 60 * 24 * 60 * 60));
            }
            return diff >= 0 ? Integer.toString(diff) : AppConstants._EMPTY_;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return AppConstants._EMPTY_;
    }

    // Set max value by current month
    public static void setMaxDayByCurrent(Object obj) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        if (obj instanceof EditTextPicker)
            ((EditTextPicker) obj).setMaxvalue(cal.get(Calendar.DAY_OF_MONTH));
    }

    // Set max value by current month
    public static void setMaxMonthByCurrent(Object obj) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        if (obj instanceof EditTextPicker)
            ((EditTextPicker) obj).setMaxvalue(cal.get(Calendar.MONTH) + 1);
    }

    // Set max value by current year
    public static void setMaxYearByCurrent(Object obj) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        if (obj instanceof EditTextPicker)
            ((EditTextPicker) obj).setMaxvalue(cal.get(Calendar.YEAR));
    }

    // Calculate AGE from DOB
    /* *
     * Age calculation from day, month and year.
     * Return list of string including age in years, months and days.
     * Example Calculated Age = 29 years 2 months and 20 days
     * index 0 = Years like 29 years
     * index 1 = Months like 2 months
     * index 2 = Days like 20 days
     * */
    public static List<String> calculateAgeFromDOB(String year, String month, String day) {
        List<String> ageList = new ArrayList<String>() {{
            add(AppConstants._EMPTY_);
            add(AppConstants._EMPTY_);
            add(AppConstants._EMPTY_);
        }};
        if (!AppConstants.isEmpty(year) && !AppConstants.isEmpty(month) && !AppConstants.isEmpty(day)) {
            Calendar cal = Calendar.getInstance();

            int _year = Integer.parseInt(year);
            int _month = Integer.parseInt(month);
            int _day = Integer.parseInt(day);

            if (_year < 1920 || (!month.equals("98") && _month > 12) || (!day.equals("98") && _day > 31))
                return ageList;

            // Default value
            _month = _month != 98 ? _month : 6;
            _day = _day != 98 ? _day : 15;

            try {
                SimpleDateFormat df = new SimpleDateFormat(AppConstants.APP_DATE_FORMAT, Locale.ENGLISH);
                cal.setTime(Objects.requireNonNull(df.parse(_year + "-" + _month + "-" + _day)));

                long millis = System.currentTimeMillis() - cal.getTimeInMillis();
                cal.setTimeInMillis(millis);

                long inDays = MILLISECONDS.toDays(millis);
                long inYears = (long) (inDays / 365.2425);
                long inMonths = (long) ((inDays - (inYears * 365.2425)) / 30.43);
                long tDay = (long) (inDays - ((inYears * 365.2425) + (inMonths * 30.43)));

                ageList.clear();
                ageList.add(inYears > 0 ? Long.toString(inYears) : "0");
                ageList.add(inMonths > 0 ? Long.toString(inMonths) : "0");
                ageList.add(inDays > 0 ? Long.toString(tDay) : "0");
                return ageList;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ageList;
    }

    // Calculate DOB from AGE
    /* *
     * Calculate date of birth from age
     * Return list of strings including DOB year and month.
     * Example Calculated DOB = year 1947 and month 8.
     * index 0 = Year like 1947
     * index 1 = Month like 8
     * */
    public static List<String> calculateDOBFromAge(String ageYear, String ageMonth) {
        List<String> birthList = new ArrayList<String>() {{
            add(AppConstants._EMPTY_);
            add(AppConstants._EMPTY_);
        }};
        if (!AppConstants.isEmpty(ageYear) && !AppConstants.isEmpty(ageMonth)) {
            Calendar cal = Calendar.getInstance();
            Date date = new Date();
            cal.setTime(date);

            int _year = Integer.parseInt(ageYear);
            int _month = Integer.parseInt(ageMonth);

            int birthYear = cal.get(Calendar.YEAR) - _year;
            int birthMonth = cal.get(Calendar.MONTH) - _month;
            if (birthMonth < 0) {
                birthYear--;
                birthMonth = 12 + birthMonth;
            }
            birthList.clear();
            birthList.add(Integer.toString(birthYear));
            birthList.add(Integer.toString(birthMonth));
        }

        return birthList;
    }

    // Calculate days, months and years (Cumulative) into days
    public static double getAgeInDays(String years, String months, String days) {
        int _years = 0, _months = 0, _days = 0;
        if (!AppConstants.isEmpty(years))
            _years = Integer.parseInt(years);

        if (!AppConstants.isEmpty(months))
            _months = Integer.parseInt(months);

        if (!AppConstants.isEmpty(days))
            _days = Integer.parseInt(days);

        double yearsInDays = _years * 365.2425;
        double monthsInDays = _months * 30.436806;

        return yearsInDays + monthsInDays + _days;
    }

    // Calculate days, months and years (Cumulative) into months
    public static int getAgeInMonths(String years, String months) {
        int _years = 0, _months = 0;
        if (!AppConstants.isEmpty(years))
            _years = Integer.parseInt(years);

        if (!AppConstants.isEmpty(months))
            _months = Integer.parseInt(months);

        return (_years * 12) + _months;
    }

    public static String changeDateFormat(String currentDate) {
        String newDate = currentDate;
        String[] oldDateParts = currentDate.split("-");
        newDate = oldDateParts[2].trim() + "/" + oldDateParts[1].trim() + "/" + oldDateParts[0].trim();
        return newDate;
    }

}
