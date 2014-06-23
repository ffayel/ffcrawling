package com.ff.dao.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ff.dao.exception.FFCrawlSqlException;
import com.ff.tool.MyProperties;

public final class MyConnecteur {
	  private Connection _cnx;
	  private static ConnecteurConfig _configDefault = null;
	  private String _class = null;
	  private String _url = null;
	  private String _user = null;
	  private String _pass = null;

	  public MyConnecteur() {
		  if (null == _configDefault) {
			  _configDefault = new ConnecteurConfig(
					  MyProperties.getString("db.class"),
					  MyProperties.getString("db.path"),
					  MyProperties.getString("db.user"),
					  MyProperties.getString("db.pass")
			  );
		  }
		  loadConfig(_configDefault);
	  }

	  public MyConnecteur(final ConnecteurConfig config) {
		  loadConfig(config);
	  }

	  private final void loadConfig(final ConnecteurConfig config) {
		  	_class = config.getClasse();
		  	_url = config.getUrl();
		  	_user = config.getUser();
		  	_pass = config.getPass();
		    try { Class.forName(_class);
		    } catch (ClassNotFoundException e) { }
	  }

	  public Connection getConnection() throws SQLException{
		  try {
			if (_cnx == null || _cnx.isClosed()) {
				  _cnx = DriverManager.getConnection(_url, _user, _pass);
			  }
			} catch (SQLException e) {
				MySqlLog.log("DriverManager.getConnection failed ("+ _url+")\n"+ e.getMessage());
				throw new FFCrawlSqlException("DriverManager.getConnection failed");
			}
		    return _cnx;
	  }

	  private final class ConnecteurConfig {
			private String _classe, _url, _user, _pass;
			public ConnecteurConfig(final String classe, final String url, final String user, final String pass) {
				_classe = classe; _url = url; _user = user; _pass = pass; }
			public final String getPass() { return _pass; }
			public final String getUrl() { return _url; }
			public final String getUser() { return _user; }
			public final String getClasse() { return _classe; }

		}


}
