package database;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Pixels {
		private int width;
		private int height;
		private short[][] red;
		private short[][] green;
		private short[][] blue;
		BufferedImage img;
		private int imgType;
		public Pixels(int width, int height, BufferedImage currentImg) {
			this.width = width;
			this.height = height;
			img = currentImg;
			imgType = img.getType();
			red = new short[this.width][this.height];
			green = new short[this.width][this.height];
			blue = new short[this.width][this.height];
		}
		public void readPixels(){
			for(int i = 0; i < this.width; i++){
				for(int j = 0; j < this.height; j++){
					Color color = new Color(img.getRGB(i, j));
					red[i][j] = (short)color.getRed();
					green[i][j] = (short)color.getGreen();
					blue[i][j] = (short)color.getBlue();
				}
			}
		}
		public int getImgType() {
			return imgType;
		}
		public int getWidth() {
			return width;
		}
		public int getHeight() {
			return height;
		}
		public short[][] getRed() {
			return red;
		}
		public void setRed(short[][] red) {
			this.red = red;
		}
		public short[][] getGreen() {
			return green;
		}
		public void setGreen(short[][] green) {
			this.green = green;
		}
		public short[][] getBlue() {
			return blue;
		}
		public void setBlue(short[][] blue) {
			this.blue = blue;
		}
		public static void main(String[] args){
			int[][] temp = new int[2][3];
			System.out.println(temp.length);
		}
	
}
