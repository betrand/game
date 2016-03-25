package com.emb.game.control;

import com.emb.game.entity.Cell;
import com.emb.game.entity.Player;
import com.emb.game.util.JsfUtil;
import com.emb.game.util.SystemEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author bu_000
 */
@ViewScoped
@Named("game")
public class Game implements Serializable {

    private String state;
    private Board board;
    private Player player;
    private Map<String, Player> players;
    @Inject
    @SystemEvent
    Event<String> systemEvent;

    @PostConstruct
    public void init() {
        player = null;
        state = null;
        board = new Board();
        setupPlayers();
    }

    private void setupPlayers() {
        players = new HashMap<>();
        players.put(JsfUtil.SYSTEM, new Player(JsfUtil.SYSTEM, JsfUtil.SYSTEM_PLAYER));
        players.put(JsfUtil.HUMAN, new Player(JsfUtil.HUMAN, JsfUtil.HUMAN_PLAYER));
    }

    public void start() {
        board.setup();
        state = JsfUtil.PLAYING;
        player = players.get(JsfUtil.getRequestParameter(JsfUtil.PLAYER));

        if (player == null) {
            JsfUtil.addWarningMessage("!Sorry Player not found ");
            return;
        }

        if (!player.getMark().equals(JsfUtil.SYSTEM)
                && !player.getMark().equals(JsfUtil.HUMAN)) {
            JsfUtil.addWarningMessage(new StringBuilder(JsfUtil.SYSTEM_PLAYER)
                    .append(" or ").append(JsfUtil.HUMAN_PLAYER).toString());
        } else {
            player.incrementStart();
            if (player.getMark().equals(JsfUtil.SYSTEM)) {
                systemEvent.fire(JsfUtil.DO_SOMETHING);
            }
        }
    }

    protected void markCell(Cell cell) {
        if (state != null && state.equals(JsfUtil.WIN)) {
            return;
        }

        if (board.appendMark(cell.getRow(), cell.getCol(),
                new StringBuilder().append(player.getMark()).append(".png").toString())) {
        }
        updateScreen();
        switchPlayer();
    }

    private void updateScreen() {
        if (board.isGameOver()) {
            state = JsfUtil.WIN;
            announceTheWinner();
            player.incrementScore();
            return;
        } else if (!board.isGameOver() && board.cellIsNotAvailable()) {
            state = JsfUtil.DRAW;
        } else {
            state = JsfUtil.PLAYING;
        }
        //board.displayBoard();
    }

    private void announceTheWinner() {
        //if diagonal Marks Are Equal 1,1 must be in a cell of the winner
        if (board.diagonalMarksAreEqual()) {
            JsfUtil.addSuccessMessage(new StringBuilder("GAME OVER!!!  ")
                    .append(player.getName()).append(" WON").toString());
            return;
        }
        if (board.rowMarksAreEqual()) {
            //if row Marks Are Equal 0,1, 1,1 and 2,1 must be cells of the winner
            JsfUtil.addSuccessMessage(new StringBuilder("GAME OVER!!!  ")
                    .append(player.getName()).append(" WON").toString());
            return;
        }
        if (board.columnMarksAreEqual()) {
            //if column Marks Are Equal 1,0, 1,1 and 1,2 must be in cells of the winner
            JsfUtil.addSuccessMessage(new StringBuilder("GAME OVER!!!  ")
                    .append(player.getName()).append(" WON").toString());
        }
    }

    private void switchPlayer() {
        if (player != null && player.getMark().equals(JsfUtil.HUMAN)) {
            player = players.get(JsfUtil.SYSTEM);
        } else {
            player = players.get(JsfUtil.HUMAN);
        }
    }

    public void play() {
        if (player == null) {
            JsfUtil.addErrorMessage(JsfUtil.START);
            return;
        }

        markCell(board.getCell(getCellId("cellId")));
        if (!state.equals(JsfUtil.WIN) || !state.equals(JsfUtil.DRAW)) {
            systemEvent.fire(JsfUtil.DO_SOMETHING);
        }
    }

    private int getCellId(String param) throws NumberFormatException {
        String id = JsfUtil.getRequestParameter(param);
        if (JsfUtil.Empty(id)) {
            return 0;
        } else {
            return Integer.parseInt(id);
        }
    }

    public String getState() {
        if (state == null) {
            state = JsfUtil.START;
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Board getBoard() {
        return board;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

}
