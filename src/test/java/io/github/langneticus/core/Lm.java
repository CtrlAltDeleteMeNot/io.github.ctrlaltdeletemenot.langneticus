package io.github.langneticus.core;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Lm {

    private Lm(final int... data) {
        values = new String[LANGUAGE_COUNT];
        int startOffset = LANGUAGE_COUNT;
        for (int i = 0; i < LANGUAGE_COUNT; i++) {
            int outputSize;
            if (i == LANGUAGE_COUNT - 1) {
                outputSize = data.length - data[i];
            } else {
                outputSize = data[i + 1] - data[i];
            }
            final byte[] output = new byte[outputSize];
            for (int j = 0; j < output.length; j++) {
                output[j] = BITMAP.get((byte) data[j + startOffset]);
            }
            startOffset += output.length;
            values[i] = new String(output, StandardCharsets.UTF_8);
        }
    }

    // get(0) - Gets strings for language en-US
    // get(1) - Gets strings for language ro-RO
    public String get(int value) {
        return values[value];
    }

    private final String[] values;
    private static final Map<Byte, Byte> BITMAP;
    final static int LANGUAGE_COUNT;


    static {
        BITMAP = new HashMap<>();
        LANGUAGE_COUNT = 2;
        BITMAP.put((byte) 0, (byte) 90);
        BITMAP.put((byte) -1, (byte) -108);
        BITMAP.put((byte) 1, (byte) 1);
        BITMAP.put((byte) -2, (byte) -97);
        BITMAP.put((byte) 2, (byte) 48);
        BITMAP.put((byte) -3, (byte) 103);
        BITMAP.put((byte) -4, (byte) 65);
        BITMAP.put((byte) 3, (byte) 76);
        BITMAP.put((byte) 4, (byte) 87);
        BITMAP.put((byte) -5, (byte) 88);
        BITMAP.put((byte) 5, (byte) -19);
        BITMAP.put((byte) -6, (byte) -118);
        BITMAP.put((byte) 6, (byte) 64);
        BITMAP.put((byte) -7, (byte) 96);
        BITMAP.put((byte) -8, (byte) -74);
        BITMAP.put((byte) 7, (byte) -89);
        BITMAP.put((byte) 8, (byte) 32);
        BITMAP.put((byte) -9, (byte) -101);
        BITMAP.put((byte) -10, (byte) 3);
        BITMAP.put((byte) 9, (byte) -121);
        BITMAP.put((byte) -11, (byte) 30);
        BITMAP.put((byte) 10, (byte) 54);
        BITMAP.put((byte) 11, (byte) -28);
        BITMAP.put((byte) -12, (byte) -99);
        BITMAP.put((byte) 12, (byte) -16);
        BITMAP.put((byte) -13, (byte) -80);
        BITMAP.put((byte) -14, (byte) 22);
        BITMAP.put((byte) 13, (byte) -104);
        BITMAP.put((byte) -15, (byte) 83);
        BITMAP.put((byte) 14, (byte) -123);
        BITMAP.put((byte) 15, (byte) -12);
        BITMAP.put((byte) -16, (byte) -65);
        BITMAP.put((byte) 16, (byte) 69);
        BITMAP.put((byte) -17, (byte) -94);
        BITMAP.put((byte) 17, (byte) -22);
        BITMAP.put((byte) -18, (byte) 118);
        BITMAP.put((byte) -19, (byte) 39);
        BITMAP.put((byte) 18, (byte) -106);
        BITMAP.put((byte) 19, (byte) 25);
        BITMAP.put((byte) -20, (byte) 104);
        BITMAP.put((byte) 20, (byte) -8);
        BITMAP.put((byte) -21, (byte) -85);
        BITMAP.put((byte) -22, (byte) -37);
        BITMAP.put((byte) 21, (byte) 95);
        BITMAP.put((byte) 22, (byte) -20);
        BITMAP.put((byte) -23, (byte) -59);
        BITMAP.put((byte) -24, (byte) 43);
        BITMAP.put((byte) 23, (byte) -44);
        BITMAP.put((byte) -25, (byte) 0);
        BITMAP.put((byte) 24, (byte) 121);
        BITMAP.put((byte) -26, (byte) -62);
        BITMAP.put((byte) 25, (byte) -98);
        BITMAP.put((byte) -27, (byte) 36);
        BITMAP.put((byte) 26, (byte) -52);
        BITMAP.put((byte) 27, (byte) -96);
        BITMAP.put((byte) -28, (byte) 108);
        BITMAP.put((byte) 28, (byte) -111);
        BITMAP.put((byte) -29, (byte) -120);
        BITMAP.put((byte) -30, (byte) -4);
        BITMAP.put((byte) 29, (byte) -103);
        BITMAP.put((byte) -31, (byte) 41);
        BITMAP.put((byte) 30, (byte) -42);
        BITMAP.put((byte) 31, (byte) -35);
        BITMAP.put((byte) -32, (byte) 75);
        BITMAP.put((byte) 32, (byte) 40);
        BITMAP.put((byte) -33, (byte) -64);
        BITMAP.put((byte) 33, (byte) -5);
        BITMAP.put((byte) -34, (byte) 127);
        BITMAP.put((byte) 34, (byte) 61);
        BITMAP.put((byte) -35, (byte) 67);
        BITMAP.put((byte) 35, (byte) -17);
        BITMAP.put((byte) -36, (byte) 19);
        BITMAP.put((byte) 36, (byte) -86);
        BITMAP.put((byte) -37, (byte) 115);
        BITMAP.put((byte) 37, (byte) -23);
        BITMAP.put((byte) -38, (byte) -34);
        BITMAP.put((byte) -39, (byte) 20);
        BITMAP.put((byte) 38, (byte) -93);
        BITMAP.put((byte) -40, (byte) 5);
        BITMAP.put((byte) 39, (byte) 92);
        BITMAP.put((byte) 40, (byte) 81);
        BITMAP.put((byte) -41, (byte) -128);
        BITMAP.put((byte) 41, (byte) -41);
        BITMAP.put((byte) -42, (byte) 107);
        BITMAP.put((byte) 42, (byte) 33);
        BITMAP.put((byte) -43, (byte) 109);
        BITMAP.put((byte) -44, (byte) 9);
        BITMAP.put((byte) 43, (byte) -77);
        BITMAP.put((byte) 44, (byte) 4);
        BITMAP.put((byte) -45, (byte) 59);
        BITMAP.put((byte) 45, (byte) -69);
        BITMAP.put((byte) -46, (byte) -78);
        BITMAP.put((byte) 46, (byte) -47);
        BITMAP.put((byte) -47, (byte) 73);
        BITMAP.put((byte) 47, (byte) -13);
        BITMAP.put((byte) -48, (byte) -32);
        BITMAP.put((byte) 48, (byte) 66);
        BITMAP.put((byte) -49, (byte) -125);
        BITMAP.put((byte) -50, (byte) 18);
        BITMAP.put((byte) 49, (byte) -84);
        BITMAP.put((byte) 50, (byte) -122);
        BITMAP.put((byte) -51, (byte) 124);
        BITMAP.put((byte) -52, (byte) -30);
        BITMAP.put((byte) 51, (byte) -81);
        BITMAP.put((byte) 52, (byte) 27);
        BITMAP.put((byte) -53, (byte) 50);
        BITMAP.put((byte) -54, (byte) -6);
        BITMAP.put((byte) 53, (byte) 68);
        BITMAP.put((byte) -55, (byte) -1);
        BITMAP.put((byte) 54, (byte) 63);
        BITMAP.put((byte) -56, (byte) 46);
        BITMAP.put((byte) 55, (byte) -117);
        BITMAP.put((byte) -57, (byte) 14);
        BITMAP.put((byte) 56, (byte) 71);
        BITMAP.put((byte) -58, (byte) -79);
        BITMAP.put((byte) 57, (byte) 86);
        BITMAP.put((byte) -59, (byte) -72);
        BITMAP.put((byte) 58, (byte) 110);
        BITMAP.put((byte) 59, (byte) 28);
        BITMAP.put((byte) -60, (byte) -29);
        BITMAP.put((byte) -61, (byte) 55);
        BITMAP.put((byte) 60, (byte) 89);
        BITMAP.put((byte) -62, (byte) -31);
        BITMAP.put((byte) 61, (byte) 100);
        BITMAP.put((byte) -63, (byte) -38);
        BITMAP.put((byte) 62, (byte) -48);
        BITMAP.put((byte) -64, (byte) 6);
        BITMAP.put((byte) 63, (byte) -114);
        BITMAP.put((byte) -65, (byte) 58);
        BITMAP.put((byte) 64, (byte) -113);
        BITMAP.put((byte) 65, (byte) -55);
        BITMAP.put((byte) -66, (byte) 105);
        BITMAP.put((byte) 66, (byte) -43);
        BITMAP.put((byte) -67, (byte) 79);
        BITMAP.put((byte) -68, (byte) 11);
        BITMAP.put((byte) 67, (byte) 29);
        BITMAP.put((byte) -69, (byte) 34);
        BITMAP.put((byte) 68, (byte) -36);
        BITMAP.put((byte) -70, (byte) 17);
        BITMAP.put((byte) 69, (byte) -88);
        BITMAP.put((byte) -71, (byte) -68);
        BITMAP.put((byte) 70, (byte) -126);
        BITMAP.put((byte) 71, (byte) 35);
        BITMAP.put((byte) -72, (byte) 123);
        BITMAP.put((byte) -73, (byte) 42);
        BITMAP.put((byte) 72, (byte) 77);
        BITMAP.put((byte) 73, (byte) 2);
        BITMAP.put((byte) -74, (byte) -49);
        BITMAP.put((byte) 74, (byte) 31);
        BITMAP.put((byte) -75, (byte) -73);
        BITMAP.put((byte) 75, (byte) -9);
        BITMAP.put((byte) -76, (byte) -39);
        BITMAP.put((byte) -77, (byte) -3);
        BITMAP.put((byte) 76, (byte) 51);
        BITMAP.put((byte) 77, (byte) 45);
        BITMAP.put((byte) -78, (byte) 62);
        BITMAP.put((byte) 78, (byte) -40);
        BITMAP.put((byte) -79, (byte) 85);
        BITMAP.put((byte) -80, (byte) -91);
        BITMAP.put((byte) 79, (byte) -100);
        BITMAP.put((byte) -81, (byte) 26);
        BITMAP.put((byte) 80, (byte) -124);
        BITMAP.put((byte) -82, (byte) -2);
        BITMAP.put((byte) 81, (byte) 53);
        BITMAP.put((byte) 82, (byte) 57);
        BITMAP.put((byte) -83, (byte) 97);
        BITMAP.put((byte) -84, (byte) 15);
        BITMAP.put((byte) 83, (byte) -76);
        BITMAP.put((byte) -85, (byte) -51);
        BITMAP.put((byte) 84, (byte) 99);
        BITMAP.put((byte) 85, (byte) 113);
        BITMAP.put((byte) -86, (byte) -116);
        BITMAP.put((byte) -87, (byte) 93);
        BITMAP.put((byte) 86, (byte) -102);
        BITMAP.put((byte) 87, (byte) -63);
        BITMAP.put((byte) -88, (byte) 120);
        BITMAP.put((byte) 88, (byte) -57);
        BITMAP.put((byte) -89, (byte) -60);
        BITMAP.put((byte) -90, (byte) -11);
        BITMAP.put((byte) 89, (byte) 37);
        BITMAP.put((byte) -91, (byte) 60);
        BITMAP.put((byte) 90, (byte) 116);
        BITMAP.put((byte) 91, (byte) 98);
        BITMAP.put((byte) -92, (byte) -127);
        BITMAP.put((byte) 92, (byte) -58);
        BITMAP.put((byte) -93, (byte) 101);
        BITMAP.put((byte) 93, (byte) -50);
        BITMAP.put((byte) -94, (byte) -107);
        BITMAP.put((byte) 94, (byte) 49);
        BITMAP.put((byte) -95, (byte) -56);
        BITMAP.put((byte) -96, (byte) -115);
        BITMAP.put((byte) 95, (byte) 125);
        BITMAP.put((byte) -97, (byte) 7);
        BITMAP.put((byte) 96, (byte) 94);
        BITMAP.put((byte) 97, (byte) 13);
        BITMAP.put((byte) -98, (byte) -18);
        BITMAP.put((byte) 98, (byte) -46);
        BITMAP.put((byte) -99, (byte) -92);
        BITMAP.put((byte) -100, (byte) 111);
        BITMAP.put((byte) 99, (byte) -112);
        BITMAP.put((byte) 100, (byte) -87);
        BITMAP.put((byte) -101, (byte) 112);
        BITMAP.put((byte) 101, (byte) 91);
        BITMAP.put((byte) -102, (byte) 119);
        BITMAP.put((byte) 102, (byte) -75);
        BITMAP.put((byte) -103, (byte) 106);
        BITMAP.put((byte) 103, (byte) -14);
        BITMAP.put((byte) -104, (byte) 52);
        BITMAP.put((byte) -105, (byte) 38);
        BITMAP.put((byte) 104, (byte) 126);
        BITMAP.put((byte) -106, (byte) 16);
        BITMAP.put((byte) 105, (byte) 21);
        BITMAP.put((byte) 106, (byte) -25);
        BITMAP.put((byte) -107, (byte) -66);
        BITMAP.put((byte) -108, (byte) 23);
        BITMAP.put((byte) 107, (byte) -119);
        BITMAP.put((byte) 108, (byte) -26);
        BITMAP.put((byte) -109, (byte) -83);
        BITMAP.put((byte) -110, (byte) 10);
        BITMAP.put((byte) 109, (byte) 72);
        BITMAP.put((byte) -111, (byte) -70);
        BITMAP.put((byte) 110, (byte) 80);
        BITMAP.put((byte) 111, (byte) -24);
        BITMAP.put((byte) -112, (byte) 56);
        BITMAP.put((byte) -113, (byte) -45);
        BITMAP.put((byte) 112, (byte) -71);
        BITMAP.put((byte) 113, (byte) -53);
        BITMAP.put((byte) -114, (byte) -61);
        BITMAP.put((byte) 114, (byte) -54);
        BITMAP.put((byte) -115, (byte) 117);
        BITMAP.put((byte) 115, (byte) -15);
        BITMAP.put((byte) -116, (byte) -110);
        BITMAP.put((byte) 116, (byte) 24);
        BITMAP.put((byte) -117, (byte) -105);
        BITMAP.put((byte) -118, (byte) 8);
        BITMAP.put((byte) 117, (byte) 84);
        BITMAP.put((byte) -119, (byte) 12);
        BITMAP.put((byte) 118, (byte) 78);
        BITMAP.put((byte) 119, (byte) 47);
        BITMAP.put((byte) -120, (byte) -109);
        BITMAP.put((byte) -121, (byte) -10);
        BITMAP.put((byte) 120, (byte) 114);
        BITMAP.put((byte) 121, (byte) -7);
        BITMAP.put((byte) -122, (byte) -21);
        BITMAP.put((byte) -123, (byte) 74);
        BITMAP.put((byte) 122, (byte) -82);
        BITMAP.put((byte) 123, (byte) -27);
        BITMAP.put((byte) -124, (byte) -95);
        BITMAP.put((byte) 124, (byte) 44);
        BITMAP.put((byte) -125, (byte) 82);
        BITMAP.put((byte) -126, (byte) -33);
        BITMAP.put((byte) 125, (byte) 70);
        BITMAP.put((byte) -127, (byte) -90);
        BITMAP.put((byte) 126, (byte) 122);
        BITMAP.put((byte) 127, (byte) -67);
        BITMAP.put((byte) -128, (byte) 102);

    }

    /*en-US : missing-ACCOUNT*/
    /*ro-RO : missing-ACCOUNT*/
    public static Lm ACCOUNT = new Lm(2, 24, -27, -43, -66, -37, -37, -66, 58, -3, 77, -93, 58, 77, -79, -15, -27, -4, -35, -35, -67, -79, 118, 117, -27, -43, -66, -37, -37, -66, 58, -3, 77, 120, -100, 77, -125, -67, -27, -4, -35, -35, -67, -79, 118, 117);
    /*en-US : missing-ONE*/
    /*ro-RO : ONE-RO-TEST*/
    public static Lm ONE = new Lm(2, 20, -27, -43, -66, -37, -37, -66, 58, -3, 77, -93, 58, 77, -79, -15, -27, -67, 118, 16, -67, 118, 16, 77, -125, -67, 77, 117, 16, -15, 117);
    /*en-US : $_EN_US_SETTINGS_$*/
    /*ro-RO : $_RO_SETTINGS_$*/
    public static Lm SETTINGS = new Lm(2, 20, -27, 21, 16, 118, 21, -79, -15, 21, -15, 16, 117, 117, -47, 118, 56, -15, 21, -27, -27, 21, -125, -67, 21, -15, 16, 117, 117, -47, 118, 56, -15, 21, -27);
    /*en-US : */
    /*ro-RO : */
    public static Lm EMPTY = new Lm(2, 2);
}