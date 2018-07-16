package pack;

import java.awt.Point;

public class CleverXoBotBeta extends XoBot {

	// private Values[] desk = new Values[9];
	private int counter = 1;

	public CleverXoBotBeta(XoGame game) {
		super(game);
		// for (int y = 0, counter = 0; y < 3; y++) {
		// for (int x = 0; x < 3; x++, counter++) {
		// desk[counter] = game.getDesk()[y][x] == null ? null : game.getDesk()[y][x];
		// }
		// }

	}

	// private Set<Integer> emptyValues() {
	// return Arrays.stream(desk).filter(o -> o instanceof Integer).map(o ->
	// (Integer) o).collect(Collectors.toSet());
	// }

	// private int getMoveCount() {
	// return (int) Arrays.stream(game.getDesk()).flatMap(Arrays::stream).filter(v
	// -> v != null).count();
	// }

	@Override
	public void nextMove() {
		new Thread() {
			public void run() {
				while (game.getCurrentPlayer() != value) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (value == Values.X) {
					switch (counter) {
					case 1:
						move1();
						break;
					case 2:
						move2();
						break;
					case 3:
						move3();
						break;
					case 4:
						move4();
					}
				}
				game.updateGraphics();
				counter++;
				run();
			}
		}.start();
	}

	private void move1() {
		game.next(new Point(0, 2));
	}

	private void move2() {
		if (game.getDesk()[1][1] == Values.O) {
			game.next(new Point(2, 0));
		} else if (game.getDesk()[2][2] == null) {
			if (game.getDesk()[2][1] != Values.O && game.getDesk()[0][1] != Values.O) {
				game.next(new Point(2, 2));
			} else {
				game.next(new Point(0, 0));
			}
		} else {
			game.next(new Point(0, 0));
		}
	}

	private void move3() {
		if (game.getDesk()[0][0] == Values.X && game.getDesk()[1][0] == null) {
			game.next(new Point(0, 1));
		} else if (game.getDesk()[2][2] == Values.X && game.getDesk()[2][1] == null) {
			game.next(new Point(1, 2));
		} else {
			if (game.getDesk()[1][1] == Values.O) {
				if (game.getDesk()[0][0] == Values.O) {
					game.next(new Point(2, 2));
				} else if (game.getDesk()[2][2] == Values.O) {
					game.next(new Point(0, 0));
				}
			} else {
				if (game.getDesk()[2][2] == Values.X) {
					if (game.getDesk()[0][0] == null && game.getDesk()[1][0] == null) {
						game.next(new Point(0, 0));
					} else {
						game.next(new Point(2, 0));
					}
				} else {
					if (game.getDesk()[2][1] == null && game.getDesk()[2][2] == null) {
						game.next(new Point(2, 2));
					} else {
						game.next(new Point(2, 0));
					}
				}
			}
		}
	}

	private void move4() {
		if (game.getDesk()[1][1] == Values.O && (game.getDesk()[0][1] == Values.O || game.getDesk()[1][2] == Values.O
				|| game.getDesk()[2][1] == Values.O || game.getDesk()[1][0] == Values.O)) {
			if (game.getDesk()[0][1] == Values.O) {
				game.next(new Point(1, 2));
			} else if (game.getDesk()[1][2] == Values.O) {
				game.next(new Point(0, 1));
			} else if (game.getDesk()[2][1] == Values.O) {
				game.next(new Point(1, 0));
			} else if (game.getDesk()[1][0] == Values.O) {
				game.next(new Point(2, 1));
			}
		} else if (game.getDesk()[1][1] == null) {
			game.next(new Point(1, 1));
		} else {
			if (game.getDesk()[0][0] == Values.X && game.getDesk()[0][2] == Values.X) {
				if (game.getDesk()[1][0] == null) {
					game.next(new Point(0, 1));
				} else {
					game.next(new Point(1, 0));
				}
			} else if (game.getDesk()[0][0] == Values.X && game.getDesk()[2][2] == Values.X) {
				if (game.getDesk()[1][0] == null) {
					game.next(new Point(0, 1));
				} else {
					game.next(new Point(1, 2));
				}
			} else if (game.getDesk()[2][2] == Values.X && game.getDesk()[0][2] == Values.X) {
				if (game.getDesk()[2][1] == null) {
					game.next(new Point(1, 2));
				} else {
					game.next(new Point(2, 1));
				}
			}
		}
	}

}
