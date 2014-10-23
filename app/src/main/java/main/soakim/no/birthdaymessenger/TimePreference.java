package main.soakim.no.birthdaymessenger;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by Sondre on 23.10.2014.
 */

public class TimePreference extends DialogPreference {
    private int lastHour = 0;
    private int lastMinute = 0;
    private TimePicker picker = null;

    public static int getHour(String time) {
        String[] s = time.split(":");
        return(Integer.parseInt(s[0]));
    }

    public static int getMinute(String time) {
        String[] s = time.split(":");
        return(Integer.parseInt(s[1]));
    }

    public TimePreference(Context context, AttributeSet ats) {
        super(context, ats);
        setPositiveButtonText(getContext().getString(R.string.set));
        setNegativeButtonText(getContext().getString(R.string.cancel));
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        picker.setIs24HourView(true);
        return(picker);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        picker.setCurrentHour(lastHour);
        picker.setCurrentMinute(lastMinute);
    }

    @Override
    protected void onDialogClosed(boolean value) {
        super.onDialogClosed(value);

        if (value) {
            lastHour = picker.getCurrentHour();
            lastMinute = picker.getCurrentMinute();

            String time=String.valueOf(lastHour) + ":" + String.valueOf(lastMinute);

            if (callChangeListener(time)) persistString(time);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray array, int i) {
        return(array.getString(i));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time = null;

        if (restoreValue) {
            if (defaultValue == null) time=getPersistedString("00:00");
            else time = getPersistedString(defaultValue.toString());
        }
        else time = defaultValue.toString();

        lastHour = getHour(time);
        lastMinute = getMinute(time);
    }
}