package com.zhaoyouhua.spider.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 *@Description
 *create by zhoukc
 */
public class ArrayUtil {

    public static byte[] joinBytes(byte[] byte1, byte[] byte2) {
        byte[] bytes = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, bytes, 0, byte1.length);
        System.arraycopy(byte2, 0, bytes, byte1.length, byte2.length);
        return bytes;
    }

    public static byte[] padRightBytes(byte[] bytes, int length) {
        byte[] newbytes = new byte[length];
        System.arraycopy(bytes, 0, newbytes, 0, bytes.length);
        return newbytes;
    }

    public static byte[] limitBytes(byte[] bytes, int skip) {
        return limitBytes(bytes, skip, bytes.length - skip);
    }

    public static byte[] limitBytes(byte[] bytes, int skip, int take) {
        byte[] newBytes = new byte[take];
        System.arraycopy(bytes, skip, newBytes, 0, take);
        return newBytes;
    }

    public static byte[] joinBytes(List<byte[]> bytesList) {
        int len = 0;
        for (Iterator localIterator = bytesList.iterator(); localIterator.hasNext(); ) {
            byte[] item = (byte[]) localIterator.next();
            len += item.length;
        }
        return joinBytes(bytesList, len);
    }

    public static byte[] joinBytes(List<byte[]> bytesList, int length) {
        byte[] bytes = new byte[length];
        int byteLength = 0;
        for (Iterator localIterator = bytesList.iterator(); localIterator.hasNext(); ) {
            byte[] item = (byte[]) localIterator.next();
            System.arraycopy(item, 0, bytes, byteLength, item.length);
            byteLength += item.length;
        }
        return bytes;
    }

    public static Field[] joinFields(Field[] field1, Field[] field2) {
        Field[] fields = new Field[field1.length + field2.length];
        System.arraycopy(field1, 0, fields, 0, field1.length);
        System.arraycopy(field2, 0, fields, field1.length, field2.length);
        return fields;
    }

}
