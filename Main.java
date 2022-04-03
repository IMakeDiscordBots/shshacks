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

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;


public class Main implements MouseInputListener, MouseListener {
	static int money = 50000;
	static int pollution = 0;
	static int population = 100;
	static boolean end = false;

	static int roadRotation = 0;
	static int lowResIndex = 0;
	static int highResIndex = 0;
	static int lowComIndex = 0;
	static int highComIndex = 0;

	JFrame frame;
	DrawPanel drawPanel;

	// 0 - up, 1 - side, 2 - leftUp, 3 - rightUp, 4 - rightDown, 5 - leftDown, 6 -
	// threeDown, 7 - threeUp, 8 - threeRight, 9 - threeLeft, 10 - fourway
	static ArrayList<Image> roads = new ArrayList<Image>();
	
	//residential buildings from here down
	static ArrayList<Image> lowRes = new ArrayList<Image>();
	static ArrayList<Image> highRes = new ArrayList<Image>();
	static ArrayList<Image> lowCom = new ArrayList<Image>();
	static ArrayList<Image> highCom = new ArrayList<Image>();

	boolean placing = false;
	boolean placingRoad = false;
	boolean placingLowRes = false;
	boolean placingHighRes = false;
	boolean placingLowCom = false;
	boolean placingHighCom = false;
	int cursorXCoord = MouseInfo.getPointerInfo().getLocation().x;
	int cursorYCoord = MouseInfo.getPointerInfo().getLocation().y;
	// Divide/multiply by thirty
	GameObject[][] grid = new GameObject[40][22];
	Map<int[], GameObject> grids = new HashMap<int[], GameObject>();

	public static void main(String[] args) {
		Timer whiteTimer = new Timer();
		TimerTask whiteTimerTask = new ClockTask();
		whiteTimer.scheduleAtFixedRate(whiteTimerTask, 1000, 3000);
		System.out.println("test");
		initRoads();
		initResidentials();
		initCommercials();

		new Main().go();
	}

