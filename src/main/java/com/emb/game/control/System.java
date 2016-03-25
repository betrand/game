package com.emb.game.control;

import com.emb.game.entity.Cell;
import com.emb.game.util.JsfUtil;
import com.emb.game.util.SystemEvent;
import java.io.Serializable;
import java.util.List;
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
            if (count > 50) {
                break;
            }
        }
        return board.getCell(cellId);
    }

    private int getRandomInt(Board board) {
        //LOG.info("getRandomInt");
        int num = board.getNoOfCells();
        if (num != 0) {
            Cell winingCell = playToWin(board);
            if (winingCell != null) {
                return winingCell.getId();
            }
            return ThreadLocalRandom.current().nextInt(num);
        }
        return 0;
    }

    /*
     private int getRandomInt(Board board) {
     int num = board.getNoOfCells();
     if (num != 0) {
     return ThreadLocalRandom.current().nextInt(num);
     }
     return 0;
     }*/
    private Cell playToWin(Board board) {
        //LOG.info("playToWin");
        Cell winingCell = null;
        if (board.getMarkedCells().size() >= 2) {
            List<Cell> systemCells = board.getCellsByMark(new StringBuilder()
                    .append(JsfUtil.SYSTEM).append(".png").toString());
            //LOG.info("systemCells", systemCells.size());
            int noOfCells = systemCells.size();
            if (noOfCells > 1) {
                Cell cell1;
                Cell cell2;

                if (noOfCells > 1 && noOfCells == 2) {
                    //LOG.info("noOfCells == 2");
                    cell1 = systemCells.get(0);
                    cell2 = systemCells.get(1);

                    if (board.nearRowWin(cell1, cell2)) {
                        //LOG.info("board.nearRowWin", board.nearRowWin(cell1, cell2));
                        winingCell = board.nearRowWinCell(cell1, cell2);
                        //LOG.info("board.nearRowWinCell", board.nearRowWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }

                    if (board.nearColWin(cell1, cell2)) {
                        //LOG.info("board.nearColWin", board.nearColWin(cell1, cell2));
                        winingCell = board.nearColWinCell(cell1, cell2);
                        //LOG.info("board.nearColWinCell", board.nearColWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }

                    if (board.nearDiagonalWin(cell1, cell2)) {
                        //LOG.info("board.nearDiagonalWin", board.nearDiagonalWin(cell1, cell2));
                        winingCell = board.nearDiagonalWinCell(cell1, cell2);
                        //LOG.info("board.nearDiagonalWinCell", board.nearDiagonalWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }
                }

                if (noOfCells > 1 && noOfCells == 3) {
                    //LOG.info("noOfCells == 3");
                    cell1 = systemCells.get(1);
                    cell2 = systemCells.get(2);
                    if (board.nearRowWin(cell1, cell2)) {
                        //LOG.info("board.nearRowWin", board.nearRowWin(cell1, cell2));
                        winingCell = board.nearRowWinCell(cell1, cell2);
                        //LOG.info("board.nearRowWinCell", board.nearRowWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }
                    if (board.nearColWin(cell1, cell2)) {
                        //LOG.info("board.nearColWin", board.nearColWin(cell1, cell2));
                        winingCell = board.nearColWinCell(cell1, cell2);
                        //LOG.info("board.nearColWinCell", board.nearColWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }
                    if (board.nearDiagonalWin(cell1, cell2)) {
                        //LOG.info("board.nearDiagonalWin", board.nearDiagonalWin(cell1, cell2));
                        winingCell = board.nearDiagonalWinCell(cell1, cell2);
                        //LOG.info("board.nearDiagonalWinCell", board.nearDiagonalWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }
                }
                // may chain all execution sequences 1, 2, 3,
                if (noOfCells > 1 && noOfCells == 4) {
                    //LOG.info("noOfCells == 4");
                    cell1 = systemCells.get(2);
                    cell2 = systemCells.get(3);
                    if (board.nearRowWin(cell1, cell2)) {
                        //LOG.info("board.nearRowWin", board.nearRowWin(cell1, cell2));
                        winingCell = board.nearRowWinCell(cell1, cell2);
                        //LOG.info("board.nearRowWinCell", board.nearRowWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }

                    if (board.nearColWin(cell1, cell2)) {
                        //LOG.info("board.nearColWin", board.nearColWin(cell1, cell2));
                        winingCell = board.nearColWinCell(cell1, cell2);
                        //LOG.info("board.nearColWinCell", board.nearColWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }

                    if (board.nearDiagonalWin(cell1, cell2)) {
                        //LOG.info("board.nearDiagonalWin", board.nearDiagonalWin(cell1, cell2));
                        winingCell = board.nearDiagonalWinCell(cell1, cell2);
                        //LOG.info("board.nearDiagonalWinCell", board.nearDiagonalWinCell(cell1, cell2));
                        if (winingCell != null) {
                            return winingCell;
                        }
                    }
                }
            } else {
                //LOG.info("board.prepareToWin", board.prepareToWin(systemCells.get(0)));
                return board.prepareToWin(systemCells.get(0));
            }
        }
        return winingCell;
    }

}
