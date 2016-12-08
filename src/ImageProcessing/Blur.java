package ImageProcessing;

import java.awt.image.BufferedImage;

import database.Pixels;

public class Blur {
	int numIter;
	Pixels current;
//	Pixels processed;
	int[][] redResult;
	int[][] greenResult;
	int[][] blueResult;
	public Blur(Pixels current, int numIter){
		System.out.println("Blur loading..");
		this.current = current;
		this.numIter = numIter;
//		BufferedImage newest = new BufferedImage(current.getWidth(),current.getHeight(),current.getImgType());
//		processed = new Pixels(newest.getWidth(), newest.getHeight(), newest);
		System.out.println("Blur loading finished...");
	}
	public void blurProcess(){
		System.out.println("Start Blurring...");
		if(this.numIter <= 0){
			System.out.println("ERROR: BLUR ITERATIONS WRONG");
			return;
		}
		int width = current.getWidth();
		int height = current.getHeight();
		int[][] red = current.getRed();
		int[][] green = current.getGreen();
		int[][] blue = current.getBlue();
		int[][] redTemp = new int[width + 2][height + 2];
		int[][] blueTemp = new int[width + 2][height + 2];
		int[][] greenTemp = new int[width + 2][height + 2];
		redResult = new int[width][height];
		blueResult = new int[width][height];
		greenResult = new int[width][height];
		/*Enlarge the original pixel array with 1 on the boundary*/
		for (int i = 0; i < greenTemp.length; i++) {
			  redTemp[i][0] = 0;
			  redTemp[i][greenTemp[0].length - 1] = 0;
			  greenTemp[i][0] = 0;
			  greenTemp[i][greenTemp[0].length - 1] = 0;
			  blueTemp[i][0] = 0;
			  blueTemp[i][greenTemp[0].length - 1] = 0;
		}
		for (int i = 0; i < greenTemp[0].length; i++) {
			  redTemp[0][i] = 0;
			  redTemp[greenTemp.length - 1][i] = 0;
			  greenTemp[0][i] = 0;
			  greenTemp[greenTemp.length - 1][i] = 0;
			  blueTemp[0][i] = 0;
			  blueTemp[greenTemp.length - 1][i] = 0;		
		}
		for (int i = 1; i < greenTemp.length - 1; i++) {
			for (int j = 1; j < greenTemp[0].length - 1; j++) {
				redTemp[i][j] = red[i - 1][j - 1];
				greenTemp[i][j] = green[i - 1][j - 1];
				blueTemp[i][j] = blue[i-1][j-1];
			}
		}
		for (int count = 1; count <= this.numIter; count++) {
			  for (int i = 0; i < width; i++) {
				  for (int j = 0; j < height; j++) {
					  redResult[i][j] = 0;
					  greenResult[i][j] = 0;
					  blueResult[i][j] = 0;
				  }
			  }
			  for (int i = 0; i < width; i++) {
				  for (int j = 0; j < height; j++) {
					  for (int i1 = i + 1 - 1; i1 <= i + 1 + 1; i1++) {
						  for (int j1 = j; j1 <= j + 2; j1++) {
							redResult[i][j] += redTemp[i1][j1];
							blueResult[i][j] += blueTemp[i1][j1];
							greenResult[i][j] += greenTemp[i1][j1];
						  }
					  }
					  if (i == 0 || i == width - 1 || j == height - 1 || j == 0) {
						  if((i == 0 || i == width - 1) && (j == 0 || j == height - 1)){
							  redResult[i][j] /= 4; 
							  blueResult[i][j] /= 4; 
							  greenResult[i][j] /= 4; 
						  }else{
							  redResult[i][j] /= 6; 
							  blueResult[i][j] /= 6; 
							  greenResult[i][j] /= 6;
						  }
					  }else{
						  redResult[i][j] /= 9; 
						  blueResult[i][j] /= 9; 
						  greenResult[i][j] /= 9;
					  }
				  }
			  }
			  for (int i = 1; i < greenTemp.length - 1; i++) {
					for (int j = 1; j < greenTemp[0].length - 1; j++) {
						redTemp[i][j] = redResult[i - 1][j - 1];
						greenTemp[i][j] = greenResult[i - 1][j - 1];
						blueTemp[i][j] = blueResult[i-1][j-1];
					}
			  }
		}
		System.out.println("Blur process finished");
	}
	public void setBlur(){
		current.setRed(redResult);
		current.setGreen(greenResult);
		current.setBlue(blueResult);
		System.out.println("Blur DONE");
	}
	
	public static void main(String[] args) {
		
	}

}
