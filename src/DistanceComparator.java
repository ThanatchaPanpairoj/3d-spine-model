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
        return t2.getDistance() - t1.getDistance();
    }
}
