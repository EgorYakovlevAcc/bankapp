package com.presentation.demo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.presentation.demo.model.Bill;
import com.presentation.demo.service.bill.BillService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Locale;

@Controller
public class QRCreateController {

    @Autowired
    private BillService billService;

    @GetMapping(value = "/getqrcode")
    @ResponseBody
    public String getQRCode(@RequestParam("id") Integer id,
                            @RequestParam("type") String type,
                            @RequestParam("amount") Integer amount,
                            Model model,
                            HttpServletResponse response) {
        if (billService.findBillById(id) != null &&
                ("PUT_CASH".equals(type.toUpperCase()) || "GET_CASH".equals(type.toUpperCase()))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmSS", Locale.ENGLISH);
            Bill bill = billService.findBillById(id);
            if ("GET_CASH".equals(type.toUpperCase()) && BigInteger.valueOf(amount).compareTo(bill.getBalance()) == -1)
                return "Insufficient funds int the account";
            int QRSize = 500;
            String date = dtf.format(LocalDateTime.now());
            String res = id + type.toUpperCase() + amount + date;
            try
            {
                Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(res, BarcodeFormat.QR_CODE, QRSize, QRSize, hintMap);
                int matrixWidth = bitMatrix.getWidth();
                BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
                image.createGraphics();
                Graphics2D graphics = (Graphics2D) image.getGraphics();

                graphics.setColor(Color.white);
                graphics.fillRect(0, 0, matrixWidth, matrixWidth);

                Color mainColor = new Color(77, 122, 160);
                graphics.setColor(mainColor);

                for (int i = 0; i < matrixWidth; i++) {
                    for (int j = 0; j < matrixWidth; j++) {
                        if (bitMatrix.get(i, j)) {
                            graphics.fillRect(i, j, 1, 1);
                        }
                    }
                }
                BufferedImage logo = ImageIO.read( new File("src/main/resources/static/images/NCLOGO.png"));
                double scale = calcScaleRate(image, logo);
                logo = getScaledImage( logo,
                        (int)( logo.getWidth() * scale),
                        (int)( logo.getHeight() * scale));
                graphics.drawImage( logo,
                        image.getWidth()/2 - logo.getWidth()/2,
                        image.getHeight()/2 - logo.getHeight()/2,
                        image.getWidth()/2 + logo.getWidth()/2,
                        image.getHeight()/2 + logo.getHeight()/2,
                        0, 0, logo.getWidth(), logo.getHeight(), null);
                File file = new File("src/main/resources/static/images/qrcode.jpg");
                ImageIO.write(image, "jpg", file);
                response.setContentType("image/jpeg, image/jpg");
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                baos.flush();
                byte[] byteArray = baos.toByteArray();
                response.getOutputStream().write(byteArray);
                response.getOutputStream().close();
            }
            catch (Exception e){
                e.getMessage();
            }
            return "QR";
        }
        return "Something goes wrong";
    }

    private float calcScaleRate(BufferedImage image, BufferedImage logo){
        return 1.0f / 4;
    }

    private BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp( scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter( image, new BufferedImage(width, height, image.getType()));
    }


}
