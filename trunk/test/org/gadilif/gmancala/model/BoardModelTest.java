package org.gadilif.gmancala.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.gadilif.gmancala.view.BoardTextView;
import org.junit.Before;
import org.junit.Test;

public class BoardModelTest {
	
	private BoardModel board;
	private BoardTextView boardView;
	
	@Before
	public void init() {
		board = new BoardModel();
		board.init();
		boardView = new BoardTextView(board, System.out);
	}
	
	@Test
	public void boardInit() {
		assertTrue("board should be initialized now", board.isInitialized());
	}
	
	@Test
	public void getCellValue() {
		assertEquals(4, board.getCellValue(1));
		//boardView.draw();
	}
	
	@Test
	public void getCellAfterPlay() {
		board.play(4);
		assertEquals(0, board.getCellValue(4));
		assertEquals(1, board.getRightHoleValue());
		//boardView.draw();
	}
	
	@Test
	public void getCellAfterPlay2() {
		board.play(11);
		assertEquals(0, board.getCellValue(11));
		assertEquals(1, board.getLeftHoleValue());
		//boardView.draw();
	}
	
	@Test
	public void consecutivePlays() {
		board.play(4);
		assertEquals(1, board.getRightHoleValue());
		assertFalse(board.canPlay(4));
		board.play(4);
		assertEquals(1, board.getRightHoleValue());
		board.play(5);
		assertEquals(2, board.getRightHoleValue());
		//boardView.draw();
	}
	
	@Test
	public void capture() {
		board.play(6);
		board.play(12);
		board.play(1);
		assertEquals(7, board.getRightHoleValue());
		//boardView.draw();
	}

	@Test
	public void capture2() {
		board.play(13);
		board.play(6);
		board.play(8);
		assertEquals(7, board.getLeftHoleValue());
		boardView.draw();
	}
}