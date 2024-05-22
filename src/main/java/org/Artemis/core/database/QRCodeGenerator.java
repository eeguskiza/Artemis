package org.Artemis.core.database;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeGenerator {
    void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException;
}
