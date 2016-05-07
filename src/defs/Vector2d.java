package defs;
import java.awt.geom.Point2D;
import java.lang.Math;
/**
 *
 * @author Mark Austin
 * Modified by: Ronen
 */
public class Vector2d {

   public double dX;
   public double dY;

   // Constructor methods ....
   /**
    * Constructs an empty vector
    */
   public Vector2d() {
      dX = dY = 0.0;
   }
   /**
    * Construct a vector from two integers
    * @param dX-the x delta
    * @param dY -the y delta
    */
   public Vector2d( double dX, double dY ) {
      this.dX = dX;
      this.dY = dY;
   }
   /**
    * Constructs a vector from two points
    * @param a-the Point the vector starts from
    * @param b -the point it ends
    */
   public Vector2d(Point2D a,Point2D b){
	   this.dX = b.getX()-a.getX();
	   this.dY = b.getY()-a.getY();
   }


   // Convert vector to a string ...

   public String toString() {
      return "Vector2D(" + dX + ", " + dY + ")";
   }

   // Compute magnitude of vector ....

   public double length() {
      return Math.sqrt ( dX*dX + dY*dY );
   }

   // Sum of two vectors ....

   public Vector2d add( Vector2d v1 ) {
       Vector2d v2 = new Vector2d( this.dX + v1.dX, this.dY + v1.dY );
       return v2;
   }

   // Subtract vector v1 from v .....

   public Vector2d sub( Vector2d v1 ) {
       Vector2d v2 = new Vector2d( this.dX - v1.dX, this.dY - v1.dY );
       return v2;
   }

   // Scale vector by a constant ...

   public Vector2d scale( double scaleFactor ) {
       Vector2d v2 = new Vector2d( this.dX*scaleFactor, this.dY*scaleFactor );
       return v2;
   }

   // Normalize a vectors length....

   public Vector2d normalize() {
      Vector2d v2 = new Vector2d();

      double length = Math.sqrt( this.dX*this.dX + this.dY*this.dY );
      if (length != 0) {
        v2.dX = this.dX/length;
        v2.dY = this.dY/length;
      }

      return v2;
   }
   //add vector to point
   public Point2D add(Point2D a){
	   a.setLocation(a.getX()+this.dX, a.getY()+this.dY);
	   return a;
   }
   //Subtract vector from point
   public Point2D subtract(Point2D a){
	   a.setLocation(a.getX()-this.dX, a.getY()-this.dY);
	   return a;
   }
   //Subtract vector from vector
   public Vector2d subtract(Vector2d a){
	   this.dX-=a.dX;
	   this.dY-=a.dY;
	   return a;
   }

   // Dot product of two vectors .....

   public double dotProduct ( Vector2d v1 ) {
        return this.dX*v1.dX + this.dY*v1.dY;
   }

   // Exercise methods in Vector2D class
}