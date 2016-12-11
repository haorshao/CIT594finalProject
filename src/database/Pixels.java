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
		/**
		 * Constructor
		 * @param width
		 * @param height
		 * @param currentImg
		 */
		public Pixels(int width, int height, BufferedImage currentImg) {
			this.width = width;
			this.height = height;
			img = currentImg;
			imgType = img.getType();
			red = new short[this.width][this.height];
			green = new short[this.width][this.height];
			blue = new short[this.width][this.height];
		}
		/**
		 * read the RGB and store in 2d array
		 */
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
		/**
		 * get image type
		 * @return
		 */
		public int getImgType() {
			return imgType;
		}
		/**
		 * get width
		 * @return
		 */
		public int getWidth() {
			return width;
		}
		/**
		 * get height
		 * @return
		 */
		public int getHeight() {
			return height;
		}
		/**
		 * get red array
		 * @return
		 */
		public short[][] getRed() {
			return red;
		}
		/**
		 * set red array
		 * @param red
		 */
		public void setRed(short[][] red) {
			this.red = red;
		}
		/**
		 * get green array
		 * @return
		 */
		public short[][] getGreen() {
			return green;
		}
		/**
		 * set green array
		 * @param green
		 */
		public void setGreen(short[][] green) {
			this.green = green;
		}
		/**
		 * get blue array
		 * @return
		 */
		public short[][] getBlue() {
			return blue;
		}
		/**
		 * set blue array
		 * @param blue
		 */
		public void setBlue(short[][] blue) {
			this.blue = blue;
		}
}
