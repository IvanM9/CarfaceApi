package com.septimo.carface.carface.util;

import java.util.UUID;

public class FileNameUtils {
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getFileName(String fileOriginName) {
        return UUID.randomUUID().toString().replace("-", "")
                + FileNameUtils.getSuffix(fileOriginName);
    }
}
