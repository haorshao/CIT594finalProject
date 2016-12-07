package database;

import java.awt.Color;
import java.awt.datatransfer.FlavorTable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

public class Loadfile {
	
	
	
	public static void main(String[] args) {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pixel[][] image = new Pixel[img.getWidth()][img.getHeight()];
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color color = new Color(img.getRGB(i, j));
				image[i][j] = new Pixel(color.getRed(), color.getGreen(), color.getBlue());
			}
		}
		for (int t = 0; t < 20; t++) {
			for (int i = 1; i < image.length-1; i++) {
				for (int j = 1; j < image[0].length-1; j++) {
					int sumr = 0;
					int sumg = 0;
					int sumb = 0;
					int[] delta = {-1, 0, 1};
					for (int deltax : delta) {
						for (int deltay : delta) {
							sumr += image[i+deltax][j+deltay].getRed();
							sumg += image[i+deltax][j+deltay].getGreen();
							sumb += image[i+deltax][j+deltay].getBlue();
						}
					}
					image[i][j] = new Pixel(sumr/9, sumg/9, sumb/9);
				}
			}
		}
		
		
		BufferedImage outputimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < image.length; i++ ) {
			for (int j = 0; j < image[0].length; j++) {
				img.setRGB(i, j, new Color(image[i][j].getRed(),image[i][j].getGreen(),image[i][j].getBlue()).getRGB());
			}
		}
		try {
			ImageIO.write(img, "jpg", new File("output.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
