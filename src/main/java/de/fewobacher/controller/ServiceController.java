package de.fewobacher.controller;

import com.google.zxing.WriterException;
import de.fewobacher.model.RequestCodeModel;
import de.fewobacher.service.QrCodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@Validated
public class ServiceController
{
    final private static String CHARSET = "UTF-8";

    final private static String OUTPUT_FORMAT = "PNG";

    final private static String OUTPUT_FORMAT_MIME_TYPE = "image/png";

    private final QrCodeService service;

    public ServiceController(QrCodeService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<byte[]> create(@Valid @RequestBody RequestCodeModel request) throws IOException, WriterException
    {
        BufferedImage bufferedImage = service.createQR(
                request.getData(),
                CHARSET,
                request.getErrorLevel(),
                request.getImageSize(),
                request.getImageSize());

        // Prepare output
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, OUTPUT_FORMAT, bos);
        return ResponseEntity
                .ok()
                .header("Content-type", OUTPUT_FORMAT_MIME_TYPE)
                .body(bos.toByteArray());
    }
}
