package com.example.imgprocessing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import com.example.imgprocessing.HelloApplication;

public class ImgProcessing {
    public static Image grayscale(Image a) {
        int width = (int) a.getWidth();
        int height = (int) a.getHeight();

        PixelReader pixelReader = a.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                Pixel pixel = new Pixel(
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255)
                );
                int avg = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                Color gray = Color.rgb(avg, avg, avg);
                writer.setColor(x, y, gray);
            }
        }
        return result;
    }
    public static Image inversion(Image a){
        int width = (int) a.getWidth();
        int height = (int) a.getHeight();

        PixelReader reader = a.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                Pixel pixel = new Pixel(
                        (int)(color.getRed() * 255),
                        (int)(color.getGreen() * 255),
                        (int)(color.getBlue() * 255)
                );
                Color inverted = Color.rgb(
                        255 - pixel.getRed(),
                        255 - pixel.getGreen(),
                        255 - pixel.getBlue()
                );
                writer.setColor(x, y, inverted);
            }
        }

        return result;
    }
    public static Image blur(Image a) {
        int width = (int) a.getWidth();
        int height = (int) a.getHeight();

        PixelReader reader = a.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        int[][] kernel = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        int kernelSize = 3;
        int kernelSum = 9;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int rSum = 0, gSum = 0, bSum = 0;

                for (int ky = 0; ky < kernelSize; ky++) {
                    for (int kx = 0; kx < kernelSize; kx++) {
                        int dx = x + kx - 1;
                        int dy = y + ky - 1;
                        Color color = reader.getColor(dx, dy);
                        Pixel pixel = new Pixel(
                                (int)(color.getRed() * 255),
                                (int)(color.getGreen() * 255),
                                (int)(color.getBlue() * 255)
                        );
                        rSum += pixel.getRed() * kernel[ky][kx];
                        gSum += pixel.getGreen() * kernel[ky][kx];
                        bSum += pixel.getBlue() * kernel[ky][kx];
                    }
                }

                Color blurred = Color.rgb(
                        rSum / kernelSum,
                        gSum / kernelSum,
                        bSum / kernelSum
                );
                writer.setColor(x, y, blurred);
            }
        }

        return result;
    }
}
