package database;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Pixels {
		int width;
		int height;
		private int[][] red;
		private int[][] green;
		private int[][] blue;
		BufferedImage img;
		public Pixels(int width, int height, BufferedImage currentImg) {
			this.width = width;
			this.height = height;
			img = currentImg;
			red = new int[this.width][this.height];
			green = new int[this.width][this.height];
			blue = new int[this.width][this.height];
		}
		public void readPixels(){
			for(int i = 0; i < this.width; i++){
				for(int j = 0; j < this.height; j++){
					Color color = new Color(img.getRGB(i, j));
					red[i][j] = color.getRed();
					green[i][j] = color.getGreen();
					blue[i][j] = color.getBlue();
				}
			}
		}
		
		public int[][] getRed() {
			return red;
		}
		public void setRed(int[][] red) {
			this.red = red;
		}
		public int[][] getGreen() {
			return green;
		}
		public void setGreen(int[][] green) {
			this.green = green;
		}
		public int[][] getBlue() {
			return blue;
		}
		public void setBlue(int[][] blue) {
			this.blue = blue;
		}
		
	
}
