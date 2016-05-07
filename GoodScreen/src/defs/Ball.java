package defs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import runner.Main;
/**
 * represents a ball in the game; paints and updates its self plus handles collision
 * @author Ronen
 */
public class Ball {
        private boolean dead;
	private Point2D pos;
	private Vector2d speed;
	private double radius;
	private double mass;
	private Ellipse2D outcircle;
	private Ellipse2D incirlce;
	private Color in;
	private Color text;
	private int ballNum;
	private static Board b;
        private boolean playball;
        /**
         * regular constructor only used in debugging
         * @param pos- the position of the ball
         * @param speed-the speed vector of the ball
         * @param radius-the radius in pixels
         * @param c-the color of the face of the ball
         * @param write-the writing color of the ball
         * @param BallNum-the number of the ball
         * @param mass-the mass of the ball in kg
         * @param play -if the ball is hit by the stick
         */
	public Ball(Point2D pos, Vector2d speed, double radius,Color c,Color write,int BallNum,double mass,boolean play) {
		super();
		this.pos = pos;
		this.speed = speed;
		this.radius = radius;
		setIn(c);
		setBallNum(BallNum);
		setText(write);
		setMass(mass);
                setPlayball(play);
	}
        /**
         * Constructor used with user input
         * @param text-radius;BallNum;mass
         * @param point2d-the position of the ball
         * @param bcolor-the ball color
         * @param writeColor-the writing color
         * @param play -if the ball is hit by the stick
         */
	public Ball(String text, Point2D point2d,Color bcolor,Color writeColor,boolean play) throws NullPointerException{
		super();
		if(text.contains(";")){
			String[] data=text.split(";");
			if(data.length==3){
			this.pos = point2d;
			this.speed = new Vector2d(0,0);
			this.radius = Double.parseDouble(data[0]);
			setIn(bcolor);
			setBallNum(Integer.parseInt(data[1]));
			setText(writeColor);
			setMass(Double.parseDouble(data[2]));
                        setPlayball(play);
                        setDead(false);
			}
		}
	}
	/**
         * paints the ball to the given graphics
         * @param g2d
         * @return
         */
	public boolean paint(Graphics2D g2d){
            if(!dead){
		setOutcircle(new Ellipse2D.Double(getPos().getX()-radius, getPos().getY()-radius, 2*getRadius(), 2*getRadius()));
		setIncirlce(new Ellipse2D.Double(getPos().getX()-radius/2, getPos().getY()-radius/2, radius, radius));
		if(getBallNum()<9){
			g2d.setColor(getIn());
			g2d.fill(outcircle);
			g2d.setColor(Color.WHITE);
			g2d.fill(incirlce);
			g2d.setColor(text);
			g2d.drawString(""+getBallNum(),(int )(pos.getX()-radius/10),( int)(pos.getY()+radius/10));
		}
		else{
			g2d.setColor(Color.WHITE);
			g2d.fill(outcircle);
			g2d.setColor(getIn());
			g2d.fill(incirlce);
			g2d.setColor(text);
			g2d.drawString(""+getBallNum(),(int )(pos.getX()-radius/10),( int)(pos.getY()+radius/10));
		}
		return true;
            }
            return false;
	}
	/**
         * Performs the collision with another ball
         * @param b -the other ball
         */
	public void resolveCollision(Ball b){
            if(!dead&&!b.isDead()){
                if(mass==0&&b.getMass()==0)return;
                Vector2d v_nfull=new Vector2d(pos, b.getPos());
                
                float im1 =(float)( 1 / getMass()); 
                float im2 = (float)(1 / b.getMass());
                
                Vector2d mtd=v_nfull.scale(((getRadius()+b.getRadius())-v_nfull.length())/v_nfull.length());

                setPos(mtd.scale(im1/(im1+im2)).add(pos));
                b.setPos(mtd.scale(im2/(im1+im2)).add(b.getPos()));

                Vector2d v=speed.subtract(b.getSpeed());
                float vn=(float)(v.dotProduct(mtd));
                
                if (vn > 0.0f) return;
                
                Vector2d v_n=v_nfull.normalize();
                Vector2d v_t=new Vector2d(-v_n.dY, v_n.dX);
                
                double v1n = v_n.dotProduct(speed);
                double v1t = v_t.dotProduct(speed);
                double v2n = v_n.dotProduct(b.getSpeed());
                double v2t = v_t.dotProduct(b.getSpeed());
                
                double v1tPrime=v1t;
                double v2tPrime = v2t;
                
                double v1nPrime = (v1n * (mass - b.getMass()) + 2. * b.getMass() * v2n) / (mass + b.getMass());
                double v2nPrime = (v2n * (b.getMass() - mass) + 2. * mass * v1n) / (mass + b.getMass());
                
                Vector2d v_v1nPrime = v_n.scale(v1nPrime);
                Vector2d v_v1tPrime = v_t.scale(v1tPrime);
                Vector2d v_v2nPrime = v_n.scale(v2nPrime);
                Vector2d v_v2tPrime = v_t.scale(v2tPrime);
                
                Vector2d finalV1=v_v1nPrime.add(v_v1tPrime);
                Vector2d finalV2=v_v2nPrime.add(v_v2tPrime);
                setSpeed(finalV1);
                b.setSpeed(finalV2);
            }
	}
	/**
         * updates the position according to the speed
         */
	public void UpdatePos(){
            if(!dead){
		setPos(new Point2D.Double(pos.getX()+speed.dX, pos.getY()+speed.dY));
            }
	}
	/**
         * checks if the given ball and this one collide
         * @param b-the other ball
         * @return
         */
	public boolean isColliding(Ball b){
		if(new Vector2d(this.pos, b.getPos()).length()<=this.radius+b.getRadius()){
			return true;
		}
		return false;
	}
	//Setters and getters
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public Ellipse2D getOutcircle() {
		return outcircle;
	}
	public void setOutcircle(Ellipse2D outcircle) {
		this.outcircle = outcircle;
	}
	public Ellipse2D getIncirlce() {
		return incirlce;
	}
	public void setIncirlce(Ellipse2D incirlce) {
		this.incirlce = incirlce;
	}
	public Color getIn() {
		return in;
	}
	public void setIn(Color in) {
		this.in = in;
	}
	public int getBallNum() {
		return ballNum;
	}
	public void setBallNum(int ballNum) {
		this.ballNum = ballNum;
	}
	public Color getText() {
		return text;
	}

