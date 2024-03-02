package io.github.langneticus.core.util;

import io.github.langneticus.core.validation.exceptions.ValidationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ByteUtil {

    public static final int INT_256 = 256;

    private ByteUtil() {
    }

    public static Map<Byte, Byte> createSubstitutionBox() throws ValidationException {
        final List<Integer> range = IntStream.range(0, INT_256).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        final Map<Byte, Byte> toReturn = new HashMap<>(INT_256);
        for (int i = 0; i < range.size(); i++) {
            toReturn.put(unsignedIntToByte(i), unsignedIntToByte(range.get(i)));
        }
        return toReturn;
    }

    public static Map<Byte, Byte> createReverseSubstitutionBox(final Map<Byte, Byte> aMap) throws ValidationException {
        final Map<Byte, Byte> toReturn = new HashMap<>(INT_256);
        aMap.keySet().forEach((aKey)->toReturn.put(aMap.get(aKey),aKey));
        return toReturn;
    }


    public static int byteToUnsigned(final byte aByte) {
        return 0xFF & aByte;
    }

    public static byte unsignedIntToByte(final int anInt) {
        return (byte) (0xFF & anInt);
    }

    public static byte[] transform(final Map<Byte,Byte> substitutionBox, final byte[] data) throws ValidationException {

        final byte[] toReturn = new byte[data == null ? 0 : data.length];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = substitutionBox.get(data[i]);
        }
        return toReturn;
    }
}
