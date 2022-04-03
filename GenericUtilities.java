import java.util.*;
import java.awt.*;

public class GenericUtilities {
    public static int randomIndex(ArrayList<Image> arr) {
        return (int)(Math.random() * arr.size());
    }
}