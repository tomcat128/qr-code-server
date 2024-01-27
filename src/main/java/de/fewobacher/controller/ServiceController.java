package de.fewobacher.controller;

import com.google.zxing.WriterException;
import de.fewobacher.service.QrCodeService;
import de.fewobacher.model.RequestCodeModel;
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
                null,
                "UTF-8",
                request.getErrorLevel(),
                request.getImageSize(),
                request.getImageSize());

        // Prepare output
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", bos);
        return ResponseEntity.ok().header("Content-type", "image/png").body(bos.toByteArray());
    }
}
