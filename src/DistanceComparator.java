import java.util.Comparator;

/**
 * Write a description of class FaceDistanceComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceComparator implements Comparator<Triangle>
{
    public int compare(Triangle t1, Triangle t2) {
        Point p1 = t1.getCenter();
        Point p2 = t2.getCenter();
        return (int)Math.sqrt(Math.pow(p2.getX(), 2) + Math.pow(p2.getY(), 2) + Math.pow(p2.getZ() + 10, 2))
        - (int)Math.sqrt(Math.pow(p1.getX(), 2) + Math.pow(p1.getY(), 2) + Math.pow(p1.getZ() + 10, 2));
    }
}
