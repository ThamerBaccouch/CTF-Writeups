package com.andry;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    /* renamed from: c1 */
    public native int mo2c1(int i);

    public native int c10(int i);

    public native int c11(int i);

    public native int c12(int i);

    public native int c13(int i);

    public native int c14(int i);

    public native int c15(int i);

    public native int c16(int i);

    public native int c17(int i);

    public native int c18(int i);

    public native int c19(int i);

    /* renamed from: c2 */
    public native int mo13c2(int i);

    public native int c20(int i);

    public native int c21(int i);

    public native int c22(int i);

    public native int c23(int i);

    public native int c24(int i);

    public native int c25(int i);

    public native int c26(int i);

    public native int c27(int i);

    public native int c28(int i);

    public native int c29(int i);

    /* renamed from: c3 */
    public native int mo24c3(int i);

    public native int c30(int i);

    public native int c31(int i);

    public native int c32(int i);

    /* renamed from: c4 */
    public native int mo28c4(int i);

    /* renamed from: c5 */
    public native int mo29c5(int i);

    /* renamed from: c6 */
    public native int mo30c6(int i);

    /* renamed from: c7 */
    public native int mo31c7(int i);

    /* renamed from: c8 */
    public native int mo32c8(int i);

    /* renamed from: c9 */
    public native int mo33c9(int i);

    static {
        System.loadLibrary("andry-lib");
    }

    public static List<String> splitBySize(String text, int size) {
        List<String> ret = new ArrayList<>(((text.length() + size) - 1) / size);
        int start = 0;
        while (start < text.length()) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
            start += size;
        }
        return ret;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0046R.layout.activity_main);
        ((Button) findViewById(C0046R.C0048id.button2)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.check_password().booleanValue()) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Yes!", 0).show();
                    DynamicLoaderService.startActionLoad(MainActivity.this.getApplicationContext(), ((EditText) MainActivity.this.findViewById(C0046R.C0048id.editPassword1)).getText().toString());
                    return;
                }
                Toast.makeText(MainActivity.this.getApplicationContext(), "No...", 0).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public Boolean check_password() {
        String input = ((EditText) findViewById(C0046R.C0048id.editPassword1)).getText().toString();
        Integer count = Integer.valueOf(0);
        Integer valueOf = Integer.valueOf(0);
        ListIterator<String> it = splitBySize(input, 2).listIterator();
        while (it.hasNext()) {
            Integer index = Integer.valueOf(it.nextIndex());
            int d = Integer.parseInt((String) it.next(), 16);
            switch (index.intValue() + 1) {
                case 1:
                    if (mo2c1(d) != 6326) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 2:
                    if (mo13c2(d) != 2259) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 3:
                    if (mo24c3(d) != 455) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 4:
                    if (mo28c4(d) != 1848) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 5:
                    if (mo29c5(d) != 275400) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 6:
                    if (mo30c6(d) != 745) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 7:
                    if (mo31c7(d) != 1714) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 8:
                    if (mo32c8(d) != 1076) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 9:
                    if (mo33c9(d) != 12645) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 10:
                    if (c10(d) != 2120) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 11:
                    if (c11(d) != 153664) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 12:
                    if (c12(d) != 10371) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 13:
                    if (c13(d) != 37453) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 14:
                    if (c14(d) != 203640) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 15:
                    if (c15(d) != 691092) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 16:
                    if (c16(d) != 36288) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 17:
                    if (c17(d) != 753) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 18:
                    if (c18(d) != 2011) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 19:
                    if (c19(d) != 59949) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 20:
                    if (c20(d) != 18082) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 21:
                    if (c21(d) != 538) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 22:
                    if (c22(d) != 12420) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 23:
                    if (c23(d) != 2529) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 24:
                    if (c24(d) != 1130) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 25:
                    if (c25(d) != 6076) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 26:
                    if (c26(d) != 11702) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 27:
                    if (c27(d) != 47217) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 28:
                    if (c28(d) != 1056) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 29:
                    if (c29(d) != 207) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 30:
                    if (c30(d) != 11315) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 31:
                    if (c31(d) != 2676) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
                case 32:
                    if (c32(d) != 261) {
                        break;
                    } else {
                        count = Integer.valueOf(count.intValue() + 1);
                        break;
                    }
            }
        }
        if (count.intValue() == 32) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
