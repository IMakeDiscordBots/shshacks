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
	public static ArrayList<Car> carsOnRoad = new ArrayList<Car>();
	//public static ArrayList<Tree> treesInCity = new ArrayList<Tree>();
	static int money = 150;
	static int pollution = 0;
	static int population = 100;
	static String severity = "Clean";
	static int happy = 150;
	static boolean end = false;

	static int roadRotation = 0;
	static int lowResIndex = 0;
	static int highResIndex = 0; //ITS A TREE
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
	boolean placingHighRes = false; //tree
	boolean placingLowCom = false;
	boolean placingHighCom = false;
	int cursorXCoord = MouseInfo.getPointerInfo().getLocation().x;
	int cursorYCoord = MouseInfo.getPointerInfo().getLocation().y;
	// Divide/multiply by thirty
	static GameObject[][] grid = new GameObject[40][22];
	Map<int[], GameObject> grids = new HashMap<int[], GameObject>();

	public static void main(String[] args) {
		Timer whiteTimer = new Timer();
		TimerTask whiteTimerTask = new ClockTask();
		whiteTimer.scheduleAtFixedRate(whiteTimerTask, 1000, 4000-(happy*8)); //slower with less happy
		TimerTask carTicking = new Tick();
		whiteTimer.scheduleAtFixedRate(carTicking, 20000, 100);
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

			g2d.setColor(Color.BLUE);
			g2d.setFont(new Font("Monospace", Font.BOLD, 25));
			g2d.setColor(new Color(0, 0, 0));
			g2d.drawString("$" + money + "", 10, 645);
			g2d.drawString("Number of People in Your City: " + population, 10, 50);
			g2d.drawString("The Pollution Level(based off of API): " + pollution,  10, 80);
			g2d.drawString("The Happiness Level: " + happy, 10, 110);
			if (pollution <= 75) {
				Main.severity = "Clean";
			} else if (pollution <= 150){
				Main.severity = "Moderately Unhealthy";
			} else if (pollution <= 200) {
				Main.severity = "Unhealthy";
			} else if (pollution <= 300) {
				Main.severity = "Very Unhealthy";
				population -= (Math.floor(Math.random() * 4));
			} else {
				population -= 7;
				Main.severity = "Very Severe";
			}
			g2d.drawString("Severity Level: " + severity, 10, 140);
			
			g2d.setFont(new Font("Monospace", Font.BOLD, 20));
			g2d.setColor(Color.BLUE);
			g2d.fillRect(120, 610, 70, 90);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Roads", 125, 650);
			//Low residential
			g2d.setColor(Color.BLUE);
			g2d.fillRect(200, 610, 200, 90);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Residential", 210, 650);
			//Low commercial
			g2d.setColor(Color.BLUE);
			g2d.fillRect(410, 610, 200, 90);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Small Commercial", 420, 650);
			//TREE
			g2d.setColor(Color.BLUE);
			g2d.fillRect(620, 610, 200, 90);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Tree", 690, 650);
			//High commercial
			g2d.setColor(Color.BLUE);
			g2d.fillRect(830, 610, 200, 90);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Large Commercial", 840, 650);
			

			g.setColor(Color.BLACK);
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
				else if(obj instanceof CommercialBuilding) { //tree
					if(((CommercialBuilding)obj).isHigh()) {
						g.drawImage(highCom.get(((CommercialBuilding)obj).getIndex()), obj.getX(), obj.getY(), 30, 30, null);
					}
					else {
						g.drawImage(lowCom.get(((CommercialBuilding)obj).getIndex()), obj.getX(), obj.getY(), 30, 30, null);
					}
				}
			}
			if (placingRoad) {
				g.drawImage(roads.get(roadRotation), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			if(placingLowRes) {
				g.drawImage(lowRes.get(lowResIndex), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			if(placingLowCom) {
				g.drawImage(lowCom.get(lowComIndex), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			if(placingHighRes) {
				g.drawImage(highRes.get(highResIndex), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			if(placingHighCom) {
				g.drawImage(highCom.get(highComIndex), cursorXCoord - 20, cursorYCoord - 35, 30, 30, null);
			}
			// Image img2 = icon.getImage();
			// g2d.drawImage(img2, 10, 10, null);

			//Draw cars
			g2d.setColor(Color.RED);
			for(Car car : carsOnRoad) {
				if(car.getPath().size() > 1) {
					//Left
					if(car.getPath().get(car.getIndex() + 1).getX() < car.getX()) {
						g2d.fillRect(car.getPath().get(0).getX() + 20, car.getPath().get(0).getY() - 4, 6, 6);
					}
					//Up
					if(car.getPath().get(car.getIndex() + 1).getY() < car.getY()) {
                		g2d.fillRect(car.getPath().get(0).getX() + 20, car.getPath().get(0).getY() + 24, 6, 6);
            		}
					//Down
					if(car.getPath().get(car.getIndex() + 1).getY() > car.getY()) {
						g2d.fillRect(car.getPath().get(0).getX() + 4, car.getPath().get(0).getY(), 6, 6);
            		}
					//Right
					if(car.getPath().get(car.getIndex() + 1).getX() > car.getX()) {
                		g2d.fillRect(car.getPath().get(0).getX() + 6, car.getPath().get(0).getY() + 20, 6, 6);
            		}
				}
			}

			if (end){
				super.paint(g);
				ImageIcon icon = new ImageIcon("game_objects/images/loseScreen.png");
				Image lose = icon.getImage();
				g.drawImage(lose, 0, 0, 1140, 700, null);

			}

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
			else if(e.getX()>=200 && e.getX()<=400 && e.getY()>=610 && e.getY()<=700){
				if(!placingLowRes) {
					placingLowRes = true;
					lowResIndex = GenericUtilities.randomIndex(lowRes);
				}
				placing = true;
			}
			else if(e.getX()>=410 && e.getX()<=610 && e.getY()>=610 && e.getY()<=700){
				if(!placingLowCom) {
					placingLowCom = true;
					lowComIndex = GenericUtilities.randomIndex(lowCom);
				}
				placing = true;
			}
			else if(e.getX()>=620 && e.getX()<=820 && e.getY()>=610 && e.getY()<=700){
				if(!placingHighRes) {
					placingHighRes = true;
					highResIndex = GenericUtilities.randomIndex(highRes);
				}
				placing = true;
			}
			else if(e.getX()>=830 && e.getX()<=1030 && e.getY()>=610 && e.getY()<=700){
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
					money-=20;
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
					money -= 70;
					ResidentialBuilding b = new ResidentialBuilding(coords[0], coords[1], true, new State(highResIndex));
					grids.put(coords, b);
					grid[coords[0]/30][coords[1]/30] = b;
				}
				if(placingLowCom) {
					placingLowCom = false;
					money -= 100;
					CommercialBuilding c = new CommercialBuilding(coords[0], coords[1], false, new State(lowComIndex));
					grids.put(coords, c);
					grid[coords[0]/30][coords[1]/30] = c;
				}
				if(placingHighCom) {
					placingHighCom = false;
					money -= 175;
					CommercialBuilding c = new CommercialBuilding(coords[0], coords[1], true, new State(highComIndex));
					grids.put(coords, c);
					grid[coords[0]/30][coords[1]/30] = c;
				}
			}
			else {
				placingRoad = false;
				placingLowRes = false;
				placingHighRes = false;
				placingLowCom = false;
				placingHighCom = false;
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

	

	public static ArrayList<Road> pathfind2(Car car, GameObject a, GameObject b) {
		ArrayList<Node> openList = new ArrayList<Node>();
		Set<Node> closedList = new HashSet<Node>();

		Node first = ((Road)grid[a.getX()/30][a.getY()/30]).getNode();
		
		boolean[] bool = {false};
		ArrayList<Road> path = getPath(new ArrayList<Road>(), first, b, closedList, bool);
		System.out.println(path);
		if(path.size() > 0 && checkForBuilding(path.get(path.size() - 1), b)) {
			return path;
		}
		else {
			path = new ArrayList<Road>();
			return path;
		}
	}
	
	// calculating cost for each adjacent tile for pathfinding of cars
	public int cost(int startX, int startY, int finalX, int finalY) {
		return (finalX - startX) + (finalY - startY);
	}
	
	//checking if right tile is a Road
	public boolean isRightRoad(Car car) {
		
		try {

			return (grid[car.getY()][car.getX() + 1]) instanceof Road;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	//checking if above tile is a Road
	public boolean isUpRoad(Car car) {
		try {

			return (grid[car.getY() - 1][car.getX()]) instanceof Road;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	//checking if left tile is a Road
	public boolean isLeftRoad(Car car) {
		try {

			return (grid[car.getY()][car.getX() - 1]) instanceof Road;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	//checkign if below tile is a Road
	public boolean isDownRoad(Car car) {
		try {
			return (grid[car.getY() + 1][car.getX()]) instanceof Road;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	public static ArrayList<Road> getPath(ArrayList<Road> p, Node n, GameObject end, Set<Node> closed, boolean[] b) {
		int closedHas = 0;
		p.add(n.getRoad());
		
		ArrayList<int[]> openList = new ArrayList<int[]>();

		
		//Check if building is connected to the road (right, up, left, down order) || grid[b.getX()][b.getY() - 1] instanceof Road || grid[b.getX() - 1][b.getY()] instanceof Road
		/*if(((GameObject)(grid[start.getX() + 1][start.getY()])) instanceof Road) {
			ArrayList.add({start.getX(), start.getY(), cost(grid[start.getX() + 1][start.getY()].getX(), grid[start.getX() + 1][start.getY()].getY(), end.getX(), end.getY()});
		}*/
		if(checkForBuilding(n.getRoad(), end) || b[0]) {
			System.out.println("Found building at " + p);
			b[0] = true;
			return p;
		}
		closed.add(n);
		try {
			if(!b[0]) {
				if(!closed.contains(n.getRight())) {
					System.out.println("Entering recursion");
					p = getPath(p, n.getRight(), end, closed, b);
				}
				else {
					closedHas++;
				}
			}
		}
		catch(Exception e) { System.out.println("Does not have right node");}
		try {
			if(!b[0]) {
				if(!closed.contains(n.getLeft())) {
					System.out.println("Entering recursion");
					p = getPath(p, n.getLeft(), end, closed, b);
				}
				else {
					closedHas++;
				}
			}
		}
		catch(Exception e) { System.out.println("Does not have left node");}
		try {
			if(!b[0]) {
				if(!closed.contains(n.getTop())) {
					System.out.println("Entering recursion");
					p = getPath(p, n.getTop(), end, closed, b);
				}
				else {
					closedHas++;
				}
			}
		}
		catch(Exception e) { System.out.println("Does not have top node");}
		try {
			if(!b[0]) {
				if(!closed.contains(n.getDown())) {
					System.out.println("Entering recursion");
					p = getPath(p, n.getDown(), end, closed, b);
				}
				else {
					closedHas++;
				}
			}
		}
		catch(Exception e) { System.out.println("Does not have bottom node");}
		if(closedHas >= n.count()) {
			System.out.println("No tiles connected");
			p.remove(n.getRoad());
			return p;
		}
		if(!b[0]) {
			p.remove(n.getRoad());
		}
		return p;
	}
	public static boolean checkForBuilding(Road r, GameObject b) {
		if(r.getX() - 30 >= 0) {
            if(r.getX() - 30 == b.getX() && r.getY() == b.getY()) {
                return true;
            }
        }
        if(r.getX() + 30 < 1140) {
            if(r.getX() + 30 == b.getX() && r.getY() == b.getY()) {
                return true;
            }
        }
        if(r.getY() - 30 >= 0) {
            if(r.getX() == b.getX() && r.getY() - 30 == b.getY()) {
                return true;
            }
        }
        if(r.getY() + 30 < 600) {
            if(r.getX() == b.getX() && r.getY() + 30 == b.getY()) {
                return true;
            }
        }
		return false;
	}

	//check if there are chX roads between car and gameobject
		
	//check if there are chY roads between car and gameobject
		
	//if no, then no path is possible

	//calculate all possible paths, compare, then choose least by how many grid spaces passed
		
		
	

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
		icon = new ImageIcon("game_objects/images/residential buildings/tree1.png");
		Image tree1 = icon.getImage();
		highRes.add(tree1);
		//icon = new ImageIcon("game_objects/images/residential buildings/tree1.png");
		//Image residential2 = icon.getImage();
		//highRes.add(residential2);
		//icon = new ImageIcon("game_objects/images/residential buildings/residential_3.png");
		//Image residential3 = icon.getImage();
		//highRes.add(residential3);
		//icon = new ImageIcon("game_objects/images/residential  buildings/residential_4.png");
		//Image residential4 = icon.getImage();
		//highRes.add(residential4);
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

	//public static void initTree() {
	//	ImageIcon icon = new ImageIcon("game_objects/images/residential buildings/tree1.png");
	//	Image t1 = icon.getImage();
	//	tree.add(t1);
	//}

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