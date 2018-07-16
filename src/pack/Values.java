package pack;

public enum Values {
	X, O;

	public Values getOppositeValues() {
		return this == X ? O : X;
	}
}
