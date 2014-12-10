package persistency;

/**
 * Abstracte klasse om het wegschrijven of inlezen van 
 * databaseformaat
 *
 * @author Adriaan Kuipers
 * @version 10/12/2014
 *
 */

import java.io.IOException;

abstract class DBOpdrachtLeesSchrijf extends DBTemplate {

	DBOpdrachtLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected abstract String getLeesQuery();

	@Override
	protected abstract <T> T maakObject(Object[] rij);

	@Override
	protected abstract <T> String getSchrijfStatement(T object) throws IOException;

	@Override
	protected abstract String getDeleteStatement();

}
