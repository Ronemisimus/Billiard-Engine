package runner;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import defs.Ball;
import java.awt.Checkbox;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
/**
 * responsible too get all data from user and transfer to the game
 * @author Ronen
 */
public class DataGetter extends JFrame{
	private static final long serialVersionUID = 1L;
	private boolean done;
	private JTextField[] data;
        private Checkbox[] cb;
	private JLabel[] labels;
	private JButton getballs;
	private JButton setgame;
        private JButton default_game;
	private final Main m;
	private final JFrame colorset;
	private Ball[] balls;
	private Color c,woodcolor,stickcolor;
	private JComboBox<Dimension> sizechoise;
	public JFrame getBallsetter(){
		return colorset;
	}
	/**
         * starts the data getting process
         */
        public DataGetter(){
		super("game builder window");
		colorset=new JFrame("ball set");
		m=new Main();
		setFrame();
		setComponents();
	}
        /**
         * sets the components in the frame
         */
        @SuppressWarnings("empty-statement")
	private void setComponents() {
		data=new JTextField[5];
		labels=new JLabel[6];
		//labels set:
		labels[0]=new JLabel("Enter amount of balls:");
		labels[0].setToolTipText("<html>"+"enter a number!!"+"<br>"+"recommended lower then 100 to avoid bugs"+ "<br>"
				+"will be allow to mod every ball in this structure:"+"<br>"
				+""+"<br>"
				+"<html>");
		labels[1]=new JLabel("Enter Screen Size:");
		labels[1].setToolTipText("<html>"+"Choose the right screen size"+"<br>"
				+"from the multi-choise box"
				+"<html>");
		labels[5]=new JLabel("for more information leave mouse hanging on a label");
		labels[5].setToolTipText("Great Job :-)");
		//data set:
		data[0]=new JTextField(30);
		sizechoise=new JComboBox<>();
		sizechoise.addItem(new Dimension(1024, 768));sizechoise.addItem(new Dimension(1366, 768));sizechoise.addItem(new Dimension(1280,720));sizechoise.addItem(new Dimension(1440,810));sizechoise.addItem(new Dimension(1600,900));sizechoise.addItem(new Dimension(1600, 1200));;sizechoise.addItem(new Dimension(1680,1050));sizechoise.addItem(new Dimension(1920,1080));
		sizechoise.setPreferredSize(new Dimension(300, 30));
		getballs =new JButton("modify balls");
		setgame=new JButton("start playing already!!");
                default_game=new JButton("Default Game");
		al al=new al();
                default_game.addActionListener(al);
		getballs.addActionListener(al);
		setgame.addActionListener(al);
		//enter to frame
		this.add(labels[0]);
		this.add(data[0]);
		this.add(labels[1]);
		this.add(sizechoise);
		this.add(labels[5]);
		this.add(getballs);
		this.add(setgame);
                this.add(default_game);

	}
        /**
         * sets the frame itself
         */
	private void setFrame() {
		setDone(false);
		setSize(360,235);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		URL url = Main.class.getResource("/Curser.gif");
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(url).getImage(), new Point(1, 1), "it's a me mario!"));
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	/**
         * listnes to two main buttons: 1.modify balls 2.start playing all ready!
         */
        private class al implements ActionListener{
		private boolean getball=false;
		private JButton done;
                /**
                 * called if there is an action
                 * responds differently to every button and checks if data was inserted correctly
                 * @param e
                 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(getballs)){
				int ballcount=0;
				try{
					ballcount=Integer.parseInt(data[0].getText());
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(DataGetter.this, "enter an actual number!!", "not a number in ball amount",JOptionPane.ERROR_MESSAGE );
				}
				if(ballcount>0){
					c=JColorChooser.showDialog(DataGetter.this, "Pick board color", new Color(0,120,0));
					woodcolor=JColorChooser.showDialog(DataGetter.this, "Pick the wood color", new Color(120,120,120));
					stickcolor=JColorChooser.showDialog(DataGetter.this, "Pick the stick color", new Color(120,120,120));
					DataGetter.this.setState(Frame.ICONIFIED);
					getballs(colorset,ballcount,c);
					getball=true;
				}
				else{
					JOptionPane.showMessageDialog(DataGetter.this, "Do not enter 0!!", "not a number in ball amount",JOptionPane.ERROR_MESSAGE );
				}
			}
			else if(e.getSource().equals(setgame)&&getball){
				dispose();
				setDone(true);
				m.createAndShowGUI(balls,(Dimension)sizechoise.getSelectedItem(),c,woodcolor,stickcolor);
			}
                        else if(e.getSource().equals(default_game)){
                            int ballcount=16;
                            c=new Color(0, 255, 0);
                            woodcolor=new Color(120,120,120);
                            stickcolor=new Color(120,120,120);
                            balls=new Ball[ballcount];
                            balls[0]=new Ball("30;0;1", new Point2D.Double(200, 400), Color.WHITE, Color.WHITE, true);
                            balls[1]=new Ball("30;1;1", new Point2D.Double(200, 500), Color.YELLOW, Color.BLACK, false);
                            balls[2]=new Ball("30;2;1", new Point2D.Double(200, 600), Color.BLUE, Color.BLACK, false);
                            balls[3]=new Ball("30;3;1", new Point2D.Double(200, 700), Color.RED, Color.BLACK, false);
                            balls[4]=new Ball("30;4;1", new Point2D.Double(300, 300), Color.MAGENTA, Color.BLACK, false);
                            balls[5]=new Ball("30;5;1", new Point2D.Double(300, 400), Color.ORANGE, Color.BLACK, false);
                            balls[6]=new Ball("30;6;1", new Point2D.Double(300, 500), new Color(0, 128, 0), Color.BLACK, false);
                            balls[7]=new Ball("30;7;1", new Point2D.Double(300, 600), Color.DARK_GRAY, Color.BLACK, false);
                            balls[8]=new Ball("30;8;1", new Point2D.Double(300, 700), Color.BLACK, Color.BLACK, false);
                            balls[9]=new Ball("30;9;1", new Point2D.Double(400, 300), Color.YELLOW, Color.BLACK, false);
                            balls[10]=new Ball("30;10;1", new Point2D.Double(400, 400), Color.BLUE, Color.BLACK, false);
                            balls[11]=new Ball("30;11;1", new Point2D.Double(400, 500), Color.RED, Color.BLACK, false);
                            balls[12]=new Ball("30;12;1", new Point2D.Double(400, 600), Color.MAGENTA, Color.BLACK, false);
                            balls[13]=new Ball("30;13;1", new Point2D.Double(400, 700), Color.ORANGE, Color.BLACK, false);
                            balls[14]=new Ball("30;14;1", new Point2D.Double(500, 500), new Color(0, 128, 0), Color.BLACK, false);
                            balls[15]=new Ball("30;15;1", new Point2D.Double(500, 400), Color.DARK_GRAY, Color.BLACK, false);
                            getball=true;
                        }
			else{
				JOptionPane.showMessageDialog(DataGetter.this, "press modify balls or default game first", "Warning", JOptionPane.WARNING_MESSAGE);
			}

		}
                /**
                 * starts a new frame to get the balls data
                 * @param colorset-the frame to get the balls in
                 * @param ballcount-the amount of balls
                 * @param c -the color of the board
                 */
		private void getballs(JFrame colorset, int ballcount, Color c){
			balls=new Ball[ballcount];
			colorset.setSize(700,330+ballcount*50);
			colorset.setVisible(true);
			colorset.setLocationRelativeTo(null);
			colorset.setLayout(new FlowLayout(FlowLayout.LEFT));
			URL url = Main.class.getResource("/Curser2.png");
			colorset.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(url).getImage(), new Point(1, 1), "I Shall kill this button!!"));
			done=new JButton("finish");
			//text
			JTextField[] data=new JTextField[ballcount];
                        cb=new Checkbox[ballcount];
			JLabel[] ballslab=new JLabel[ballcount];
			for(int i=0;i<ballcount;i++){
				data[i]=new JTextField(30);
                                cb[i]=new Checkbox("is the play ball:");
				ballslab[i]=new JLabel("ball "+i+" :");
				ballslab[i].setToolTipText("<html>"+"enter ball data"+"<br>"+
				"in this form:"+"<br>"+
						"radius;ballNumber;Mass(replace with numbers)"
				+"<html>");
				colorset.add(ballslab[i]);
				colorset.add(data[i]);
                                colorset.add(cb[i]);
			}
			//panel
			Color col;
			if(c == null){
				col=new Color(0, 120, 0);
			}
			else{
				col=c;
			}
			ballpositioner jp=new ballpositioner(ballcount,col);
			/**
                         * mouse listener to allow positioning of the ball
                         **/
                        jp.addMouseListener(new MouseListener() {
				private int count=0;
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if(count<ballcount){
						jp.ballpos[count].setLocation(e.getPoint());
						count++;
						jp.repaint();
					}
				}

			});
			jp.setToolTipText("<html>"+"click a location on "+"<br>"
					+ "the panel to position"+"<br>"
					+ "a ball on the board"+"<html>");
			colorset.add(jp);
                        /**
                         * action listener waiting to finish getting the balls and start the game
                         * makes sure every data is entered correctly
                         */
			done.addActionListener((ActionEvent e) -> {
                            if(e.getSource().equals(done)){
                                int count=0,chosenball=0;
                                for(int i=0;i<ballcount;i++){
                                    Color bcolor=JColorChooser.showDialog(colorset, "enter ball "+i+" color", Color.BLACK);
                                    Color wc=JColorChooser.showDialog(colorset, "enter ball "+i+" writing color", Color.WHITE);
                                    Dimension d=(Dimension)(sizechoise.getSelectedItem());
                                    boolean b = false;
                                    if(1>count&&(cb[i].getState())){
                                        b=cb[i].getState();
                                        count++;
                                        chosenball=i;
                                    }
                                    else if(cb[i].getState()){
                                        count++;
                                    }
                                    else{
                                        b=false;
                                    }
                                    try{
                                        balls[i]=new Ball(data[i].getText(),new Point2D.Double(d.getWidth()*jp.ballpos[i].getX()/500, d.getHeight()*jp.ballpos[i].getY()/270), bcolor,wc,b);
                                    }catch(NullPointerException exception){
                                        JOptionPane.showMessageDialog(null, "ball i's data is entered wrong", "wrong input", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                if(count!=1){
                                    JOptionPane.showMessageDialog(colorset, "too many balls chosen!");
                                }
                                JOptionPane.showMessageDialog(colorset, "the chosen ball is: "+ chosenball);
                                getBallsetter().setVisible(false);
                                DataGetter.this.setState(Frame.NORMAL);
                            }
                            // TODO Auto-generated method stub
                        });
			colorset.add(done);
		}

	}
        /**
         * the panel that allows you to position the balls
         */
	private class ballpositioner extends JPanel{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private Point2D[] ballpos;
                /**
                 * build a new panel
                 * @param ballcount the amount of balls
                 * @param c -the color of the board
                 */
		public ballpositioner(int ballcount,Color c){
			super();
			super.setBackground(c);
			setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			ballpos=new Point2D.Double[ballcount];
			for(int i=0;i<ballcount;i++){
				ballpos[i]=new Point2D.Double( 0, 0);
			}
		}
                /**
                 * paint the panel
                 * @param g -the graphics
                 */
                @Override

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			advancedPaint((Graphics2D)g);
		}
                /**
                 * allows clean work with more complex graphics paints the red dots on the positioner
                 * @param g -graphics2d
                 */
		private void advancedPaint(Graphics2D g) {
			g.setColor(Color.WHITE);
			Stroke def=g.getStroke();
			g.setStroke(new BasicStroke(2.0f));
			g.drawLine(100, 0, 100, 270);
			g.setStroke(def);
			g.setColor(Color.red);
                    for (Point2D ballpo : ballpos) {
                        if (ballpo!=null) {
                            g.fillOval((int) ballpo.getX() - 5, (int) ballpo.getY() - 5, 10, 10);
                        }
                    }
		}
                /**
                 * sets the panels size
                 * @return
                 */
                @Override
		public Dimension getPreferredSize(){
			return new Dimension(500, 270);
		}
	}
}