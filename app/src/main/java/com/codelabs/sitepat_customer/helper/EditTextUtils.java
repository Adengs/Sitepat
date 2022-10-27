package com.codelabs.sitepat_customer.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextUtils {

    public static TextWatcher getTextWatcher(EditText et, EditText next_focus, boolean is_last) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if (!is_last)
                        et.clearFocus();
                    if (next_focus != null) {
                        next_focus.requestFocus();
                        next_focus.setCursorVisible(true);
                    }
                }

            }
        };
    }

    public static View.OnKeyListener onClickDelete(EditText pref_focus, EditText et, boolean is_first) {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (!is_first && et.getText().toString().length() == 0)
                        et.clearFocus();
                    if (pref_focus != null && et.getText().toString().length() == 0) {
                        pref_focus.requestFocus();
                        pref_focus.setCursorVisible(true);
                    }
                }
                return false;

            }
        };
    }
}
