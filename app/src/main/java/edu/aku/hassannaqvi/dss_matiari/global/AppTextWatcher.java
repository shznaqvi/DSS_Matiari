package edu.aku.hassannaqvi.dss_matiari.global;

import android.text.Editable;
import android.text.TextWatcher;

public class AppTextWatcher implements TextWatcher {

    private final int viewId;
    private final IAppTextWatcher iAppTextWatcher;

    public AppTextWatcher(int viewId, IAppTextWatcher iAppTextWatcher) {
        this.viewId = viewId;
        this.iAppTextWatcher = iAppTextWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        iAppTextWatcher.afterTextChanged(viewId, editable.toString());
    }

    // TextWatcher callback
    public interface IAppTextWatcher {
        void afterTextChanged(int viewId, String text);
    }
}
