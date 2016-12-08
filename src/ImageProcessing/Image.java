package ImageProcessing;
/**
 * This Image class represents an image with specific width and height
 * The image is comprised of width * height pixels of which each has red, green, blue
 * color intensities storing in three separate 2D arrays.
 * @author Haoran Shao
 *
 */
public class Image {
	short[][] red;
	short[][] blue;
	short[][] green;
	int width;
	int height;
	public Image(int width, int height){
		this.width = width;
		this.height = height;
		red = new short[height][width];
		blue = new short[height][width];
		green = new short[height][width];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
