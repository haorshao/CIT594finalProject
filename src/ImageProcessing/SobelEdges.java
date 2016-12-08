package ImageProcessing;

import database.Pixels;

public class SobelEdges {
	Pixels current;
	int[][] redResult;
	int[][] greenResult;
	int[][] blueResult;
	long[][] energyPrev;
	int[][] gxRed;
	int[][] gxGreen;
	int[][] gxBlue;
	int[][] gyRed;
	int[][] gyGreen;
	int[][] gyBlue;
	int width;
	int height;
	public SobelEdges(Pixels current){
		System.out.println("SobelEdges loading...");
		this.current = current;
		this.width = current.getWidth();
		this.height = current.getHeight();
		redResult = new int[width][height];
		greenResult = new int[width][height];
		blueResult = new int[width][height];
		energyPrev = new long[width][height];
		gxRed = new int[width][height];
		gxGreen = new int[width][height];
		gxBlue = new int[width][height];
		gyRed = new int[width][height];
		gyGreen = new int[width][height];
		gyBlue = new int[width][height];
		System.out.println("SobelEdges loading finished...");
	}
	private static int mag2gray(long mag) {
	    int intensity = (int)(30.0 * Math.log(1.0 + (double) mag) - 256.0);

	    // Make sure the returned intensity is in the range 0...255, regardless of
	    // the input value.
	    if (intensity < 0) {
	      intensity = 0;
	    } else if (intensity > 255) {
	      intensity = 255;
	    }
	    return intensity;
	}
	public void SobelEdgeProcess(){
		System.out.println("Start Sobel Edge detecting...");
		int[][] gxConstant =  {{1,0,-1},
							   {2,0,-2},
			  				   {1,0,-1}};
		int[][] gyConstant = {{1,2,1},
								{0,0,0},
								{-1,-2,-1}};
		int[][] red = current.getRed();
		int[][] green = current.getGreen();
		int[][] blue = current.getBlue();
		int[][] redTemp = new int[width + 2][height + 2];
		int[][] greenTemp = new int[width + 2][height + 2];
		int[][] blueTemp = new int[width + 2][height + 2];
		for (int i = 1; i < greenTemp.length - 1; i++) {
			  redTemp[i][0] = red[i-1][0];
			  redTemp[i][greenTemp[0].length - 1] = red[i - 1][this.height - 1];
			  greenTemp[i][0] = green[i-1][0];
			  greenTemp[i][greenTemp[0].length - 1] = green[i - 1][this.height - 1];
			  blueTemp[i][0] = blue[i-1][0];
			  blueTemp[i][greenTemp[0].length - 1] = blue[i - 1][this.height - 1];
		}
		for (int i = 1; i < greenTemp[0].length - 1; i++) {
			  redTemp[0][i] = red[0][i - 1];
			  redTemp[greenTemp.length - 1][i] = red[this.width - 1][i - 1];
			  greenTemp[0][i] = green[0][i - 1];
			  greenTemp[greenTemp.length - 1][i] = green[this.width - 1][i - 1];
			  blueTemp[0][i] = blue[0][i - 1];
			  blueTemp[greenTemp.length - 1][i] = blue[this.width - 1][i - 1];		
		}
		for (int i = 1; i < greenTemp.length - 1; i++) {
			for (int j = 1; j < greenTemp[0].length - 1; j++) {
				redTemp[i][j] = red[i - 1][j - 1];
				greenTemp[i][j] = green[i - 1][j - 1];
				blueTemp[i][j] = blue[i-1][j-1];
			}
		}
		for (int i = 0; i < greenTemp.length; i += greenTemp.length - 1) {
			  for (int j = 0; j < greenTemp[0].length; j += greenTemp[0].length - 1) {
					if (i == 0) {
						redTemp[i][j] = redTemp[i + 1][j];
						greenTemp[i][j] = greenTemp[i + 1][j];
						blueTemp[i][j] = blueTemp[i + 1][j];
					}else{
						redTemp[i][j] = redTemp[i - 1][j];
						greenTemp[i][j] = greenTemp[i - 1][j];
						blueTemp[i][j] = blueTemp[i - 1][j];
					}
			  }
		}
		int indX = 0;
		int indY = 0;
		for (int i = 0; i < this.width; i++) {
		  for (int j = 0; j < this.height; j++) {
			  for (int i1 = i; i1 <= i + 2; i1++) {
				  for (int j1 = j; j1 <= j + 2; j1++) {
					  gxRed[i][j] += gxConstant[indX][indY] * redTemp[i1][j1];
					  gyRed[i][j] += gyConstant[indX][indY] * redTemp[i1][j1];
					  gxGreen[i][j] += gxConstant[indX][indY] * greenTemp[i1][j1];
					  gyGreen[i][j] += gyConstant[indX][indY] * greenTemp[i1][j1];
					  gxBlue[i][j] += gxConstant[indX][indY] * blueTemp[i1][j1];
					  gyBlue[i][j] += gyConstant[indX][indY] * blueTemp[i1][j1];
					  indY++;
				  }
				  indY = 0;
				  indX ++ ;
			  }
			  indX = 0;
		  }
		}
		for (int i = 0; i < this.width; i++) {
		  for (int j = 0; j < this.height; j++) {
			energyPrev[i][j] = gxRed[i][j] * gxRed[i][j] + gyRed[i][j] * gyRed[i][j] + gxGreen[i][j] * gxGreen[i][j] + gyGreen[i][j] * gyGreen[i][j] 
					+ gxBlue[i][j] * gxBlue[i][j] + gyBlue[i][j] * gyBlue[i][j]; 
		  }
		}
		System.out.println("Sobel Edge detection finished...");
	}
	public void setSobelEdges(){
		for (int i = 0; i < this.width; i++) {
		  for (int j = 0; j < this.height; j++) {
				redResult[i][j] = mag2gray(energyPrev[i][j]);
				greenResult[i][j] = mag2gray(energyPrev[i][j]);
				blueResult[i][j] = mag2gray(energyPrev[i][j]);
		  }
		}
		current.setRed(redResult);
		current.setGreen(greenResult);
		current.setBlue(blueResult);
		System.out.println("Sobel Edge process DONE");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
