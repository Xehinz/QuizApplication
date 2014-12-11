package persistency;

/**
 * Klasse om Reproductie objecten weg te schrijven of in te lezen in
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 11/12/2014
 *
 */

import java.io.IOException;

public class DBReproductieLeesSchrijf extends DBTemplate {

	DBReproductieLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM reproductie";
	}

	@Override
	protected <T> T maakObject(Object[] rij) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T> String getSchrijfStatement(T object) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE reproductie";
	}

}
