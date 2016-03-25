package com.emb.game.control;

import com.emb.game.entity.Cell;
import com.emb.game.util.LOG;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bu_000
 */
public class Board implements Serializable {

    private Cell[][] board;
    private static final int rows = 3;
    private static final int cols = 3;

    public Board() {
        setup();
    }

    /**
     * Setup Board
     */
    public void setup() {
        int id = 0;
        board = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Initialize each cell object of the grid
                board[i][j] = new Cell(id, i, j, null);
                id++;
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public void displayBoard() {
        LOG.info("-----------------------------------------------");
        for (int i = 0; i < rows; i++) {
            LOG.info(" ");
            for (int j = 0; j < cols; j++) {
                LOG.info(new StringBuilder()
                        .append(board[i][j])
                        .append(" | ").toString());
            }
        }
    }

    /**
     *
     * @return state of cell availability
     */
    public boolean cellIsNotAvailable() {
        boolean result = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((board[i][j]).getMark() == null) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     *
     * @param row
     * @param col
     * @param mark
     * @return false if cell is already marked Append mark to board cell if cell
     * is not already marked and return true
     */
    public boolean appendMark(int row, int col, String mark) {
        // Ensure we have valid row and column sizes
        if ((row >= 0) && (row < rows)) {
            if ((col >= 0) && (col < cols)) {
                if (notMarked(board[row][col])) {
                    board[row][col] = new Cell((board[row][col]).getId(), row, col, mark);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean marksAreEqual(Cell cell1, Cell cell2, Cell cell3) {
        return ((cell1.getMark() != null)
                && (cell1.getMark().equals(cell2.getMark()))
                && (cell2.getMark().equals(cell3.getMark())));
    }

    public boolean rowMarksAreEqual() {
        for (int i = 0; i < rows; i++) {
            if (marksAreEqual(board[i][0], board[i][1], board[i][2]) == true) {
                return true;
            }
        }
        return false;
    }

    public boolean columnMarksAreEqual() {
        for (int i = 0; i < cols; i++) {
            if (marksAreEqual(board[0][i], board[1][i], board[2][i]) == true) {
                return true;
            }
        }
        return false;
    }

    public boolean diagonalMarksAreEqual() {
        return ((marksAreEqual(board[0][0], board[1][1], board[2][2]) == true)
                || (marksAreEqual(board[0][2], board[1][1], board[2][0]) == true));
    }

    /**
     *
     * @return a win state
     */
    public boolean isGameOver() {
        return (rowMarksAreEqual() || columnMarksAreEqual() || diagonalMarksAreEqual());
    }

    public boolean notMarked(Cell cell) {
        return cell.getMark() == null;
    }

    public boolean notMarked(int id) {
        return notMarked(getCell(id));
    }

    public List<Cell> getUnMarkedCells() {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (notMarked(board[i][j])) {
                    list.add(board[i][j]);
                }
            }
        }
        return list;
    }

    public List<Cell> getMarkedCells() {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!notMarked(board[i][j])) {
                    list.add(board[i][j]);
                }
            }
        }
        return list;
    }

    public String getCellMark(int id) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((board[i][j]).getId() == id) {
                    return (board[i][j]).getMark();
                }
            }
        }
        return null;
    }

    public Cell getCell(int id) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((board[i][j]).getId() == id) {
                    return (board[i][j]);
                }
            }
        }
        return null;
    }

    public List<Cell> getCellsByMark(String mark) {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((board[i][j]).getMark() != null) {
                    if ((board[i][j]).getMark().equals(mark)) {
                        list.add(board[i][j]);
                    }
                }
            }
        }
        return list;
    }

    public List<Cell> getItems() {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                list.add(board[i][j]);
            }
        }
        return list;
    }

    public int getNoOfMarkedCells() {
        return getMarkedCells().size();
    }

    public int getNoOfUnMarkedCells() {
        return getUnMarkedCells().size();
    }

    public int getNoOfCells() {
        return rows * cols;
    }

}
