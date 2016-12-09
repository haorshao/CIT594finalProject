package mvc;

import ImageProcessing.Blur;
import ImageProcessing.SobelEdges;
import database.Loadfile;

public class Model {
	Loadfile loadBlur;
	Loadfile loadEdge;
	Blur blurSl;
	public Model(){
		loadBlur = null;
		loadEdge = null;
	}
	public void loadFile(String inputName, String outputNameBlur, String outputNameEdge){
		loadBlur = new Loadfile(inputName, outputNameBlur);
		loadEdge = new Loadfile(inputName, outputNameEdge);
		blurSl = new Blur(loadBlur.pixImage);
	}
	
	public void edgeDetection(){
		SobelEdges testEdge = new SobelEdges(loadEdge.pixImage);
		testEdge.SobelEdgeProcess();
		testEdge.setSobelEdges();
		loadEdge.setFile();
		loadEdge.outputFile();
	}
	
	public void blurButton(){
		long start = System.currentTimeMillis();
		Blur test = new Blur(loadBlur.pixImage, 300);
		test.blurProcess();
		test.setBlur();
		loadBlur.setFile();
		loadBlur.outputFile();
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
	}
	
	public void blurSlider(int error){
		for(int i = 0; i < error; i++){
			blurSl.blurProcess();
		}
		blurSl.setBlur();
		loadBlur.setFile();
		loadBlur.outputFile();
	}
}
