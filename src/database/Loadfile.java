package database;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

public class Loadfile {
	
	public static void main(String[] args) {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("1.jpg"));
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
	}
}
