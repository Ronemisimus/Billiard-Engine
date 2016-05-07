package defs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
/**
 * generates a board
 * @author Ronen
 */
public class Board {
	private Dimension size;
	private Trapezoid[] bounds;
	private Color BouColor;
	private Rectangle2D.Double[] BHoles;
	private Color BColor;
	private Ellipse2D.Double[] holes;
	private Color hColor;
	public Trapezoid[] getBounds() {
		return bounds;
	}
	public void setBounds(Trapezoid[] bounds) {
		this.bounds = bounds;
	}
	public Color getBouColor() {
		return BouColor;
	}
	public void setBouColor(Color bouColor) {
		BouColor = bouColor;
	}
	public Rectangle2D.Double[] getBHoles() {
		return BHoles;
	}
	public void setBHoles(Rectangle2D.Double[] bHoles) {
		BHoles = bHoles;
	}
	public Color getBColor() {
		return BColor;
	}
	public void setBColor(Color bColor) {
		BColor = bColor;
	}
	public Ellipse2D.Double[] getHoles() {
		return holes;
	}
	public void setHoles(Ellipse2D.Double[] holes) {
		this.holes = holes;
	}
	public Color gethColor() {
		return hColor;
	}
	public void sethColor(Color hColor) {
		this.hColor = hColor;
	}
	/**
         * Constructs a billiard board like it should look
         * @param size-the size of the screen
         * @param woodColor -the color of the bounds
         */
        public Board(Dimension size, Color woodColor){
		bounds=new Trapezoid[6];
		BHoles=new Rectangle2D.Double[6];
		holes=new Ellipse2D.Double[6];
		BouColor=woodColor;
		BColor=Color.RED;
		hColor=Color.BLACK;
		this.size=size;
		for(int i=0;i<3;i++){
			BHoles[i]=new Rectangle2D.Double(size.getWidth()*930*i/1920, 0, size.getWidth()*60/1920, size.getHeight()*60/1080);
			BHoles[i+3]=new Rectangle2D.Double(size.getWidth()*930*i/1920,size.getHeight()*940/1080, size.getWidth()*60/1920, size.getHeight()*60/1080);
			holes[i]=new Ellipse2D.Double(size.getWidth()*930*i/1920, 0, size.getWidth()*60/1920, size.getHeight()*60/1080);
			holes[i+3]=new Ellipse2D.Double(size.getWidth()*930*i/1920,size.getHeight()*940/1080, size.getWidth()*60/1920, size.getHeight()*60/1080);
		}
		double[] Blength0={size.getHeight()*880/1080,size.getHeight()*670/1080};
		Point2D.Double Hpoints0=new Point2D.Double(0, size.getHeight()*500/1080);
		double[] Blength1={size.getWidth()*690/1920,size.getWidth()*870/1920};
		Point2D.Double Hpoints1=new Point2D.Double(size.getWidth()*495/1920, size.getHeight()*920/1080);
		double[] Blength2={size.getWidth()*690/1920,size.getWidth()*870/1920};
		Point2D.Double Hpoints2=new Point2D.Double(size.getWidth()*1425/1920, size.getHeight()*920/1080);
		double[] Blength3={size.getWidth()*870/1920,size.getWidth()*690/1920};
		Point2D.Double Hpoints3=new Point2D.Double(size.getWidth()*495/1920, size.getHeight()*0/1080);
		double[] Blength4={size.getWidth()*870/1920,size.getWidth()*690/1920};
		Point2D.Double Hpoints4=new Point2D.Double(size.getWidth()*1425/1920, size.getHeight()*0/1080);
		double[] Blength5={size.getHeight()*670/1080,size.getHeight()*880/1080};
		Point2D.Double Hpoints5=new Point2D.Double(size.getWidth()*1840/1920, size.getHeight()*500/1080);
		bounds[0]=new Trapezoid(Blength0,Hpoints0,1,size.getWidth()*80/1920);
		bounds[1]=new Trapezoid(Blength1,Hpoints1,0,size.getHeight()*80/1080);
		bounds[2]=new Trapezoid(Blength2,Hpoints2,0,size.getHeight()*80/1080);
		bounds[3]=new Trapezoid(Blength3,Hpoints3,0,size.getHeight()*80/1080);
		bounds[4]=new Trapezoid(Blength4,Hpoints4,0,size.getHeight()*80/1080);
		bounds[5]=new Trapezoid(Blength5,Hpoints5,1,size.getWidth()*80/1920);
	}
        /**
         * a general constructor-never used
         * @param bounds-the array of trapezoid bounds
         * @param bouColor-the color of the trapezoid
         * @param bHoles-the array of Rectangles around the holes
         * @param bColor-the color of the board itself
         * @param holes-the array of circles representing the holes
         * @param hColor -the color of the holes
         */
	public Board(Trapezoid[] bounds, Color bouColor, Double[] bHoles, Color bColor, java.awt.geom.Ellipse2D.Double[] holes,
			Color hColor) {
		super();
		this.bounds = bounds;
		BouColor = bouColor;
		BHoles = bHoles;
		BColor = bColor;
		this.holes = holes;
		this.hColor = hColor;
	}
	/**
         * paints the board to the given graphics
         * @param g2d -the graphics
         */
        public void paint(Graphics2D g2d){
		g2d.setColor(BColor);
		for(Rectangle2D x: BHoles){
			g2d.fill(x);
		}
		g2d.setColor(hColor);
		for(Ellipse2D x:holes){
			g2d.fill(x);
		}
		g2d.setColor(BouColor);
		for(Trapezoid x:bounds){
			if(x!=null){
			g2d.fill(x);
			}
		}
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}
}
