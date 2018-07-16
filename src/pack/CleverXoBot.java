package pack;

import java.awt.Point;

public class CleverXoBot extends XoBot {

	protected CleverXoBot(XoGame game) {
		super(game);
		value = Values.X;
	}

	@Override
	public void nextMove() {
		new Thread(() -> {
			while (game.getGameCondition() == GameCondition.CONTINUE) {
				System.out.println("next");

				while (game.getCurrentPlayer() != value) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (containsLine()) {

				} else {
					if (game.getDesk()[0][0] == null) {
						game.nextStap(new Point(0, 0));
					} else if (game.getDesk()[2][2] == null) {
						game.nextStap(new Point(2, 2));
					} else if (game.getDesk()[0][2] == null) {
						game.nextStap(new Point(2, 0));
					} else if (game.getDesk()[2][0] == null) {
						game.nextStap(new Point(0, 2));
					} else if (game.getDesk()[0][1] == null) {
						game.nextStap(new Point(1, 0));
					} else if (game.getDesk()[1][0] == null) {
						game.nextStap(new Point(0, 1));
					} else if (game.getDesk()[1][2] == null) {
						game.nextStap(new Point(2, 1));
					} else if (game.getDesk()[2][1] == null) {
						game.nextStap(new Point(1, 2));
					} else {
						game.nextStap(new Point(1, 1));
					}
				}
			}
			// System.out.println("over");
		}).start();

	}

	private boolean containsLine() {
		return isLine(value, new Point(0, 0), new Point(0, 1), new Point(0, 2))
				|| isLine(value, new Point(1, 0), new Point(1, 1), new Point(1, 2))
				|| isLine(value, new Point(2, 0), new Point(2, 1), new Point(2, 2))
				|| isLine(value, new Point(0, 0), new Point(1, 0), new Point(2, 0))
				|| isLine(value, new Point(0, 1), new Point(1, 1), new Point(2, 1))
				|| isLine(value, new Point(0, 2), new Point(1, 2), new Point(2, 2))
				|| isLine(value, new Point(0, 0), new Point(1, 1), new Point(2, 2))
				|| isLine(value, new Point(0, 2), new Point(1, 1), new Point(2, 0))
				|| isLine(value.getOppositeValues(), new Point(0, 0), new Point(0, 1), new Point(0, 2))
				|| isLine(value.getOppositeValues(), new Point(1, 0), new Point(1, 1), new Point(1, 2))
				|| isLine(value.getOppositeValues(), new Point(2, 0), new Point(2, 1), new Point(2, 2))
				|| isLine(value.getOppositeValues(), new Point(0, 0), new Point(1, 0), new Point(2, 0))
				|| isLine(value.getOppositeValues(), new Point(0, 1), new Point(1, 1), new Point(2, 1))
				|| isLine(value.getOppositeValues(), new Point(0, 2), new Point(1, 2), new Point(2, 2))
				|| isLine(value.getOppositeValues(), new Point(0, 0), new Point(1, 1), new Point(2, 2))
				|| isLine(value.getOppositeValues(), new Point(0, 2), new Point(1, 1), new Point(2, 0));
	}

	private boolean isLine(Values value, Point... points) {
		if (game.getDesk()[points[0].y][points[0].x] == game.getDesk()[points[1].y][points[1].x]
				&& game.getDesk()[points[0].y][points[0].x] == value
				&& game.getDesk()[points[2].y][points[2].x] == null) {
			game.nextStap(points[2]);
			return true;
		} else if (game.getDesk()[points[0].y][points[0].x] == game.getDesk()[points[2].y][points[2].x]
				&& game.getDesk()[points[0].y][points[0].x] == value
				&& game.getDesk()[points[1].y][points[1].x] == null) {
			game.nextStap(points[1]);
			return true;
		} else if (game.getDesk()[points[1].y][points[1].x] == game.getDesk()[points[2].y][points[2].x]
				&& game.getDesk()[points[1].y][points[1].x] == value
				&& game.getDesk()[points[0].y][points[0].x] == null) {
			game.nextStap(points[0]);
			return true;
		} else {
			return false;
		}
	}

}
