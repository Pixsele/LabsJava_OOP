package function.api;

import java.util.Iterator;

public interface Iterable<Point> {
    Iterator<Point> iterator();
}