package a7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {

    private enum Player {BLACK, WHITE};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private Spot _blueSpots;
    private Spot _greenSpots;
    private Player _nextToPlay;
    private Color _winningColor;

    public TicTacToeWidget(){
        _board = new JSpotBoard(3,3, Color.GRAY);
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
        _nextToPlay = Player.WHITE;
        _gameWon = false;
        _winningColor = null;
        _message.setText("Welcome to the TicTacToe. White to play");
    }

    public void spotClicked(Spot spot) {
        if (_gameWon || !spot.isEmpty()) {
            return;
        }
        String player_name = null;
        String next_player_name = null;
        Color player_color = null;

        if (_nextToPlay == Player.WHITE) {
            player_color = Color.WHITE;
            player_name = "White";
            next_player_name = "Black";
            _nextToPlay = Player.BLACK;
        } else {
            player_color = Color.BLACK;
            player_name = "Black";
            next_player_name = "White";
            _nextToPlay = Player.WHITE;
        }


        /* Set color of spot clicked and toggle. */
        spot.setSpotColor(player_color);
        spot.toggleSpot();
        _gameWon = checkOneCombination(0,0, 1,0, 2,0)
                || checkOneCombination(0,1,1,1,2,1)
                || checkOneCombination(0,2,1,2,2,2)
                || checkOneCombination(0,0, 0,1,0, 2)
                || checkOneCombination(1,0, 1, 1, 1, 2)
                || checkOneCombination(2,0, 2,1, 2,2)
                || checkOneCombination(0,0, 1,1,2,2)
                || checkOneCombination(2,0, 1, 1, 0,2);

        if (_gameWon) {
            _message.setText(_winningColor.toString());
        }


        /* Check if spot clicked is secret spot.
         * If so, mark game as won and update background
         * of spot to show that it was the secret spot.
         */


        /* Update the message depending on what happened.
         * If spot is empty, then we must have just cleared the spot. Update message accordingly.
         * If spot is not empty and the game is won, we must have
         * just won. Calculate score and display as part of game won message.
         * If spot is not empty and the game is not won, update message to
         * report spot coordinates and indicate whose turn is next.
         */

    }

    public void spotEntered(Spot spot) {
        if (_gameWon) {
            return;
        }
        spot.highlightSpot();
    }

    public void spotExited(Spot spot) {
        spot.unhighlightSpot();
    }

    public void actionPerformed(ActionEvent e) {
        resetGame();
    }
    private boolean checkOneCombination(int x1, int y1, int x2, int y2, int x3, int y3) {
        if((_board.getSpotAt(x1, y1).getSpotColor() == _board.getSpotAt(x2, y2).getSpotColor()) &&
                _board.getSpotAt(x1, y1).getSpotColor() == _board.getSpotAt(x3, y3).getSpotColor() &&
                _board.getSpotAt(x1,y1).getSpotColor() != null &&
                _board.getSpotAt(x2,y2).getSpotColor() != null &&
                _board.getSpotAt(x3,y3).getSpotColor() != null) {
            _winningColor = _board.getSpotAt(x1, y1).getSpotColor();
        }
        return (_board.getSpotAt(x1, y1).getSpotColor() == _board.getSpotAt(x2, y2).getSpotColor() &&
                _board.getSpotAt(x1, y1).getSpotColor() == _board.getSpotAt(x3, y3).getSpotColor()&&
                _board.getSpotAt(x1,y1).getSpotColor() != null &&
                _board.getSpotAt(x2,y2).getSpotColor() != null &&
                _board.getSpotAt(x3,y3).getSpotColor() != null);
    }

}
