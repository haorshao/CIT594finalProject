package mvc;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.HashMap;

import ImageProcessing.Blur;
import ImageProcessing.SobelEdges;
import database.Loadfile;
/**
 * The model will use the algorithms in image processing package
 * to handle all the processing
 *
 */
public class Model {
	Loadfile loadBlur;
	Loadfile loadEdge;
	Loadfile loadBlurSl;
	String inputName;
	String outputName;
	HashMap<Integer, BufferedImage> map;
	Controller theController;
	/**
	 * Constructor
	 */
	public Model(){
		loadBlur = null;
		loadEdge = null;
		loadBlurSl = null;
		map = new HashMap<Integer, BufferedImage>();
	}
	
	/**
	 * set controller
	 * @param theController
	 */
	public void setTheController(Controller theController) {
		this.theController = theController;
	}
	/**
	 * load file and blur
	 * @param inputName
	 * @param outputNameBlur
	 * @param outputNameEdge
	 */
	public void loadFile(String inputName, String outputNameBlur, String outputNameEdge){
//		System.out.println("Loading");
		this.inputName = inputName;
		this.outputName = outputNameBlur;
		loadBlur = new Loadfile(inputName, outputNameBlur);
		loadEdge = new Loadfile(inputName, outputNameEdge);
		loadBlurSl = new Loadfile(inputName, outputNameBlur);
		blurSlider();
	}
	/**
	 * load image according the link
	 * @param link
	 * @param inputName
	 * @param outputNameBlur
	 * @param outputNameEdge
	 */
	public void loadLink(URL link, String inputName, String outputNameBlur, String outputNameEdge){
		this.inputName = inputName;
		this.outputName = outputNameBlur;
		loadBlur = new Loadfile(link, inputName, outputNameBlur);
		loadEdge = new Loadfile(link, inputName, outputNameEdge);
		loadBlurSl = new Loadfile(link, inputName, outputNameBlur);
		blurSlider();
	}
	/**
	 * detect the edge
	 */
	public void edgeDetection(){
		SobelEdges testEdge = new SobelEdges(loadEdge.pixImage);
		testEdge.SobelEdgeProcess();
		testEdge.setSobelEdges();
		loadEdge.setFile();
//		loadEdge.outputFile();
	}
	/**
	 * export the blur image
	 */
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
	/**
	 * export the edge image
	 */
	public void exportEdge(){
		loadEdge.outputFile();
	}
	/**
	 * copy buffered image
	 * @param bi
	 * @return
	 */
	public BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	/**
	 * iterate 100 blurring and store all the bufferedimage
	 */
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
