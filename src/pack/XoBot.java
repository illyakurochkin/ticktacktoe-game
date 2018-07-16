package pack;

public abstract class XoBot {
	protected Values value = /* new Random().nextBoolean() ? */ Values.O;// : Values.X;
	protected XoGame game;

	protected XoBot(XoGame game) {
		this.game = game;
	}

	public final void start() {
		nextMove();
	}

	public abstract void nextMove();
}
