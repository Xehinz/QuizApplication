package persistency;

public enum StorageStrategy {
	TEKST, CSV, DATABASE;

	public String toString() {
		switch (this) {
		case TEKST:
			return "Tekst";
		case CSV:
			return "CSV";
		case DATABASE:
			return "Database";
		default:
			return "";
		}
	}
}
