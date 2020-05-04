package a7.Widgets;

import a7.JSpot;
import a7.JSpotBoard;
import a7.Spot;
import a7.SpotListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {

    private int x;
    private int y;

    private enum Player {BLACK, WHITE};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private boolean _stalemate;
    private Player _nextToPlay;
    private int _counter;

    private Integer[] winningCombo1;
    private Integer[] winningCombo2;
    private Integer[] winningCombo3;
    private Integer[] winningCombo4;
    private Integer[] winningCombo5;
    private Integer[] winningCombo6;
    private Integer[] winningCombo7;
    private Integer[] winningCombo8;

    private List<Integer[]> _winningComboList;

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

        _winningComboList = new ArrayList<>();

        winningCombo1 = new Integer[]{0,0,1,0,2,0}; // top row
        winningCombo2 = new Integer[]{0,1,1,1,2,1}; // middle row
        winningCombo3 = new Integer[]{0,2,1,2,2,2}; // bot row
        winningCombo4 = new Integer[]{0,0,0,1,0,2}; // first column
        winningCombo5 = new Integer[]{1,0,1,1,1,2}; // mid column
        winningCombo6 = new Integer[]{2,0,2,1,2,2}; // last column
        winningCombo7 = new Integer[]{0,0,1,1,2,2}; // diag 1
        winningCombo8 = new Integer[]{2,0,1,1,0,2}; // diag 2

        _winningComboList.add(winningCombo1);
        _winningComboList.add(winningCombo2);
        _winningComboList.add(winningCombo3);
        _winningComboList.add(winningCombo4);
        _winningComboList.add(winningCombo5);
        _winningComboList.add(winningCombo6);
        _winningComboList.add(winningCombo7);
        _winningComboList.add(winningCombo8);

        resetGame();
    }

    private void resetGame(){
        for (Spot s : _board) {
            s.clearSpot();
        }
        _nextToPlay = Player.WHITE;
        _gameWon = false;
        _stalemate = false;
        _counter = 1;
        _message.setText("Welcome to TicTacToe. White to play");
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

        spot.setSpotColor(player_color);
        spot.toggleSpot();
        spot.unhighlightSpot();


        for (x=0; x<_winningComboList.size(); x++) {
            Integer[] temp = _winningComboList.get(x);
            _gameWon = checkGameStatus(temp[0],temp[1],temp[2],
                    temp[3], temp[4], temp[5]);
            if (_gameWon) {
                break;
            }
        }
        for (x=0; x<_board.getSpotWidth(); x++) {
            for(y=0; y<_board.getSpotHeight(); y++) {
                JSpot tempSpot = (JSpot) _board.getSpotAt(x,y);
                if (!tempSpot.isEmpty()) {
                    _counter += 1;
                }
            }
        }
        if (_counter == 9 & !_gameWon) {
            _stalemate = true;
        }

        if (!spot.isEmpty()) {
            _message.setText(next_player_name + " to play.");
        }

        if (_gameWon) {
            _message.setText(player_name + " won the game!!!");
        }

        if (_stalemate) {
            _message.setText("Draw!!!");
        }

        _counter = 0;


    }

    public void spotEntered(Spot spot) {
        if (_gameWon || !spot.isEmpty()) {
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

    private boolean checkGameStatus(int x1,int y1, int x2, int y2, int x3, int y3) {
        if (_board.getSpotAt(x1,y1).isEmpty() || _board.getSpotAt(x2,y2).isEmpty() || _board.getSpotAt(x3,y3).isEmpty()) {
            // nobody has won the game because these spots will be empty
            return false;
        }
        if (!_board.getSpotAt(x1,y1).isEmpty() && !_board.getSpotAt(x2,y2).isEmpty() && !_board.getSpotAt(x3,y3).isEmpty()) {
            // game may be over
            if (_board.getSpotAt(x1, y1).getSpotColor() == _board.getSpotAt(x2, y2).getSpotColor() &&
            _board.getSpotAt(x1, y1).getSpotColor() == _board.getSpotAt(x3,y3).getSpotColor() &&
            _board.getSpotAt(x2,y2).getSpotColor() == _board.getSpotAt(x3, y3).getSpotColor()) {
                //game should be over
                return true;
            }
        }
        return false;
    }

}