        public boolean isPlayball() {
            return playball;
        }

        public void setPlayball(boolean playball) {
            this.playball = playball;
        }

	public void setText(Color text) {
		this.text = text;
	}
	public Point2D getPos() {
		return pos;
	}
	public void setPos(Point2D pos) {
		this.pos = pos;
	}
	public Vector2d getSpeed() {
		return speed;
	}
	public void setSpeed(Vector2d speed) {
		this.speed = speed;
	}
	/**
         * handeles this ball collisions with the wall(also checks if colliding)
         * in addition if should collide but isn't collide it pots the ball
         * @param size-the size of the board
         * @param balls-the array of balls
         * @param s -the stick of the game
         */
        public void collidewall(Dimension size,Ball[] balls,Stick s){
            if(!dead){
           if(getPos().getY()+getRadius()>size.height-size.height*160/1080){
                boolean touch=false;
                double x=getPos().getX();
                if(x-radius/2>size.width*150/1920&&x+radius/2<size.width*840/1920){
                   touch=true;
                }
                if(x-radius/2>size.width*1080/1920&&x+radius/2<size.width*1770/1920){
                    touch=true;
                }
                if(touch){
                setPos(new Point2D.Double(getPos().getX(),size.height-size.height*160/1080-getRadius()));
                setSpeed(new Vector2d(speed.dX, -speed.dY));
                }
                else{
                    pot(s,balls);
                }
           }
           else if(getPos().getY()-getRadius()<size.height*80/1080){
               boolean touch=false;
                double x=getPos().getX();
                if(x-radius/2>size.width*150/1920&&x+radius/2<size.width*840/1920){
                   touch=true;
                }
                if(x-radius/2>size.width*1080/1920&&x+radius/2<size.width*1770/1920){
                    touch=true;
                }
                if(touch){
               setPos(new Point2D.Double(getPos().getX(),size.height*80/1080+radius));
               setSpeed(new Vector2d(speed.dX, -speed.dY));
                } else{
                    pot(s,balls);
                }
           }
           else{
               //do nothing
           }
           if(getPos().getX()+getRadius()>size.width-size.width*80/1920){
                boolean touch=false;
                double y=getPos().getY();
                if(y-radius/2>size.height*165/1080&&y+radius/2<size.getHeight()*835/1080){
                    touch=true;
                }
                if(touch){
                    setPos(new Point2D.Double(size.width-size.width*80/1920-radius,getPos().getY()));
                    setSpeed(new Vector2d(-speed.dX, speed.dY));
                }
                else{
                    pot(s,balls);
                }
           }
           else if(getPos().getX()-getRadius()<size.width*80/1920){
               boolean touch=false;
                double y=getPos().getY();
                if(y-radius/2>size.height*165/1080&&y+radius/2<size.getHeight()*835/1080){
                    touch=true;
                }
                if(touch){
                    setPos(new Point2D.Double(size.width*80/1920+radius+1,getPos().getY()));
                    setSpeed(new Vector2d(-speed.dX, speed.dY));
                }
                else{
                    pot(s,balls);
                }
           }
           else{
              //do nothing
           }
            }
        }
	public static Board getB() {
		return b;
	}
	public static void setB(Board b) {
		Ball.b = b;
	}
        public void updateSpeed(){
            if(speed.length()>0.01){
            speed=speed.normalize().scale(speed.length()-0.001*10);
            }
            else{
                speed=speed.scale(0);
            }
        }
        /**
         * handeles potting the ball
         * @param s-the stick
         * @param balls -the array of balls
         */
        public void pot(Stick s,Ball[] balls){
            if(s.getPlayball().equals(this)){
                setPos(set(balls, new Point2D.Double(200, 450)));
                setSpeed(new Vector2d(0, 0));
            }
            else{
                setSpeed(new Vector2d(0, 0));
                setDead(true);
                if(runner.Runner.turnnum%2==1){
                    Main.score1++;
                    Main.Label1.setText("Player 1: "+ Main.score1+"    P");
                }
                else{
                    Main.score2++;
                    Main.Label2.setText("layer 2: "+ Main.score2);
                }
            }
        }
        /**
         * not ready yet- should make sure no balls are put on each other
         * @param balls-the array of balls
         * @param pos-the checked location
         * @return -true if no ball is there, false if otherwise
         */
        public Point2D set(Ball[] balls,Point2D pos){
            boolean clear=true;
            for(Ball x:balls){
                if(pos.distance(x.getPos())<x.getRadius()){
                    clear=false;
                }
            }
            if(clear){
                return pos;
            }
            else{
                return set(balls,new Point2D.Double(pos.getX(),pos.getY()+balls[0].getRadius()));
            }
        }

        public void setDead(boolean dead) {
            this.dead = dead;
        }

        public boolean isDead() {
            return dead;
        }


}
