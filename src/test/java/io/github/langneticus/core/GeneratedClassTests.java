package io.github.langneticus.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeneratedClassTests {

    @Test
    public void success_Account_Index_0(){
        assertEquals("$missing-en-US$ACCOUNT",Lm.ACCOUNT.get(0));
    }

    @Test
    public void success_Account_Index_1(){
        assertEquals("$missing-ro-RO$ACCOUNT",Lm.ACCOUNT.get(1));
    }

    @Test
    public void success_Empty_Index_0(){
        assertEquals("",Lm.EMPTY.get(0));
    }

    @Test
    public void success_Empty_Index_1(){
        assertEquals("",Lm.EMPTY.get(1));
    }

    @Test
    public void success_Settings_Index_0(){
        assertEquals("$_EN_US_SETTINGS_$",Lm.SETTINGS.get(0));
    }

    @Test
    public void success_Settings_Index_1(){
        assertEquals("$_RO_SETTINGS_$",Lm.SETTINGS.get(1));
    }

    @Test
    public void success_One_Index_0(){
        assertEquals("$missing-en-US$ONE",Lm.ONE.get(0));
    }

    @Test
    public void success_One_Index_1(){
        assertEquals("ONE-RO-TEST",Lm.ONE.get(1));
    }

    @Test
    public void fails_Index_2(){
        assertThrows(ArrayIndexOutOfBoundsException.class,()->Lm.ACCOUNT.get(2));
    }
}
