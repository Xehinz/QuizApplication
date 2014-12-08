package persistency;

public enum StorageStrategy {
	TEKST, DATABASE;

	public String toString() {
		switch (this) {
		case TEKST:
			return "Tekst";
		case DATABASE:
			return "Database";
		default:
			return "";
		}
	}
}
