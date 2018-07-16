package pack;

import java.awt.Point;
import java.util.Random;

public class RandomXoBot extends XoBot {
	private static final Random random = new Random();

	public RandomXoBot(XoGame game) {
		super(game);
	}

	@Override
	public void nextMove() {
		new Thread() {
			public void run() {
				while (value != game.getCurrentPlayer()) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Point point = new Point(random.nextInt(3), random.nextInt(3));
				while (game.getDesk()[point.y][point.x] != null) {
					point = new Point(random.nextInt(3), random.nextInt(3));
				}
				if (game.getGameCondition() == GameCondition.CONTINUE) {
					game.next(point);
					run();
				}
			}
		}.start();
	}
}
