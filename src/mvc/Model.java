package mvc;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.HashMap;

import ImageProcessing.Blur;
import ImageProcessing.SobelEdges;
import database.Loadfile;

public class Model {
	Loadfile loadBlur;
	Loadfile loadEdge;
	Loadfile loadBlurSl;
	String inputName;
	String outputName;
	HashMap<Integer, BufferedImage> map;
	Controller theController;
	public Model(){
		loadBlur = null;
		loadEdge = null;
		loadBlurSl = null;
		map = new HashMap<Integer, BufferedImage>();
//		theController = new Controller(theView, this);
//		this.theController = theController;
	}
	
	public void setTheController(Controller theController) {
		this.theController = theController;
	}

	public void loadFile(String inputName, String outputNameBlur, String outputNameEdge){
//		System.out.println("Loading");
		this.inputName = inputName;
		this.outputName = outputNameBlur;
		loadBlur = new Loadfile(inputName, outputNameBlur);
		loadEdge = new Loadfile(inputName, outputNameEdge);
		loadBlurSl = new Loadfile(inputName, outputNameBlur);
		blurSlider();
//		blurOri = new Blur(loadBlurSl.pixImage);
	}
	
	public void loadLink(URL link, String inputName, String outputNameBlur, String outputNameEdge){
		this.inputName = inputName;
		this.outputName = outputNameBlur;
		loadBlur = new Loadfile(link, inputName, outputNameBlur);
		loadEdge = new Loadfile(link, inputName, outputNameEdge);
		loadBlurSl = new Loadfile(link, inputName, outputNameBlur);
		blurSlider();
	}
	
	public void edgeDetection(){
		SobelEdges testEdge = new SobelEdges(loadEdge.pixImage);
		testEdge.SobelEdgeProcess();
		testEdge.setSobelEdges();
		loadEdge.setFile();
//		loadEdge.outputFile();
	}
	
	public void exportBlur(){
		if(theController.url == false){
			loadBlur = new Loadfile(inputName, this.outputName);
		}else{
			loadBlur = new Loadfile(theController.link, inputName, this.outputName);
		}
		long start = System.currentTimeMillis();
		Blur test = new Blur(loadBlur.pixImage, theController.prevSlider);
		test.blurProcess();
		test.setBlur();
		loadBlur.setFile();
		loadBlur.setOutputFileName("Blur-" + theController.prevSlider + "-" + outputName);
		loadBlur.outputFile();
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
	}
	
	public void exportEdge(){
		loadEdge.outputFile();
	}
	
	public BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public void blurSlider(){
		Blur blurSl = new Blur(loadBlurSl.pixImage);
		for(int i = 1; i <= 100; i++){
			blurSl.blurProcess();
			blurSl.setBlur();
			loadBlurSl.setFile();
			map.put(i, deepCopy(loadBlurSl.outputImage));
		}
	}
}
