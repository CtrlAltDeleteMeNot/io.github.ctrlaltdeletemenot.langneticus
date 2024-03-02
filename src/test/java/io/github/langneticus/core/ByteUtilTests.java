package io.github.langneticus.core;


import io.github.langneticus.core.util.ByteUtil;
import io.github.langneticus.core.validation.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

public class ByteUtilTests {
    @Test
    public void generateSubstitutionBoxTest() throws ValidationException {
        final Map<Byte, Byte> substitutionBox = ByteUtil.createSubstitutionBox();
        final Map<Byte, Byte> reverseSubstitutionBox = ByteUtil.createReverseSubstitutionBox(substitutionBox);
        System.out.println("Substitution box:" + substitutionBox);
        System.out.println("Reverse substitution box:" + reverseSubstitutionBox);
        Assertions.assertEquals(256, substitutionBox.keySet().size());
        Assertions.assertEquals(256, reverseSubstitutionBox.keySet().size());
        for (final Byte aKey : substitutionBox.keySet()) {
            Assertions.assertEquals(aKey, reverseSubstitutionBox.get(substitutionBox.get(aKey)));
        }
    }

    @Test
    public void obfuscateTest() throws ValidationException {
        final Map<Byte, Byte> substitutionBox = ByteUtil.createSubstitutionBox();
        final Map<Byte, Byte> reverseSubstitutionBox = ByteUtil.createReverseSubstitutionBox(substitutionBox);
        final byte[] data = new byte[]{0x01, 0x02, 0x03, 0x04};
        final byte[] obfuscated = ByteUtil.transform(substitutionBox, data);
        final byte[] plain = ByteUtil.transform(reverseSubstitutionBox, obfuscated);
        System.out.println("Input:     " + Arrays.toString(data));
        System.out.println("Obfuscated:    " + Arrays.toString(obfuscated));
        System.out.println("Plain:    " + Arrays.toString(plain));
        Assertions.assertArrayEquals(data, plain);
    }

    @Test
    public void byteToUnsignedReturns255() {
        final byte given = (byte) -1;
        final int b2ui = ByteUtil.byteToUnsigned(given);
        final byte ui2b = ByteUtil.unsignedIntToByte(b2ui);
        Assertions.assertEquals(given, ui2b);
        Assertions.assertEquals(255, b2ui);

    }

    @Test
    public void byteToUnsignedReturns128() {
        final int b2ui = ByteUtil.byteToUnsigned(Byte.MIN_VALUE);
        final byte ui2b = ByteUtil.unsignedIntToByte(b2ui);
        Assertions.assertEquals(Byte.MIN_VALUE, ui2b);
        Assertions.assertEquals(128, b2ui);
    }

    @Test
    public void byteToUnsignedReturns127() {
        final int b2ui = ByteUtil.byteToUnsigned(Byte.MAX_VALUE);
        final byte ui2b = ByteUtil.unsignedIntToByte(b2ui);
        Assertions.assertEquals(127, b2ui);
        Assertions.assertEquals(Byte.MAX_VALUE, ui2b);
    }
}
