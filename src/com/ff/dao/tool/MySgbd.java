package com.ff.dao.tool;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.rowset.serial.SerialBlob;

import com.ff.tool.MyProperties;

public final class MySgbd{

	private Connection connection 			= null;
	private MyConnecteur connector 			= new MyConnecteur();

	private static long nbQuery = 0;

	public static enum SETTER_TYPE {INT, STRING, TIMESTAMP, LONG, BLOB, DOUBLE, BOOLEAN}

	public MySgbd() {
		try{ connection = connector.getConnection();
		} catch (SQLException e) { }

		if (MyProperties.getBoolean("debug.sql")){
			++nbQuery;
		}
	}

	private void createConnection() {
		try{ connection = connector.getConnection();
		} catch (SQLException e) { }
	}

	public final Connection getConnection(){
		return connection;
	}

	public final int doQueryInt(final String query, final Object[][] params){
		return doQueryInt(query, params, -1);
	}

	public final int doQueryInt(final String query, final Object[][] setters, final int defaultValue){
		if (MyProperties.getBoolean("debug.sql")) print(query, setters);
		int result = defaultValue;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : setters) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = rs.getInt(1);
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public double doQueryDouble(String query, Object[][] setters, double defaultValue) {
		if (MyProperties.getBoolean("debug.sql")) print(query, setters);
		double result = defaultValue;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : setters) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = rs.getDouble(1);
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}

		return result;
	}

	public final long doQueryLong(final String query, final Object[][] setters){
		return doQueryLong(query, setters, -1);
	}

	public final long doQueryLong(final String query, final Object[][] setters, final long defaultValue){
		if (MyProperties.getBoolean("debug.sql")) print(query, setters);
		long result = -1;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : setters) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = rs.getLong(1);
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}

		return result;
	}

	public final String doQueryString(final String query, final Object[][] params){
		return doQueryString(query, params, "-1");
	}

	public final String doQueryString(final String query, final Object[][] params, final String defaultValue){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		String result = defaultValue;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = rs.getString(1);
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public Blob doQueryBlob(String query, Object[][] params) {
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		Blob result = null;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = rs.getBlob(1);
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final Timestamp doQueryTimestamp(final String query, final Object[][] params){
		return doQueryTimestamp(query, params, null);
	}

	public final Timestamp doQueryTimestamp(final String query, final Object[][] params, final Timestamp defaultValue){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		Timestamp result = defaultValue;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = rs.getTimestamp(1);
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final boolean doQueryExist(final String query, final Object[][] params){
		return doQueryExist(query, params, false);
	}

	public final boolean doQueryExist(final String query, final Object[][] params, final boolean defaultValue){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		boolean result = false;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					if(rs.next()){
						result = true;
					}
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final int doQueryUpdate(final String query, final Object[][] params){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		int result = -1;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				result = s.executeUpdate();
			}catch (Exception e) {
				MySqlLog.log(e);
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final int doQueryInsert(final String query, final Object[][] params){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		int result = -1;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				result = s.executeUpdate();
			}catch (Exception e) {
				MySqlLog.log(e);
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final int doQueryInsertGeneratedKey(final String query, final Object[][] params){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		int result = -1;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				s.executeUpdate();
				final ResultSet rs = s.getGeneratedKeys();
				try {
					result = rs.next() ? rs.getInt(1) : -1;
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final int doQueryDelete(final String query, final Object[][] params){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		int result = -1;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				result = s.executeUpdate();
			}catch (Exception e) {
				MySqlLog.log(e);
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return result;
	}

	public final ResultSet doQueryResultSet(final String query, final Object[][] params){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		PreparedStatement s;
		ResultSet rs = null;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			int index = 1;
			for	(final Object[] param : params) {
				addSetter(s, index, param);
				++index;
			}
			rs = s.executeQuery();
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		}
		return rs;
	}

	public final boolean existColumn(final String table, final String column){
		if (MyProperties.getBoolean("debug.sql")) MySqlLog.log("SQL " + nbQuery + ":\t existColumn "+table+" "+column);
		try {
			String sql = "SELECT `"+column+"` FROM `"+table+"`";
			Statement statement = connection.createStatement();
			ResultSet resultat = statement.executeQuery(sql);
			ResultSetMetaData metadata = resultat.getMetaData();
			if (column.equals(metadata.getColumnLabel(1)))
				return true;
		} catch (Exception e) { }
		return false;
	}

	public final boolean addColumn(final String table, final String column, final String type, final String defaultValue){
		if (MyProperties.getBoolean("debug.sql")) MySqlLog.log("SQL " + nbQuery + ":\t addColumn "+table+" "+column+" "+type+" "+defaultValue);
		try {
			String sql = "ALTER TABLE `"+table+"` ADD `"+column+"` "+type+" DEFAULT '"+defaultValue+"';";
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	public final int doQueryNbRow(final String query, final Object[][] params){
		if (MyProperties.getBoolean("debug.sql")) print(query, params);
		int result = 0;
		PreparedStatement s;
		try {
			if (connection.isClosed()) createConnection();
			s = connection.prepareStatement(query);
			try {
				int index = 1;
				for	(final Object[] param : params) {
					addSetter(s, index, param);
					++index;
				}
				final ResultSet rs = s.executeQuery();
				try {
					rs.last();
					result = rs.getRow();
				}catch (Exception e) {
					MySqlLog.log(e);
				} finally {
					rs.close();
				}
			} finally {
				try {
					s.close();
				} catch (SQLException e) {
					MySqlLog.log(e);
				}
			}
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) { }
		}
		return result;
	}

	public final void closePool(){
		try {
			if (null != connection)
				connection.close();
		} catch (Exception e) {
			MySqlLog.log(e);
		}
	}

	public boolean existTable(String table) {
		if (MyProperties.getBoolean("debug.sql")) MySqlLog.log("Existe table: " + table);
		try {
			if (connection.isClosed()) createConnection();
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, table, null);
			return rs.next();
		} catch (SQLException e) {
			MySqlLog.log(e);
		} catch (NullPointerException e) {
			MySqlLog.log(e);
		} finally {
			try {
				if (null != connection && connection.getAutoCommit())
					connection.close();
			} catch (SQLException e) {
				MySqlLog.log(e);
			}
		}
		return false;
	}

	private void addSetter(PreparedStatement s, final int index, final Object[] param) throws NumberFormatException, SQLException {
		if ((SETTER_TYPE)param[0] == SETTER_TYPE.INT){
			s.setInt(index, (Integer)param[1]);
		} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.BOOLEAN){
			s.setBoolean(index, (Boolean)param[1]);
		} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.STRING){
			s.setString(index, (String)param[1]);
		} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.TIMESTAMP){
			s.setTimestamp(index,(Timestamp)param[1]);
		} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.BLOB){
			try { s.setBlob(index, new SerialBlob((byte[]) param[1]));
			} catch (Exception e) { s.setString(index, ""); }
		} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.DOUBLE){
			s.setDouble(index, (Double)param[1]);
		}
	}

	public static void print(String query, Object[][] params) {
		for	(final Object[] param : params) {
			String value = "";
			if ((SETTER_TYPE)param[0] == SETTER_TYPE.INT){
				value = ((Integer)param[1]).toString();
			} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.STRING){
				value = (String)param[1];
			} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.TIMESTAMP){
				value = ((Timestamp)param[1]).toString();
			} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.BLOB){
				try { value = (new SerialBlob((byte[]) param[1])).toString(); } catch (Exception e) { }
			} else if ((SETTER_TYPE)param[0] == SETTER_TYPE.DOUBLE){
				value = ((Double)param[1]).toString();
			} else {
				value = param[1].toString();
			}
			try { query = query.replaceFirst("\\?", value); } catch (Exception e) { }
		}
		MySqlLog.log("SQL " + nbQuery + ":\t " + query.trim());
	}
}