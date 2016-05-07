package runner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import defs.Ball;
import defs.Vector2d;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Initialization and UI
 * Open only to add UI
 * @author Ronen
 *
 */
public class Main {
	public static boolean running=true;
	public static Runner run;
        public static int main;
        public static int score1,score2;
        public static JLabel Label1,Label2;
        public static JFrame f;
        /**
         * starts the program on event dispatcher thread
         * @param args
         */
	public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
                            score1=0;
                            score2=0;
                            @SuppressWarnings("unused")
                            DataGetter dg=new DataGetter();
                });
	}

    /**
     * called from data getter gets the data the user entered, initializes and starts running the game loop
     * @param balls
     * @param size
     * @param boardcolor
     * @param WoodColor
     * @param StickColor
     */
    public void createAndShowGUI(Ball[] balls,Dimension size,Color boardcolor,Color WoodColor,Color StickColor) {
	    //make sure we run in the Event Dispatch Thread
	    System.out.println("Created GUI on EDT? "+
	    SwingUtilities.isEventDispatchThread());
	    //JFrame set
	    f = new JFrame("Swing Paint Demo");
	    f.setUndecorated(true);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	    f.setSize(size);
	    f.setLocationRelativeTo(null);
	    //JButton set
	    JButton exit=new JButton("");
	    exit.setToolTipText("exit the game");
	    URL url = Main.class.getResource("/close-icon.png");
	    ImageIcon ii=new ImageIcon(url);
	    exit.setIcon(ii);
	    exit.setSize(50, 50);
	    JButton min=new JButton("");
	    min.setToolTipText("This minimizes the window");
	    url = Main.class.getResource("/rsz_blue-minimized-icon-32193.png");
	    min.setIcon(new ImageIcon(url));
	    min.setSize(50,50);
	    //JBottun listener set
	    ActionListener al=(ActionEvent e) -> {
                if(e.getSource().equals(exit)){
                    running=false;
                    f.dispose();
                    System.exit(0);
                }
                if(e.getSource().equals(min)){
                    f.setState(JFrame.ICONIFIED);
                }
            };
		exit.addActionListener(al);
		min.addActionListener(al);
		//JPanel set
		MyPanel mp=new MyPanel(boardcolor,new Dimension(1920,1080));
		mp.setLayout(new GroupLayout(mp));
		mp.setDoubleBuffered(true);
		//Finishing up JFrame set
		f.add(exit);
		f.add(min);
                Label1=new JLabel("Player 1: "+score1+"    P");
                Label2=new JLabel("layer 2: "+score2);
                f.add(Label1);
                f.add(Label2);
		f.add(mp);
		f.setVisible(true);
                main=0;
		//set updater and painter separate class for comfort
		run=new Runner(WoodColor,balls,size,StickColor,mp.getMousePosition(),main);
		//start updating and painting
		Thread thread = new Thread(new RePainter(mp));
		thread.setDaemon(true);
		thread.start();
	}
}



/**
 * main panel where the game happens
 * @author admin
 *
 */
class MyPanel extends JPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Dimension size;
        /**
         * builds a new pane
         * @param boardcolor
         * @param size
         */
	public MyPanel(Color boardcolor,Dimension size){
		super();
		super.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		setBackground(boardcolor);
		this.size=size;
                addMouseListener(new MouseListener() {
                    /**
                     * mouse listener.gets when the mouse is pressed and gives the ball the right speed in that moment
                     * @param me
                     */
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        if(Main.run.setmode()){
                            System.out.println("was able to shoot");
                            Main.main=1;
                            Vector2d speed;
                            double distance=Main.run.s.getPlayball().getPos().distance(getMousePosition());
                            if(distance>Main.run.s.getLength()/2){
                                speed=new Vector2d(Main.run.s.getDir().scale(Main.run.s.getLength()/2).add(getMousePosition()), Main.run.s.getPlayball().getPos()).scale(0.08);
                            }
                            else{
                               speed=Main.run.s.getDir().scale(Main.run.s.getPlayball().getRadius()).scale(0.2);
                            }
                            Main.run.s.setSpeed(speed);
                            Main.run.setTurn(true);
                            Runner.turnnum++;
                        }
                        else{
                            System.out.println("wasn't able to shoot");
                        }

                    }
                    /**
                     * same
                     * @param me
                     */
                    @Override
                    public void mousePressed(MouseEvent me) {
                       if(Main.run.setmode()){
                            System.out.println("was able to shoot");
                            Main.main=1;
                            Vector2d speed;
                            double distance=Main.run.s.getPlayball().getPos().distance(getMousePosition());
                            if(distance>Main.run.s.getLength()/2){
                                speed=new Vector2d(Main.run.s.getDir().scale(Main.run.s.getLength()/2).add(getMousePosition()), Main.run.s.getPlayball().getPos()).scale(0.025);
                            }
                            else{
                                speed=Main.run.s.getDir().scale(Main.run.s.getPlayball().getRadius()).scale(0.025);
                            }
                            System.out.print(speed);
                            Main.run.s.setSpeed(speed);
                            Main.run.setTurn(true);
                            Runner.turnnum++;
                        }
                        else{
                            System.out.println("wasn't able to shoot");
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {

                    }

                    @Override
                    public void mouseExited(MouseEvent me) {

                    }
                });
	}
        /**
         * sets the size
         * @return
         */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(size.width, size.height);

	}
        /**
         * paints the hole game to the right scale
         * @param g
         */
	@Override
	public void paintComponent(Graphics g) {
		Image image=this.createImage(1920, 1080);
		Graphics ImGraf=image.getGraphics();
		super.paintComponent(ImGraf);
		Update();
		AdvancedPaint((Graphics2D)ImGraf);
		g.drawImage(image, 0, 0, size.width, size.height,null);
	}
	//call update from separate class for clean code
	private void Update() {
		Main.run.Update(getMousePosition(),Main.main);
	}
	//call paint from separate class for clean code
	public void AdvancedPaint(Graphics2D g2d){
		Main.run.Paint(g2d);
        }

}
/**
 * Special thread to allow an infinite update loop in a canvas
 * @author Ronen
 *
 */
class RePainter implements Runnable{
	private final JPanel jp;
	private static boolean running;
	public RePainter(JPanel panel) {
		this.jp=panel;
		running=true;
	}
	@Override
	public void run() {
		 while (running) {
             jp.repaint();
             try {
                 Thread.sleep(30);
             } catch (InterruptedException ex) {
            	 running=false;
             }
         }
		 jp.getParent().setVisible(false);

	}
        public static void setrunning(boolean running2){
            running=running2;
        }
}
