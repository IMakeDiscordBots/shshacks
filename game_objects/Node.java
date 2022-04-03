package game_objects;

public class Node {
    private Road road;
    private Node top;
    private Node down;
    private Node left;
    private Node right;
    public Node(Road r) {
        road = r;
    }
    public Road getRoad() {
        return road;
    }
    public void setRoad(Road r) {
        road = r;
    }
    public void setRight(Node n) {
        right = n;
    }
    public void setLeft(Node n) {
        left = n;
    }
    public void setTop(Node n) {
        top = n;
    }
    public void setDown(Node n) {
        down = n;
    }
    public Node getRight() {
        return right;
    }
    public Node getLeft() {
        return left;
    }
    public Node getTop() {
        return top;
    }
    public Node getDown() {
        return down;
    }
    public int count() {
        int count = 0;
        if(right != null) {
            count++;
        }
        if(left != null) {
            count++;
        }
        if(top != null) {
            count++;
        }
        if(down != null) {
            count++;
        }
        return count;
    }

    public boolean equals(Node n) {
        if(n.getRoad() == getRoad()) {
            return true;
        }
        return false;
    }
}