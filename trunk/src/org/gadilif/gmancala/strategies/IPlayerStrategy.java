package org.gadilif.gmancala.strategies;

import org.gadilif.gmancala.controller.BoardController.PlayResult;
import org.gadilif.gmancala.model.PlayerType;

public interface IPlayerStrategy {
	int play();
	PlayerType getPlayer();
}
