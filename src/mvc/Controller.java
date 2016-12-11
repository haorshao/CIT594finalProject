package mvc;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Controller  implements ActionListener, ChangeListener {
	View theView;
	Model theModel;
	int blurIter;
	int prevSlider;
	boolean edgeDec;
	boolean url;
	URL link;
	/**
	 * Constructor
	 * @param theView
	 * @param theModel
	 */
	public Controller(View theView, Model theModel){
		this.theView = theView;
		this.theModel = theModel;
		this.theView.addViewListener(this);
		this.theView.addChangeListener(this);
		prevSlider = 0;
		edgeDec = false;
		url = false;
	}
	/**
	 * slider listener
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	    	this.edgeDec = false;
			new BlurInController().execute();
	    }
	}
	/**
	 * button listener
	 */
	@Override
	public void actionPerformed(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("Load")){
			if (theView.getLink().equals("Image URL")) {
				try {
	                loadStorageFile();
	            } catch (IOException ioe) {
	                System.out.println("not able to load from the file");
	            }
			} else {
				try {
					url = true;
					link = new URL(theView.getLink());
					theView.statusLabel.setText("Current image: " + "Online Image");
					System.out.println("Loading Image from " + "Online Image" + " ...");
					theModel.loadLink(link, "Online Image.jpg", "Online Image.jpg", "SobelEdges-" + "Online Image.jpg");
					theView.statusLabel.setText("Current image: "+ theModel.loadBlur.inputFileName);
					System.out.println("Loading complete.");
					System.out.println("Image: " + theModel.loadBlur.inputFileName);
					System.out.println("Mission Complete");
					System.out.println("Updating original img..");
					updateImg(theModel.loadBlur.currentImg);
					System.out.println("Updating original img finished...");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			
		}else if(cmd.equals("Edge Detection")){
			edgeDec = true;
			new EdgeInController().execute();
		}else if(cmd.equals("Export")){
			new ExportInController().execute();
		}
	}
	
	class ExportInController extends SwingWorker<Void, Void>{
		@Override
		protected Void doInBackground() throws Exception{
			if(!edgeDec){
				theModel.exportBlur();
			}else{
				theModel.exportEdge();
			}
			return null;
		}
	}
	
	class EdgeInController extends SwingWorker<Void, Void>{
		@Override
		protected Void doInBackground() throws Exception{
			theModel.edgeDetection();
			System.out.println("Updating processed img..");
			updateImg(theModel.loadEdge.outputImage);
			System.out.println("Updating processed img finished..");
			return null;
		}
	}
	
	class BlurInController extends SwingWorker<Void, Void>{
		@Override
		protected Void doInBackground() throws Exception{
			blurIter = theView.blurSlider.getValue();
			prevSlider = blurIter;
			System.out.println("BlurNum: " + blurIter);
			updateImg(theModel.map.get(blurIter));
			return null;
		}
	}
	
	class LoadInController extends SwingWorker<Void, Void>{
		@Override
		protected Void doInBackground() throws Exception{
			if (theView.fileChooser.showOpenDialog(theView.frame) != JFileChooser.APPROVE_OPTION) {
				return null;
			}
			File selected = theView.fileChooser.getSelectedFile();
			if (selected == null) {
				return null;
			}
			String filename = selected.getAbsolutePath();
			theView.statusLabel.setText("Current image: " + selected.getName());
			System.out.println("Loading Image from " + selected.getName() + " ...");
			
			theModel.loadFile(filename, selected.getName(), "SobelEdges-" + selected.getName());
			theView.statusLabel.setText("Current image: "+ theModel.loadBlur.inputFileName);
			System.out.println("Loading complete.");
			System.out.println("Image: " + theModel.loadBlur.inputFileName);
			System.out.println("Mission Complete");
			System.out.println("Updating original img..");
			updateImg(theModel.loadBlur.currentImg);
			System.out.println("Updating original img finished...");
			return null;
		}
	}
	
	/**
	 * load file
	 * @throws IOException
	 */
	private void loadStorageFile() throws IOException{
		 new LoadInController().execute();
	}
	/**
	 * create icon for displaying image
	 * @param img
	 * @return
	 */
	public JLabel createIcon(BufferedImage img) {
		ImageIcon icon = new ImageIcon(img);
		icon.setImage(img.getScaledInstance(480, 360, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(icon);
		return label;
	}
	/**
	 * update the display image
	 * @param newImg
	 */
	public void updateImg(BufferedImage newImg) {
		theView.p5.removeAll();
		theView.p5.add(createIcon(newImg));
		theView.p5.validate();
	}
	
	public static void main(String[] args){
		View view = new View();
		Model model = new Model();
		Controller test = new Controller(view, model);
		model.setTheController(test);
	}
}
