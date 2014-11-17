package controller;

import persistency.DBHandler;

/**
 * 
 * @author Johan Boogers
 *
 */

public class BeheerLeerlingController {

	private DBHandler aDBHandler;
	
	public BeheerLeerlingController(DBHandler aHandler) {
		this.setDBHandler(aHandler);
	}

	public DBHandler getDBHandler() {
		return aDBHandler;
	}

	private void setDBHandler(DBHandler aDBHandler) {
		this.aDBHandler = aDBHandler;
	}

}
