package de.fewobacher.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.fewobacher.constant.ErrorLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QrCodeService
{
    Logger logger = LoggerFactory.getLogger(QrCodeService.class);

    @Value("${qr.image-path.codes:}")
    private String qrCodeImagePath;

    private ErrorCorrectionLevel mapErrorLevel(ErrorLevel el) {
        return switch (el) {
            case LOW -> ErrorCorrectionLevel.L;
            case MEDIUM -> ErrorCorrectionLevel.M;
            case QUARTILE -> ErrorCorrectionLevel.Q;
            case HIGH -> ErrorCorrectionLevel.H;
        };
    }

    public BufferedImage createQR(String data,
                                  String charset,
                                  ErrorLevel errorLevel,
                                  int width,
                                  int height)
            throws WriterException, IOException {

        logger.debug("Creating new qr code with error level [{}], size [{}x{}] data [{}]",
                errorLevel, width, height, data);

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

        if (StringUtils.hasText(qrCodeImagePath))
        {
            Path filename = Paths.get(qrCodeImagePath, "qr-"+ UUID.randomUUID()+".png");

            if (!filename.getParent().toFile().exists())
            {
                filename.getParent().toFile().mkdirs();
            }

            MatrixToImageWriter.writeToPath(matrix, "png", filename);
            logger.info("Saved image to {}", filename);
        }

        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
