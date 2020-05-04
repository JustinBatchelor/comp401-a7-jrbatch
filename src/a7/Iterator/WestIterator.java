package a7.Iterator;

import a7.Spot;
import a7.SpotBoard;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class WestIterator implements Iterator<Spot> {

    private int _currentX;
    private int _currentY;
    private SpotBoard _board;

    public WestIterator(SpotBoard board, int startX, int startY){
        _currentX = startX;
        _currentY = startY;
        _board = board;
    }

    public boolean hasNext() {
        return (_currentX - 1 >= 0);
    }

    public Spot next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        _currentX -= 1;
        Spot spot = _board.getSpotAt(_currentX, _currentY);
        return spot;
    }
}