	private void go() {
		JFrame frame = new JFrame("World");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1140, 700));
		frame.setBackground(Color.BLACK);
		frame.pack();

		frame.addKeyListener(new Keychecker());

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
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			super.paintComponent(g);

			g.setColor(grass);
			g.fillRect(0, 0, 1140, 600);
			g.setColor(Color.BLACK);
			g.fillRect(0, 600, 1140, 10);

			g2d.setFont(new Font("Monospace", Font.BOLD, 25));
			// g2d.setColor(new Color(124,252,0)); color is too bright?
			g2d.setColor(new Color(0, 0, 0));
			g2d.drawString("$" + money + "", 10, 645);
			g2d.drawString(population+" people in your city", 10, 20);
			g2d.drawString(pollution+" pollution levels", 10, 50);
			
			g2d.setColor(Color.BLUE);
			g2d.fillRect(120, 610, 70, 90);
			//Low residential
			g2d.fillRect(200, 610, 70, 90);
			//Low commercial
			g2d.fillRect(280, 610, 70, 90);
			//High Residential
			g2d.fillRect(360, 610, 70, 90);
			//High commercial
			g2d.fillRect(440, 610, 70, 90);

			g.setColor(Color.BLACK);
			if (placingRoad) {
				g.drawImage(roads.get(roadRotation), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			if(placingLowRes) {
				g.drawImage(lowRes.get(lowResIndex), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			for (GameObject obj : grids.values()) {
				if (obj instanceof Road) {
					g.drawImage(roads.get(((Road) obj).getIndex()), obj.getX(), obj.getY(), 30, 30, null);
				}
				else if(obj instanceof ResidentialBuilding) {
					if(((ResidentialBuilding)obj).isHigh()) {
						g.drawImage(highRes.get(((ResidentialBuilding)obj).getIndex()), obj.getX(), obj.getY(), 30, 30, null);
					}
					else {
						g.drawImage(lowRes.get(((ResidentialBuilding)obj).getIndex()), obj.getX(), obj.getY(), 30, 30, null);
					}
				}
			}
			// Image img2 = icon.getImage();
			// g2d.drawImage(img2, 10, 10, null);

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
		if(SwingUtilities.isLeftMouseButton(e) && e.getY() >= 600 && !placing) {
            System.out.println("Clicked on Buy Menu");
			if (e.getX()>=120 && e.getX()<=190 && e.getY()>=610 && e.getY()<=700){
				if(!placingRoad) {
					placingRoad = true;
				}
				placing = true;
			}
			else if(e.getX()>=200 && e.getX()<=270 && e.getY()>=610 && e.getY()<=700){
				if(!placingLowRes) {
					placingLowRes = true;
					lowResIndex = GenericUtilities.randomIndex(lowRes);
				}
				placing = true;
			}
			else if(e.getX()>=280 && e.getX()<=350 && e.getY()>=610 && e.getY()<=700){
				if(!placingLowCom) {
					placingLowCom = true;
					lowComIndex = GenericUtilities.randomIndex(lowCom);
				}
				placing = true;
			}
			else if(e.getX()>=360 && e.getX()<=430 && e.getY()>=610 && e.getY()<=700){
				if(!placingHighRes) {
					placingHighRes = true;
					highResIndex = GenericUtilities.randomIndex(highRes);
				}
				placing = true;
			}
			else if(e.getX()>=440 && e.getX()<=510 && e.getY()>=610 && e.getY()<=700){
				if(!placingHighCom) {
					placingHighCom = true;
					highComIndex = GenericUtilities.randomIndex(highCom);
				}
				placing = true;
			}
        }
		else if(SwingUtilities.isLeftMouseButton(e) && e.getY() <= 600 && placing) {
			//CODE TO DETERMINE COORDINATES
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
				if(placingRoad) {
					placingRoad = false;
					money-=10;
					Road r = new Road(coords[0], coords[1], roadRotation);
					grids.put(coords, r);
					//CHANGE THIS
					grid[coords[0]/30][coords[1]/30] = r;
					r.changeIndexes(coords[0]/30, coords[1]/30, grid);
				}
				if(placingLowRes) {
					placingLowRes = false;
					money-=100;
					ResidentialBuilding b = new ResidentialBuilding(coords[0], coords[1], false, new State(lowResIndex));
					grids.put(coords, b);
					grid[coords[0]/30][coords[1]/30] = b;
				}
				if(placingHighRes) {
					placingHighRes = false;
					money -= 700;
					ResidentialBuilding b = new ResidentialBuilding(coords[0], coords[1], true, new State(highResIndex));
					grids.put(coords, b);
					grid[coords[0]/30][coords[1]/30] = b;
				}
				if(placingLowCom) {
					placingLowCom = false;
					money -= 100;
				}
			}
			else {
				placingRoad = false;
				placingLowRes = false;
				placingHighRes = false;
			}
			placing = false;
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

	public void pathfind(Car a, GameObject a, GameObject b) { // pathfinding algorithm for cars in city
		int w = a.getX();
		int x = a.getY();
		int y = b.getX();
		int z = b.getY();

		int chX = w-y;
		int chY = x-z;
		if (w-y<0){
			chX*=-1;
		}
		if (x-z<0){
			chY*=-1;
		}

		//check if there are chX roads between car and gameobject

		//check if there are chY roads between car and gameobject

		//if no, then no path is possible

		//if road present
		//move horizontally
		//else
		//move vertically
		//repeat
		//
		// 
	}

	public static void initRoads() {
		ImageIcon icon = new ImageIcon("game_objects/images/roads/roadTwowayV.png");
		Image up = icon.getImage();
		roads.add(up);
		icon = new ImageIcon("game_objects/images/roads/roadTwowayH.png");
		Image side = icon.getImage();
		roads.add(side);

		// Corners
		//2 - leftUp, 3 - rightUp, 4 - rightDown, 5 - leftDown
		icon = new ImageIcon("game_objects/images/roads/roadLeftUp.png");
		Image leftUp = icon.getImage();
		roads.add(leftUp);
		icon = new ImageIcon("game_objects/images/roads/roadRightUp.png");
		Image rightUp = icon.getImage();
		roads.add(rightUp);
		icon = new ImageIcon("game_objects/images/roads/roadRightDown.png");
		Image rightDown = icon.getImage();
		roads.add(rightDown);
		icon = new ImageIcon("game_objects/images/roads/roadLeftDown.png");
		Image leftDown = icon.getImage();
		roads.add(leftDown);

		// 3-way intersections
		//6 - threeDown, 7 - threeUp, 8 - threeRight, 9 - threeLeft
		icon = new ImageIcon("game_objects/images/roads/roadThreewayDown.png");
		Image threeDown = icon.getImage();
		roads.add(threeDown);
		icon = new ImageIcon("game_objects/images/roads/roadThreewayUp.png");
		Image threeUp = icon.getImage();
		roads.add(threeUp);
		icon = new ImageIcon("game_objects/images/roads/roadThreewayRight.png");
		Image threeRight = icon.getImage();
		roads.add(threeRight);
		icon = new ImageIcon("game_objects/images/roads/roadThreewayLeft.png");
		Image threeLeft = icon.getImage();
		roads.add(threeLeft);

		// 4-way intersections
		icon = new ImageIcon("game_objects/images/roads/roadFourway.png");
		Image fourway = icon.getImage();
		roads.add(fourway);

		// Transparent 16 x 16 pixel cursor image.

		// Create a new blank cursor.
		// public static void curse(){
		// Cursor blankCursor =
		// Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
		// "blank cursor");

		// Set the blank cursor to the JFrame.
		// frame.getContentPane().setCursor(blankCursor);
		// }
	}

	public static void initResidentials(){
		ImageIcon icon = new ImageIcon("game_objects/images/residential buildings/house_1.png");
		Image house1 = icon.getImage();
		lowRes.add(house1);
		icon = new ImageIcon("game_objects/images/residential buildings/house 2.png");
		Image house2 = icon.getImage();
		lowRes.add(house2);
		icon = new ImageIcon("game_objects/images/residential buildings/residential_1.png");
		Image residential1 = icon.getImage();
		highRes.add(residential1);
		icon = new ImageIcon("game_objects/images/residential buildings/residential_2.png");
		Image residential2 = icon.getImage();
		highRes.add(residential2);
		icon = new ImageIcon("game_objects/images/residential buildings/residential_3.png");
		Image residential3 = icon.getImage();
		highRes.add(residential3);
		icon = new ImageIcon("game_objects/images/residential  buildings/residential_4.png");
		Image residential4 = icon.getImage();
		highRes.add(residential4);
	}
	public static void initCommercials() {
		ImageIcon icon = new ImageIcon("game_objects/images/commercial/commercial1.png");
		Image com1 = icon.getImage();
		lowCom.add(com1);
		icon = new ImageIcon("game_objects/images/commercial/commercial2.png");
		Image com2 = icon.getImage();
		lowCom.add(com2);
		icon = new ImageIcon("game_objects/images/commercial/commercial3.png");
		Image com3 = icon.getImage();
		lowCom.add(com3);
		icon = new ImageIcon("game_objects/images/commercial/tower 1.png");
		Image tower1 = icon.getImage();
		highCom.add(tower1);
	}


	class Keychecker extends KeyAdapter {
		@Override
    	public void keyPressed(KeyEvent event) {
       		char ch = event.getKeyChar();
			if(ch == 'r' || ch == 'R') {
				if(roadRotation == 0) {
					roadRotation = 1;
				}
				else {
					roadRotation = 0;
				}
			}
    	}
	}

	
}