package util;

public class IDBeheerder {
	private int hoogsteID;

	public IDBeheerder() {
		hoogsteID = 0;
	}

	public int kenIDToe() {
		return ++hoogsteID;
	}

	public void reset() {
		hoogsteID = 0;
	}
}
