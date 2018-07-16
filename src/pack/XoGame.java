package pack;

import java.awt.Point;
import java.util.Arrays;

public class XoGame {
	private Values[][] desk = new Values[3][3];
	private XoGameFrame frame;

	// as default starts x-player
	private Values currentPlayer = Values.X;

	public Values getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void updateGraphics() {
		frame.update();
		frame.updateInfo();
	}

	public Values[][] getDesk() {
		return desk;
	}

	public void start() {
		frame = new XoGameFrame(this);
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(5);
						frame.update();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public GameCondition next(Point point) {
		GameCondition gameCondition = getGameCondition();
		if (gameCondition == GameCondition.CONTINUE) {
			nextStap(point);
		}
		return gameCondition;
	}

	public GameCondition getGameCondition() {
		Values winner = checkWinner();
		return winner == null ? (Arrays.stream(desk).flatMap(Arrays::stream).filter(value -> value == null).count() == 0
				? GameCondition.NOBODY_WIN
				: GameCondition.CONTINUE) : winner == Values.X ? GameCondition.X_WIN : GameCondition.O_WIN;
	}

	private Values checkWinner() {
		for (Values temp : Values.values()) {

			// Check lines
			for (int y = 0; y < 3; y++) {
				if (Arrays.equals(desk[y], new Values[] { temp, temp, temp })) {
					return temp;
				}
			}

			// Check columns
			for (int x = 0; x < 3; x++) {
				if (desk[0][x] == desk[1][x] && desk[1][x] == desk[2][x] && desk[2][x] == temp) {
					return temp;
				}
			}

			// Check diagonals
			if (desk[0][0] == desk[1][1] && desk[1][1] == desk[2][2] && desk[2][2] == temp
					|| desk[2][0] == desk[1][1] && desk[1][1] == desk[0][2] && desk[1][1] == temp) {
				return temp;
			}
		}

		return null;
	}

	public /* boolean */void nextStap(Point point) {
		// boolean result = desk[point.y][point.x] == null; if (result) {
		if (desk[point.y][point.x] == null) {
			desk[point.y][point.x] = currentPlayer;
			currentPlayer = currentPlayer == Values.X ? Values.O : Values.X;
		}
		// } return result;
	}
}
