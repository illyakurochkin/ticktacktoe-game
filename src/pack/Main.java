package pack;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		XoGame game = new XoGame();
		XoBot bot = new CleverXoBot(game);
		game.start();
		bot.start();
	}

}
