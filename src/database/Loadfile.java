/**
 * This Loadfile class is for loading jpg images into the system and converting 
 * the jpg images into the Pixels class format.
 * @author Haoran Shao
 */
package database;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import ImageProcessing.SobelEdges;

public class Loadfile {
	public String inputFileName;
	public String outputFileName;
	public Pixels pixImage;
	public int imgType;
	public int width;
	public int height;
	public BufferedImage outputImage;
	public BufferedImage currentImg;
	/**
	 * 
	 * @param inputFileName
	 * @param outputFileName
	 */
	public Loadfile(String inputFileName, String outputFileName){
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		currentImg = null;
		try {
			currentImg = ImageIO.read(new File(this.inputFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = currentImg.getWidth();
		this.height = currentImg.getHeight();
		pixImage = new Pixels(currentImg.getWidth(), currentImg.getHeight(), currentImg);
		pixImage.readPixels();//comment this line if needed
		this.imgType = currentImg.getType();
		outputImage = new BufferedImage(this.width, this.height, this.imgType);
	}
	
	public Loadfile(URL link, String inputFileName, String outputFileName){
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		currentImg = null;
		try {
			currentImg = ImageIO.read(link);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = currentImg.getWidth();
		this.height = currentImg.getHeight();
		pixImage = new Pixels(currentImg.getWidth(), currentImg.getHeight(), currentImg);
		pixImage.readPixels();//comment this line if needed
		this.imgType = currentImg.getType();
		outputImage = new BufferedImage(this.width, this.height, this.imgType);
	}
	public void setFile(){
		short[][] red = pixImage.getRed();
		short[][] green = pixImage.getGreen();
		short[][] blue = pixImage.getBlue();
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
//				int xindex = this.width - 1 - i;
//				int yindex = this.height - 1 - j;
//				int rgb = new Color(red[xindex][yindex], green[xindex][yindex], blue[xindex][yindex]).getRGB();
				int rgb = new Color(red[i][j], green[i][j], blue[i][j]).getRGB();
				outputImage.setRGB(i, j, rgb);
			}
		}
	}
	
	
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public void outputFile(){
		System.out.println("Processed File Starts Outputing..");
		try {
			ImageIO.write(this.outputImage, "jpg", new File(this.outputFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(this.outputFileName + " File Output Finished");
	}
	
	/**
	 * Main method in this class for testing ONLY.
	 * @param args
	 */
	public static void main(String[] args) {
		Loadfile test = new Loadfile("test.jpg", "outputSobelEdgesTest.jpg");
		SobelEdges testEdge = new SobelEdges(test.pixImage);
		testEdge.SobelEdgeProcess();
		testEdge.setSobelEdges();
//		Blur testBlur = new Blur(test.pixImage, 100);
//		testBlur.blurProcess();
//		testBlur.setBlur();
		test.setFile();
		test.outputFile();
	}
}
