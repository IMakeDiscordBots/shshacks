import java.util.*;
import javax.swing.event.MouseInputListener;
import game_objects.*;
import java.awt.Font;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Main implements MouseInputListener, MouseListener {
	static int money;

    JFrame frame;
    DrawPanel drawPanel;
	//0 - up, 1 - side, 2 - leftUp, 3 - rightUp, 4 - rightDown, 5 - leftDown, 6 - threeDown, 7 - threeUp, 8 - threeRight, 9 - threeLeft, 10 - fourway
	ArrayList<Image> roads = new ArrayList<Image>();
	private Image up;
    private Image side;
    private Image leftUp;
    private Image rightUp;
    private Image rightDown;
    private Image leftDown;
    private Image threeDown;
    private Image threeUp;
    private Image threeRight;
    private Image threeLeft;
    private Image fourway;

	boolean placingRoad = false;
	boolean placingResidential = false;
	int cursorXCoord = MouseInfo.getPointerInfo().getLocation().x;
	int cursorYCoord = MouseInfo.getPointerInfo().getLocation().y;
	//Divide/multiply by thirty
	GameObject[][] grid = new GameObject[1141][601];
    Map<int[], GameObject> grids = new HashMap<int[], GameObject>();
    public static void main(String[] args) {
		Timer whiteTimer = new Timer();
    	TimerTask whiteTimerTask = new ClockTask();
    	whiteTimer.scheduleAtFixedRate(whiteTimerTask, 1000, 1000);
        System.out.println("test");
		
        new Main().go();
    }
    private void go() {
		JFrame frame = new JFrame("World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1140, 700));
		frame.setBackground(Color.BLACK);
		frame.pack();

        drawPanel = new DrawPanel();
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.add(drawPanel);

        frame.setResizable(false);
        frame.setLocationByPlatform(true);

        
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);

        frame.setVisible(true);
    }
	public class DrawPanel extends JPanel {
		public void paint(Graphics g) {
			
			Color grass = new Color(52, 140, 49);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			super.paintComponent(g);

			g.setColor(grass);
			g.fillRect(0, 0, 1140, 600);
			g.setColor(Color.BLACK);
			g.fillRect(0, 600, 1140, 10);

			g2d.setFont(new Font("Monospace", Font.BOLD, 25));
			//g2d.setColor(new Color(124,252,0)); color is too bright?
			g2d.setColor(new Color(0, 0, 0));
			g2d.drawString("$" + money + "", 10, 645);

			g2d.setColor(Color.BLUE);
			g2d.fillRect(120, 610, 70, 90);

			g.setColor(Color.BLACK);
			ImageIcon icon = new ImageIcon("game_objects/images/twowayH.png");
			if(placingRoad) {
				g.drawImage(icon.getImage(), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			for(GameObject obj : grids.values()) {
				g.drawRect(obj.getX(), obj.getY(), 30, 30);
			}
			//Image img2 = icon.getImage();
			//g2d.drawImage(img2, 10, 10, null);

			repaint();
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		cursorXCoord = e.getX();
		cursorYCoord = e.getY();
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		System.out.println("Mouse Clicked");
		if(SwingUtilities.isLeftMouseButton(e) && e.getY() >= 600) {
            System.out.println("Clicked on Buy Menu");
			if (e.getX()>=120 && e.getX()<=190 && e.getY()>=610 && e.getY()<=700){
				if(!placingRoad) {
				placingRoad = true;
			}
			}
			
        }
		else if(SwingUtilities.isLeftMouseButton(e) && placingRoad) {
			int[] coords = {e.getX() - (e.getX()%30), e.getY() - (e.getY()%30) - 30};
			boolean already = false;
			if(money < 100) {
				already = true;
			}
			if(!already) {
				for(int[] c : grids.keySet()) {
					if(c[0] == coords[0] && c[1] == coords[1]) {
						already = true;
						break;
					}
				}
			}
			if(!already) {
				System.out.println("Placed block");
				placingRoad = false;
				money-=100;
				grids.put(coords, new Car(coords[0], coords[1]));
			}
			else {
				placingRoad = false;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void pathfind(GameObject a, GameObject b){ //uhhhhhhh help
		int w = a.getX();
		int x = a.getY();
		int y = b.getX();
		int z = b.getY();

		int chX = Math.abs(w-y)%30;
		int chY = Math.abs(x-z)%30;

		//shorte
	}

	public void initRoads() {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/twowayV.png"));
	    up = icon.getImage();
		roads.add(up);
        icon = new ImageIcon("twowayH.png");
        side = icon.getImage();
		roads.add(side);

        //Corners
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/roadLeftUp.png"));
        leftUp = icon.getImage();
		roads.add(leftUp);
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/roadRightUp.png"));
        rightUp = icon.getImage();
		roads.add(rightUp);
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/roadRightDown.png"));
        rightDown = icon.getImage();
		roads.add(rightDown);
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/roadLeftDown.png"));
        leftDown = icon.getImage();
		roads.add(leftDown);

        //3-way intersections
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/threewayDown.png"));
        threeDown = icon.getImage();
		roads.add(threeDown);
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/threewayUp.png"));
        threeUp = icon.getImage();
		roads.add(threeUp);
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/threewayRight.png"));
        threeRight = icon.getImage();
		roads.add(threeRight);
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/threewayLeft.png"));
        threeLeft = icon.getImage();
		roads.add(threeLeft);

        //4-way intersections
        icon = new ImageIcon(this.getClass().getResource("/game_objects/images/fourway.png"));
        fourway = icon.getImage();
		roads.add(fourway);



	// Transparent 16 x 16 pixel cursor image.

// Create a new blank cursor.
Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

// Set the blank cursor to the JFrame.
frame.getContentPane().setCursor(blankCursor);
	}
}