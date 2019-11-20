package a7;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValidMoveChecker {

    private int x;
    private Spot _spot;
    private Color _playerColor;
    private Iterator<Spot> _spotIterator;
    private List<Spot> _listOfSpotsToFlip;

    public ValidMoveChecker(Color playerColor, Iterator<Spot> directionIterator) {
        _spot = null;
        _playerColor = playerColor;
        _spotIterator = directionIterator;
        _listOfSpotsToFlip = new ArrayList<>();

    }

    public boolean checkForValidMove() {
        while (_spotIterator.hasNext()) {
            Spot spot = _spotIterator.next();
            Color spotColor = spot.getSpotColor();
            if (spotColor == Color.GREEN) {
                removeSpotsInList(_listOfSpotsToFlip);
                return false;
            }
            else {
                if (spotColor != _playerColor) {
                    _listOfSpotsToFlip.add(spot);
                }
                else {
                    if (!_listOfSpotsToFlip.isEmpty()) {
                        return true;
                    }
                    else return false;
                }

            }
        }
        removeSpotsInList(_listOfSpotsToFlip);
        return false;
    }

    public List<Spot> getSpotListToFlip(){
        return _listOfSpotsToFlip;
    }

    private void removeSpotsInList(List<Spot> listOfSpots) {
        if (!listOfSpots.isEmpty()) {
            listOfSpots.clear();
        }
    }
}
