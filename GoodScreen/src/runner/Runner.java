package runner;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Dimension;

import defs.Ball;
import defs.Board;
import defs.Stick;
import defs.Vector2d;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 * runs the game
 * @author Ronen
 */
public class Runner {
	private Ball[] balls;
        private List<Collision> colls;
        private Dimension size;
	private Board board;
        public Stick s;
        private Ball playball;
        private int mode;
        private boolean turn;
        public static int turnnum;
        /**
         * Initializes all the objects according to user input
         * @param woodColor
         * @param balls
         * @param size
         * @param StickColor
         * @param stick
         * @param mode -0-no ball moving only the stick moves 1- balls move stick isnt on the board
         */
	public Runner(Color woodColor, Ball[] balls, Dimension size,Color StickColor,Point2D stick,int mode){
                turnnum=0;
		board=new Board(size,woodColor);
		Ball.setB(board);
                this.balls=balls;
                playball=getplayball();
                System.out.println(playball+"  "+ stick+ "  "+ StickColor);
                s=new Stick(200, null, 3, playball, new Point2D.Double(100, 100), StickColor);
                this.mode=mode;
                turn=false;
                this.size=size;
	}
        /**
         * updates the game
         * @param mouseloc-current mouse position
         * @param mode -current mode
         */
	public void Update(Point2D mouseloc,int mode){
                colls=new ArrayList<>();
                if(setmode()){
                    this.mode=0;
                }
                else{
                    this.mode=mode;
                }
                if(notdeadballs()==1){
                    RePainter.setrunning(false);
                    if(Main.score1>Main.score2){
                        JOptionPane.showMessageDialog(Main.f, "the winner is: Player"+ 1);
                    }
                    else if(Main.score1<Main.score2){
                        JOptionPane.showMessageDialog(Main.f, "the winner is: Player"+ 2);
                    }
                    else{
                        JOptionPane.showMessageDialog(Main.f, "it's a draw!!");
                    }
                    Main.f.dispose();
                    System.exit(0);
                }
                //s.getPlayball().setSpeed();
		for(Ball x:balls){
			if(x.getPos()==null){
			} else {
                            x.UpdatePos();
                            x.collidewall(size,balls,s);
                            if(x.isPlayball()){
                                System.out.println(turn);
                                if(turn){
                                boolean coll=false;
                                for(Ball y:balls){
                                    if(y.isColliding(x)&&!y.equals(x)){
                                        coll=true;
                                    }
                                }
                                if(!coll){
                                    x.setSpeed(s.getSpeed().normalize().scale(s.getSpeed().length()*s.getMass()/s.getPlayball().getMass()));
                                }
                                }
                            }
                        }
                }
                for(int i=0;i<balls.length;i++){
			for(int j=i+1;j<balls.length;j++){
				if(balls[i].isColliding(balls[j])){
					colls.add(new Collision(balls[i], balls[j]));
				}
			}
                }
                Collision.i=0;
                colls=Collision.orgenise(colls);
                System.out.print(colls);
                for(Collision x:colls){
                    x.handleCollision();
                }
                if(this.mode==0){
                    s.setLoc(mouseloc);

                try{
                    s.setDir(new Vector2d(mouseloc, playball.getPos()).normalize());
                }catch(NullPointerException np){
                    if(mouseloc==null){
                        System.out.print("mouseloc is null");
                    }
                    else{
                         System.out.print("playball is null");
                    }
                }
            }
            for(Ball x:balls){
                boolean a=true;
                for(Ball y:balls){
                    if(!y.equals(x)){
                        if(y.isColliding(x)){
                            a=false;
                        }
                    }
                }
                if(a){
                    x.updateSpeed();
                }
            }
            turn=false;
        }
    /**
     * paint the hole game
     * @param g2d -the graphics
     */
    public void Paint(Graphics2D g2d){
        board.paint(g2d);
		for(Ball x:balls){
			x.paint(g2d);
		}
        if(mode==0){
            System.out.print(mode);
            s.paint(g2d);
        }
    }

        private Ball getplayball() {
        for(Ball x:balls){
            if(x.isPlayball()){
                return x;
            }
        }
        return balls[0];
    }
    /**
     *
     * @return true-mode should be 0 false-mode should be 1
     */
    public boolean setmode() {
        for(Ball x:balls){
            if(x.getSpeed().dY!=0||x.getSpeed().dX!=0){
                System.out.print(x.getSpeed());
                return false;
            }
        }
        return true;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    /**
     *
     * @return the amount of not dead balls in the game
     */
    private int notdeadballs() {
        int c=0;
        for(Ball x:balls){
            if(!x.isDead()){
                c++;
            }
        }
        return c;
    }


}
class Collision{
    private Ball a;
    private Ball b;
    private double length;
    public static int i;
    public Collision(Ball a,Ball b){
        this.a=a;
        this.b=b;
        length=new Vector2d(a.getPos(), b.getPos()).length();
    }
    public void handleCollision(){
        a.resolveCollision(b);
    }
    public static List<Collision> orgenise(List<Collision> a){
        i++;
        if(a.isEmpty()||a.size()==1||i>100){
            return a;
        }
        else if(a.size()==2){
            if(a.get(0).length>a.get(1).length){
                Collision temp=a.get(0);
                a.set(0, a.get(1));
                a.set(1, temp);
            }
            return a;
        }
        else{
            List<Collision> left=new ArrayList<>();
            List<Collision> right=new ArrayList<>();
            Collision pivot=a.get((int)(Math.random()*a.size()));
            for(Collision x:a){
                if(x.length>pivot.length){
                    right.add(x);
                }
                else{
                    left.add(x);
                }
            }
            a.clear();
            a.addAll(orgenise(left));
            a.addAll(orgenise(right));
            return a;
        }
    }
    public String toString(){
        return "a:"+ a.getBallNum()+"b: "+ b.getBallNum()+ "length: "+ length;
    }
}