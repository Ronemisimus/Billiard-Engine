package defs;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
/**
 * represents a trapezoid either horizontal or normal
 * @author Ronen
 */

public class Trapezoid implements Shape {
	private double[] BLength;//2 sizes
	private Point2D Hpoints;//top point
	private double h;
	private int m;
        /**
         * creates a new trapezoid
         * @param bLength-an array with two values:one is length of short part the other is the length of the long part
         * @param hpoints-the point from witch the trapezoid is built
         * @param m-the mode : 1- normal 0-horizontal
         * @param h -the height of the trapezoid
         */
	public Trapezoid(double[] bLength, Point2D hpoints,int m,double h) {
		super();
		BLength = bLength;
		Hpoints = hpoints;
		this.m=m;
		this.h=h;
	}

	public double[] getBLength() {
		return BLength;
	}

	public void setBLength(double[] bLength) {
		BLength = bLength;
	}

	public Point2D getHpoints() {
		return Hpoints;
	}

	public void setHpoints(Point2D hpoints) {
		Hpoints = hpoints;
	}

	@Override
	public boolean contains(Point2D arg0) {

		return false;
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}
        /**
         * this is never called directly
         * @param arg0-the transform of the graphics
         * @return- an object describing how to draw the shape
         */
	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		GeneralPath x=new GeneralPath();
		x.moveTo(Hpoints.getX(), Hpoints.getY());
		Point2D next=null;
		if(m==0){
			next =new Point2D.Double(Hpoints.getX()-BLength[0]/2 , Hpoints.getY());
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()-BLength[1]/2 , Hpoints.getY()+h);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+BLength[1]/2 , Hpoints.getY()+h);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+BLength[0]/2 , Hpoints.getY());
			x.lineTo(next.getX(), next.getY());
		}
		else{
			next =new Point2D.Double(Hpoints.getX() , Hpoints.getY()-BLength[0]/2);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+h , Hpoints.getY()-BLength[1]/2);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+h , Hpoints.getY()+BLength[1]/2);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX() , Hpoints.getY()+BLength[0]/2);
			x.lineTo(next.getX(), next.getY());
		}
		x.closePath();
		return x.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		GeneralPath x=new GeneralPath();
		x.moveTo(Hpoints.getX(), Hpoints.getY());
		Point2D next=null;
		if(m==0){
			next =new Point2D.Double(Hpoints.getX()-BLength[0]/2 , Hpoints.getY());
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()-BLength[1]/2 , Hpoints.getY()+h);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+BLength[1]/2 , Hpoints.getY()+h);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+BLength[0]/2 , Hpoints.getY());
			x.lineTo(next.getX(), next.getY());
		}
		else{
			next =new Point2D.Double(Hpoints.getX() , Hpoints.getY()-BLength[0]/2);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+h , Hpoints.getY()-BLength[1]/2);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX()+h , Hpoints.getY()+BLength[1]/2);
			x.lineTo(next.getX(), next.getY());
			next =new Point2D.Double(Hpoints.getX() , Hpoints.getY()+BLength[0]/2);
			x.lineTo(next.getX(), next.getY());
		}
		x.closePath();
		return x.getPathIterator(arg0);
	}
        /**
         * never used. was canceled but never deleted
         * @param arg0
         * @return
         */
	@Override
	public boolean intersects(Rectangle2D arg0) {
		switch (m){
			case 0:{
				if(arg0.getY()>Hpoints.getY()){

				}
				else{

				}
				break;
			}
			default:{
				if(arg0.getX()>Hpoints.getX()){

				}
				else{

				}
				break;
			}
		}
                return false;
	}
	/**
         *
         * @return-the shorter length in Blength
         */
        public double minBLength(){
		if(BLength[0]>BLength[1]){
			return BLength[1];
		}
		else{
			return BLength[0];
		}
	}
	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}
        /**
         * not used. never deleted
         * @param a
         * @return
         */
	public boolean intersects(Ball a){
            Rectangle2D rect;
            if(m==0){
                Point2D base=new Point2D.Double(Hpoints.getX()-getminBlength()/2,Hpoints.getY());
                rect=new Rectangle2D.Double(base.getX(), base.getY(), getminBlength(), h);
            }
            else{
                Point2D base=new Point2D.Double(Hpoints.getX()-h,Hpoints.getY()-getminBlength()/2);
                rect=new Rectangle2D.Double(base.getX(), base.getY(), h, getminBlength());
            }
            return a.getOutcircle().intersects(rect);

	}
        /**
         * Accidently the same action twice :)
         * @return
         */
        public double getminBlength(){
            if(BLength[0]>BLength[1]){
                return BLength[1];
            }
            else{
                return BLength[0];
            }
        }
	/**
	 * M is the axis along with the trapezoid will be placed: 0 is the x axis 1 is the y
	 * @return
	 */
	public double getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}
}
