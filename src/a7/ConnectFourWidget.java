package a7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener {

    private int x;
    private int y;

    private enum Player{RED, BLACK};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private Player _nextToPlay;
    private Color _winningColor;
    private boolean _didBreak;

    public ConnectFourWidget() {
        _board = new JSpotBoard(7, 6,
                new Color(0.8f, 0.8f, 0.8f),
                new Color(0.5f, 0.5f, 0.5f));

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
        }
        _nextToPlay = Player.RED;
        _gameWon = false;
        _didBreak = false;
        _message.setText("Welcome to ConnectFour. Red to play");
    }

    public void spotClicked(Spot spot) {
        int spotx = spot.getSpotX();
        int spoty = spot.getSpotY();

        if (_gameWon) {
            return;
        }

        String player_name = null;
        String next_player_name = null;
        Color player_color = null;

        if (_nextToPlay == Player.RED) {
            player_color = Color.RED;
            player_name = "RED";
            next_player_name = "Black";
            _nextToPlay = Player.BLACK;
        } else {
            player_color = Color.BLACK;
            player_name = "Black";
            next_player_name = "Red";
            _nextToPlay = Player.RED;
        }

        for (y=_board.getSpotHeight()-1; y>-1; y--) {
            if (_board.getSpotAt(spotx,y).isEmpty()) {
                _board.getSpotAt(spotx,y).setSpotColor(player_color);
                _board.getSpotAt(spotx,y).toggleSpot();
                _board.getSpotAt(spotx,y).unhighlightSpot();
                break;
            }
        }
        checkGameStatus();

        if (!spot.isEmpty()) {
            _message.setText(next_player_name + " to play.");
        }

        if (_gameWon) {
            _message.setText(player_name + " won the game!!!");
        }

    }

    public void spotEntered(Spot spot) {
        int spotx = spot.getSpotX();
        int spoty = spot.getSpotY();

        if (_gameWon) {
            return;
        }

        // Asking the board which spots are empty in the column
        // based on the spot the mouse is hovering over.  Then highlighting
        // accordingly....
        for (y=0; y<_board.getSpotHeight(); y++) {
            if (_board.getSpotAt(spotx, y).isEmpty()) {
                _board.getSpotAt(spotx, y).highlightSpot();
            }
        }

    }

    public void spotExited(Spot spot) {
        int spotx = spot.getSpotX();
        int spoty = spot.getSpotY();
        if (_gameWon) {
            return;
        }
        for (y=0; y<_board.getSpotHeight(); y++) {
            if (_board.getSpotAt(spotx, y).isHighlighted()) {
                _board.getSpotAt(spotx, y).unhighlightSpot();
            }
        }

    }
    public void actionPerformed(ActionEvent e) {
        resetGame();
    }

    private boolean checkGameStatus(){
        Color red = Color.RED;
        Color black = Color.BLACK;

        int width = _board.getWidth();
        int height = _board.getSpotHeight();

        for (x=0; x<width-3; x++) {
            for (y=0;y<height-3; y++) {
                Spot currentSpot = _board.getSpotAt(x,y);
                Color currentColor = currentSpot.getSpotColor();

                // X Spots
                Spot spotXPlus1 = _board.getSpotAt((x+1), y);
                Spot spotXPlus2 = _board.getSpotAt((x+2), y);
                Spot sportXPlus3 = _board.getSpotAt((x+3), y);

                // Y Spots
                Spot spotYPlus1 = _board.getSpotAt(x, (y+1));
                Spot spotYPlus2 = _board.getSpotAt(x, (y+2));
                Spot sportYPlus3 = _board.getSpotAt(x, (y+3));

                if (currentSpot.getSpotColor() != Color.RED || currentSpot.getSpotColor() != Color.BLACK) {
                    continue;
                }
                // Horizontal Checks
                if (currentColor == spotXPlus1.getSpotColor() &&
                currentColor == spotXPlus2.getSpotColor() &&
                currentColor == sportXPlus3.getSpotColor()) {
                    _winningColor = currentColor;
                    _gameWon = true;
                    _didBreak = true;
                    break;
                }
            }

            if (_didBreak) {
                break;
            }
        }

        return _gameWon;
    }
}
