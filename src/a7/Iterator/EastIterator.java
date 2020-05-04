package a7.Iterator;

import a7.Spot;
import a7.SpotBoard;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EastIterator implements Iterator<Spot> {
    private int _currentX;
    private int _currentY;
    private SpotBoard _board;

    public EastIterator(SpotBoard board, int startX, int startY) {
        _board = board;
        _currentX = startX;
        _currentY = startY;
    }
    public boolean hasNext() {
        return (_currentX+1 <= 7);
    }

    public Spot next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        _currentX += 1;
        Spot spot = _board.getSpotAt(_currentX, _currentY);
        return spot;
    }

}
