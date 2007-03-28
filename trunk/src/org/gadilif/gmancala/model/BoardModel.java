package org.gadilif.gmancala.model;

import java.util.ArrayList;
import java.util.List;

import org.gadilif.gmancala.strategies.IPlayerStrategy.PlayerType;
import org.gadilif.gmancala.view.listeners.ICellChangedListener;

public class BoardModel {

	private final static int LEFT_HOLE = 0;
	private final static int RIGHT_HOLE = 7;
	private final static int INITIAL_CELL_COUNT = 4;
	int[] cells = new int[14];
	private boolean initialized;
	private List<ICellChangedListener> cellChangedListeners;
	
	public void init() {
		for (int i=0;i<14;i++) {
			cells[i] = INITIAL_CELL_COUNT;
		}
		cells[LEFT_HOLE] = 0;
		cells[RIGHT_HOLE] = 0;
		cellChangedListeners = new ArrayList<ICellChangedListener>();
		this.initialized = true;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	public int getCellValue(final int i) {
		return cells[i];
	}

	private boolean isHole(final int cell) {
		return ((cell == LEFT_HOLE) || (cell == RIGHT_HOLE));
	}
	
	private void capture(final int cell, final int hole) {
		if (cells[14-cell] > 0) {
			setCellValue(hole, cells[hole]+cells[14-cell]+1);
			setCellValue(cell,0);
			setCellValue(14-cell,0);
		}
	}
	public boolean play(final int start) {
		int cell = start;
		if (cells[start] == 0) {
			return true;
		}
		while (cells[start] > 0) {
			cell++;
			cell %= 14;
			if ((start < 7) && (cell == 0)) {
				continue;
			}
			if ((start > 7) && (cell == 7)) {
				continue;
			}
			incrementCell(cell);
			decrementCell(start);
		}
		cell %= 14;
		if (!isHole(cell) && (cells[cell] == 1)) {
			if (start < 7) {
				if (cell < 7) {
					capture(cell, RIGHT_HOLE);
				}
			}
			else { //start > 7
				if (cell > 7) {
					capture(cell, LEFT_HOLE);
				} 
			}
				
		}
		return isHole(cell);
	}
	
	
	private void incrementCell(final int i) {
		setCellValue(i, cells[i]+1);
		
	}
	
	private void decrementCell(final int i) {
		setCellValue(i, cells[i]-1);
	}
	
	
	public boolean isGameOver() {
		return (getRowSum(1,7) == 0) || (getRowSum(8,14) == 0);
		
	}

	public void setCellValue(final int cell, final int value) {
		cells[cell] = value;
		for (ICellChangedListener listener : cellChangedListeners) {
			listener.cellChanged(cell, value);
		}
	}
	
	
	public int getRightHoleValue() {
		return cells[RIGHT_HOLE];
	}

	public int getLeftHoleValue() {
		return cells[LEFT_HOLE];
	}

	public boolean canPlay(final int i) {
		return cells[i] > 0;
	}
	
	public int getRightHole() {
		return RIGHT_HOLE;
	}
	
	public int getLeftHole() {
		return LEFT_HOLE;
	}

	public void addCellChangedListener(final ICellChangedListener listener) {
		cellChangedListeners.add(listener);
		
	}

	private int getRowSum(final int start, final int end) {
		int sum = 0;
		for (int i=start;i<end;i++) {
			sum += cells[i];
		}
		return sum;
	}
	
	public int getPlayer1Score() {
		return getRowSum(0,7);
	}
	
	public int getPlayer2Score() {
		return getRowSum(7, 14);
	}
	public PlayerType getWinner() {
		return (getPlayer1Score() > getPlayer2Score())?PlayerType.ONE:PlayerType.TWO;
	}

}
