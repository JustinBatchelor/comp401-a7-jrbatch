package a7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener {

    private int x;
    private int y;

    private enum Player{RED, BLACK};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private Player _nextToPlay;

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
        _message.setText("Welcome to ConnectFour. Red to play");
    }

    public void spotClicked(Spot spot) {
        int spotx = spot.getSpotX();
        int spoty = spot.getSpotY();

        if (_gameWon || !spot.isEmpty()) {
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

        for (y=0; y<_board.getSpotHeight(); y++) {
            if (_board.getSpotAt(spotx, y).isEmpty()) {
            }
        }

        spot.setSpotColor(player_color);
        spot.toggleSpot();
        spot.unhighlightSpot();



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
}
