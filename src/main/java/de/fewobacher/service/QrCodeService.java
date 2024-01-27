package de.fewobacher.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.fewobacher.constant.ErrorLevel;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeService {

    private ErrorCorrectionLevel mapErrorLevel(ErrorLevel el) {
        return switch (el) {
            case LOW -> ErrorCorrectionLevel.L;
            case MEDIUM -> ErrorCorrectionLevel.M;
            case QUARTILE -> ErrorCorrectionLevel.Q;
            case HIGH -> ErrorCorrectionLevel.H;
        };
    }

    public BufferedImage createQR(  String data,
                                    String path,
                                    String charset,
                                    ErrorLevel errorLevel,
                                    int height, int width)
            throws WriterException, IOException {

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION, this.mapErrorLevel(errorLevel));

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE,
                width,
                height,
                hashMap);

        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
