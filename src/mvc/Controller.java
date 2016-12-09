package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import ImageProcessing.Blur;
import ImageProcessing.SobelEdges;
import database.Loadfile;


public class Controller implements ActionListener {
	View theView;
//	Model theModel;
	Loadfile loadBlur;
	Loadfile loadEdge;
	public Controller(View theView){
		this.theView = theView;
//		this.theModel = theModel;
		this.theView.addViewListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("Load")){
			try {
                loadFile();
            } catch (IOException ioe) {
                System.out.println("not able to load from the file");
            }
		}else if(cmd.equals("Edge Detection")){
			SobelEdges testEdge = new SobelEdges(loadEdge.pixImage);
			testEdge.SobelEdgeProcess();
			testEdge.setSobelEdges();
			loadEdge.setFile();
			loadEdge.outputFile();
		}else if(cmd.equals("Blur")){
			Blur test = new Blur(loadBlur.pixImage, 100);
			test.blurProcess();
			test.setBlur();
			loadBlur.setFile();
			loadBlur.outputFile();
		}
	}
	
	private void loadFile() throws IOException{
		 if (this.theView.fileChooser.showOpenDialog(theView.frame) != JFileChooser.APPROVE_OPTION) {
	            return;
		 }
		 File selected = theView.fileChooser.getSelectedFile();
		 if (selected == null) {
	            return;
		 }
		 theView.statusLabel.setText("Current image: " + selected.getName());
		 System.out.println("Loading Image from " + selected.getName() + " ...");
		 loadBlur = new Loadfile(selected.getName(), "outputBlurTest.jpg");
		 loadEdge = new Loadfile(selected.getName(), "outputSobelEdgesTest.jpg");
		 theView.statusLabel.setText("Current image: "+ loadBlur.inputFileName);
		 System.out.println("Loading complete.");
		 System.out.println("Image: " + loadBlur.inputFileName);
		 System.out.println("Mission Complete");
	}
	public static void main(String[] args){
		View view = new View();
		Controller test = new Controller(view);
	}
}
