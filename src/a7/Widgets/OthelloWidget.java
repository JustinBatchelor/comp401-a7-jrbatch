package a7.Widgets;

import a7.*;
import a7.Iterator.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class OthelloWidget extends JPanel implements ActionListener, SpotListener {
                                              // Variables
    private int x;
    private int y;
    private int _whiteScore;
    private int _blackScore;

    private enum Player{BLACK, WHITE};
    private boolean _gameWon;
    private boolean _stalemate;

    private String _playerName;
    private Player _nextToPlay;
    private Color _nextToPlayColor;

    private Color _winningColor;
    private String _winningPlayerName;
    private JSpotBoard _board;
    private JLabel _message;

    private List<List<Spot>> _listOfSpotsToFlip = new ArrayList<>();

                                            // Listener Methods
    public void spotClicked(Spot spot) {
        if (_gameWon || !spot.isHighlighted()) {
            return;
        }
        flipColorsOnClick(_listOfSpotsToFlip);
        spot.setSpotColor(_nextToPlayColor);
        spot.setSpot();
        spot.unhighlightSpot();

        String player_name = null;
        String next_player_name = null;
        Color player_color = null;

        if (_nextToPlay == Player.WHITE) {
            player_color = Color.WHITE;
            player_name = "White";
            next_player_name = "Black";
            _nextToPlay = Player.BLACK;
            _nextToPlayColor = Color.BLACK;
            _playerName = "Black";
        } else {
            player_color = Color.BLACK;
            player_name = "Black";
            next_player_name = "White";
            _nextToPlay = Player.WHITE;
            _nextToPlayColor = Color.WHITE;
            _playerName = "White";
        }
        _message.setText(next_player_name + " to play");
        resetMainList();
        scoreCounter(gameOver());
    }

    public void spotEntered(Spot spot) {
        if (_gameWon || !spot.isEmpty()) {
           return;
        }
        if (hasValidMove(spot)) {
            spot.highlightSpot();
        }

    }

    public void spotExited(Spot spot) {
        spot.unhighlightSpot();
        resetMainList();
    }

    public void actionPerformed(ActionEvent e) {
        resetGame();
    }

    public OthelloWidget() {
        _board = new JSpotBoard(8, 8);
        _message = new JLabel();
        setLayout(new BorderLayout());
        add(_board, BorderLayout.CENTER);

        JPanel reset_message_panel = new JPanel();
        reset_message_panel.setLayout(new BorderLayout());
        JButton reset_button = new JButton("Restart");

        reset_button.addActionListener(this);
        reset_message_panel.add(reset_button, BorderLayout.EAST);
        reset_message_panel.add(_message, BorderLayout.CENTER);

        add(reset_message_panel, BorderLayout.SOUTH);

        _board.addSpotListener(this);
        resetGame();
    }

    private void resetGame(){
        for (Spot s : _board) {
            s.clearSpot();
            s.setSpotColor(Color.GREEN);
            s.unhighlightSpot();
        }
        _nextToPlay = Player.BLACK;
        _nextToPlayColor = Color.BLACK;
        _gameWon = false;
        _winningColor = null;
        _stalemate = false;
        _whiteScore = 0;
        _blackScore = 0;
        _playerName = "Black";
        setInitialGameBoard();
        _message.setText("Welcome to Othello. Black to play");
    }

    private void setInitialGameBoard() {
        //Set initial colors
        _board.getSpotAt(3,3).setSpotColor(Color.WHITE);
        _board.getSpotAt(4,4).setSpotColor(Color.WHITE);
        _board.getSpotAt(3,4).setSpotColor(Color.BLACK);
        _board.getSpotAt(4,3).setSpotColor(Color.BLACK);

        //Toggle Spots
        _board.getSpotAt(3,3).setSpot();
        _board.getSpotAt(4,4).setSpot();
        _board.getSpotAt(3,4).setSpot();
        _board.getSpotAt(4,3).setSpot();
    }

    private void addSpotListToMainList(List<Spot> directionList){
        if (!directionList.isEmpty()) {
            _listOfSpotsToFlip.add(directionList);
        }
    }

    private void resetMainList() {
        if (!_listOfSpotsToFlip.isEmpty()) {
            _listOfSpotsToFlip.clear();
        }
    }

    private void flipColorsOnClick(List<List<Spot>> mainSpotList) {
        if (!mainSpotList.isEmpty()) {
            if (_nextToPlayColor == Color.BLACK) {
                for (x=0; x<mainSpotList.size(); x++ ) {
                    for (y=0; y<mainSpotList.get(x).size();y++) {
                        Spot spot = mainSpotList.get(x).get(y);
                        spot.setSpotColor(Color.BLACK);
                        if (spot.isEmpty()) {
                            spot.setSpot();
                        }
                    }
                }
            }
            if (_nextToPlayColor == Color.WHITE) {
                for (x=0; x<mainSpotList.size(); x++ ) {
                    for (y=0; y<mainSpotList.get(x).size();y++) {
                        Spot spot = mainSpotList.get(x).get(y);
                        spot.setSpotColor(Color.WHITE);
                        if (spot.isEmpty()) {
                            spot.setSpot();;
                        }
                    }
                }
            }
        }
    }

    private boolean hasValidMove(Spot passedSpot) {
        // This method will be called when a spot is entered, we will pass through the spot the mouse is on
        // then create a new Class "ValidMoveChecker" that will store an individual iterator.
        // Each Iterator will move in a different direction for a total of 8 directions conventionally named
        // based on a compass direction
        int spotX = passedSpot.getSpotX();
        int spotY = passedSpot.getSpotY();

        ValidMoveChecker northChecker = new ValidMoveChecker(_nextToPlayColor, new NorthIterator(_board, spotX,spotY));
        ValidMoveChecker eastChecker = new ValidMoveChecker( _nextToPlayColor, new EastIterator(_board, spotX, spotY));
        ValidMoveChecker southChecker = new ValidMoveChecker(_nextToPlayColor, new SouthIterator(_board,spotX, spotY));
        ValidMoveChecker westChecker = new ValidMoveChecker(_nextToPlayColor, new WestIterator(_board, spotX,spotY));
        ValidMoveChecker northWestChecker = new ValidMoveChecker(_nextToPlayColor, new NorthWestIterator(_board,spotX,spotY));
        ValidMoveChecker northEastChecker = new ValidMoveChecker(_nextToPlayColor, new NorthEastIterator(_board,spotX,spotY));
        ValidMoveChecker southEastChecker = new ValidMoveChecker( _nextToPlayColor, new SouthEastIterator(_board,spotX,spotY));
        ValidMoveChecker southWestChecker = new ValidMoveChecker( _nextToPlayColor, new SouthWestIterator(_board, spotX, spotY));

        boolean north = northChecker.checkForValidMove();
        boolean east = eastChecker.checkForValidMove();
        boolean south = southChecker.checkForValidMove();
        boolean west = westChecker.checkForValidMove();
        boolean northWest = northWestChecker.checkForValidMove();
        boolean northEast = northEastChecker.checkForValidMove();
        boolean southEast = southEastChecker.checkForValidMove();
        boolean southWest = southWestChecker.checkForValidMove();

        addSpotListToMainList(northChecker.getSpotListToFlip());
        addSpotListToMainList(eastChecker.getSpotListToFlip());
        addSpotListToMainList(southChecker.getSpotListToFlip());
        addSpotListToMainList(westChecker.getSpotListToFlip());
        addSpotListToMainList(northWestChecker.getSpotListToFlip());
        addSpotListToMainList(northEastChecker.getSpotListToFlip());
        addSpotListToMainList(southEastChecker.getSpotListToFlip());
        addSpotListToMainList(southWestChecker.getSpotListToFlip());
        return !_listOfSpotsToFlip.isEmpty();
    }

    private boolean hasNoValidMove() {
        List<Spot> listOfValidSpotsToMove = new ArrayList<>();
        for(y=0; y<_board.getSpotHeight(); y++) {
            for(x=0; x<_board.getSpotWidth(); x++) {
                Spot spot = _board.getSpotAt(x,y);
                if (spot.getSpotColor() == Color.GREEN) {
                    if (hasValidMove(spot)) {
                        listOfValidSpotsToMove.add(spot);
                    }
                }
            }
        }
        return listOfValidSpotsToMove.isEmpty();
    }

    // Switches Player will be called if there is no valid move in spot entered
    private void switchPlayer(Color playerColor) {
        if (!_gameWon) {
            if (playerColor == Color.BLACK) {
                _nextToPlay = Player.WHITE;
                _nextToPlayColor = Color.WHITE;
                _playerName = "White";
            }
            if (playerColor == Color.WHITE) {
                _nextToPlay = Player.BLACK;
                _nextToPlayColor = Color.BLACK;
                _playerName = "Black";
            }
        }
    }

    private boolean gameOver() {
        if(hasNoValidMove() && !_gameWon) {
            // Means that the current player doesn't have a valid move
            _message.setText(_playerName + " has no valid Move");
            switchPlayer(_nextToPlayColor);
            _message.setText(_playerName + " to play");
            if (hasNoValidMove()) {
                // if this block runs.. neither player has a valid move
                _gameWon = true;
                _stalemate = true;
                return true;
            }
        }
        for (y=0; y<_board.getSpotHeight(); y++) {
            for(x=0; x< _board.getSpotWidth(); x++) {
                Spot spot = _board.getSpotAt(x,y);
                if (spot.getSpotColor() == Color.GREEN) {
                    _whiteScore = 0;
                    _blackScore = 0;
                    return false;
                }
            }
        }
        return true;
    }

    private void scoreCounter(boolean isGameOver) {
        if (isGameOver) {
            for (y=0; y<_board.getSpotHeight(); y++) {
                for(x=0; x< _board.getSpotWidth(); x++) {
                    Spot spot = _board.getSpotAt(x,y);
                    if(spot.getSpotColor() == Color.WHITE) {
                        _whiteScore+=1;
                    }
                    if (spot.getSpotColor() == Color.BLACK) {
                        _blackScore+=1;
                    }
                }
            }
            if (_whiteScore > _blackScore) {
                _winningColor = Color.WHITE;
                _stalemate = false;
                _gameWon =true;
                _winningPlayerName = "White";
            }
            if (_blackScore > _whiteScore) {
                _winningColor = Color.BLACK;
                _stalemate = false;
                _winningPlayerName = "Black";
                _gameWon = true;
            }
            if (_whiteScore == _blackScore) {
                _stalemate = true;
                _gameWon = true;
            }

            if (!_stalemate) {
                _message.setText("Black Scored " + _blackScore + " points. " +
                        "White Scored " + _whiteScore + " points. " +
                        _winningPlayerName + " Wins");
            }
            if (_gameWon && _stalemate) {
                _message.setText("It's a Draw.  Press the restart button to play again");
            }
        }

    }

}
