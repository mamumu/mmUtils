package com.mumu.utilslib;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class KeyBoardUtils {
    // 切换虚拟键盘状态
    public static void ChangeKeyboardStatus(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // 隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        HideKeyboard(v.getContext());
    }

    public static void HideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void HideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    // 显示虚拟键盘
    public static void ShowKeyboard(View v) {
        if (null == v) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    // 强制显示或者关闭系统键盘
    public static void KeyBoard(final EditText txtSearchKey, final String status) {
        if (null == txtSearchKey) {
            return;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) txtSearchKey.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (status.equals("open")) {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, 300);
    }

    // 通过定时器强制隐藏虚拟键盘
    public static void TimerHideKeyboard(final View v) {
        if (null == v) {
            return;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
            }
        }, 10);
    }

    // 输入法是否显示着
    public static boolean IsKeyBoardActive(View view) {
        if (null == view) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        return imm.isActive();

    }
}
