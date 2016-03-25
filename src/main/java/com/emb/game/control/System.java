package com.emb.game.control;

import com.emb.game.entity.Cell;
import com.emb.game.util.JsfUtil;
import com.emb.game.util.SystemEvent;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author bu_000
 */
public class System implements Serializable {

    @Inject
    private Game game;

    public void doSomeThing(@Observes @SystemEvent String event) {
        if (game.getState() != null && game.getState().equals(JsfUtil.WIN)) {
            return;
        }
        Cell cell = getRandomCellFromScreen();
        game.markCell(cell);
    }

    //TODO implement System algorithm for efficient/winning when system play here
    // E.G. Checking the best place to place Marks for a win
    public Cell getRandomCellFromScreen() {
        Board board = game.getBoard();
        int cellId = 1;
        int count = 0;

        cellId = getRandomInt(board);

        while (!board.notMarked(cellId)) {
            count++;
            cellId = getRandomInt(board);
            if (count > 25) {
                break;
            }
        }
        return board.getCell(cellId);
    }

    private int getRandomInt(Board board) {
        int num = board.getNoOfCells();
        if (num != 0) {
            return ThreadLocalRandom.current().nextInt(num);
        }
        return 0;
    }

}
