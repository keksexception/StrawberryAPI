package de.raffi.strawberry.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLApi {
	
	String host; 
	String port;
	String database;
	String username;
	String password;
	Connection con;
	
	String TABLE;
	/**
	 * 
	 * @param host host
	 * @param port connection port
	 * @param database database of mysql
	 * @param username mysql username
	 * @param passwort your passwort "" if no passwort
	 * @param table table 
	 */
	public MySQLApi(String host, String port, String database, String username, String passwort, String table) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = passwort;
		this.TABLE = table;
		
		Connect();
			
	}
	/**
	 * Connect to MySQL
	 */
	public void Connect() {
		if(!isConnected()) {
			try {
				this.con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",this.username,this.password);
				System.out.println("[MySQL] Connected!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @return get MySQL connection
	 */
	public Connection getConnection() {
		return this.con;
	}
	/**
	 * 
	 * @return true is MySQL is connected
	 */
	public boolean isConnected() {
		return(this.con == null ? false : true);
	}
	/**
	 * Disconnect from MySQL
	 */
	public void Disconnect() {
		if(isConnected()) {
			try {
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param spalte spalte von Value
	 * @param value value
	 * @return
	 */
	public int getInt(String spalte, String value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT `" + spalte + "` FROM " + this.TABLE + " WHERE `" + value + "` = ?");
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(spalte);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * 
	 * @param spalte spalte von Value
	 * @param value value
	 * @return
	 */
	public String getString(String spalte, String value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT `" + spalte + "` FROM " + this.TABLE + " WHERE `" + value + "` = ?");
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString(spalte);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param spalte spalte von Value
	 * @param value value
	 * @return
	 */
	public boolean getBoolean(String spalte, String value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT `" + spalte + "` FROM " + this.TABLE + " WHERE `" + value + "` = ?");
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getBoolean(spalte);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @param spalte spalte von Value
	 * @param value value
	 * @return
	 */
	public long getLong(String spalte, String value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT `" + spalte + "` FROM " + this.TABLE + " WHERE `" + value + "` = ?");
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getLong(spalte);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * 
	 * @param spalte spalte von Value
	 * @param value value
	 * @return
	 */
	public float getFloat(String spalte, String value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(
					"SELECT `" + spalte + "` FROM " + this.TABLE + " WHERE `" + value + "` = ?");
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getLong(spalte);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * 
	 * @param spalte Spalte der Abfrage
	 * @return
	 */
	public List<String> getStringList(String spalte) {
		List<String> list = new ArrayList<>();
		list.clear();
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT " + spalte + " FROM " + this.TABLE);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(spalte));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * @param spalte
	 * @param value
	 * @return true if the value exist in the Table
	 */
	public boolean existString(String spalte, String value) {
		if(getString(spalte, value) == null)
			return false;
		else
			return true;
	}
	/**
	 * 
	 * @param spalte
	 * @param value
	 * @return true if the value exist in the Table
	 */
	public boolean existInt(String spalte, String value) {
		if(getInt(spalte, value) == -1)
			return false;
		else
			return true;
	}
	/**
	 * 
	 * @param spalte
	 * @param value
	 * @return
	 */
	public boolean existFloat(String spalte, String value) {
		if(getFloat(spalte, value) == -1)
			return false;
		else
			return true;
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */	
	public void insertString(String spalte, String spalte2, String string, String value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO `" + this.TABLE + "` (" + spalte + ", "+ spalte2 + ") VALUES (?,?)");
			ps.setString(1, string);
			ps.setString(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void insertInt(String spalte, String spalte2, String string, int value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO `" + this.TABLE + "` (" + spalte + ", "+ spalte2 + ") VALUES (?,?)");
			ps.setString(1, string);
			ps.setInt(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void insertInt(String spalte, String spalte2, int string, int value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO `" + this.TABLE + "` (" + spalte + ", "+ spalte2 + ") VALUES (?,?)");
			ps.setInt(1, string);
			ps.setInt(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void insertBoolean(String spalte, String spalte2, String string, boolean value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO `" + this.TABLE + "` (" + spalte + ", "+ spalte2 + ") VALUES (?,?)");
			ps.setString(1, string);
			ps.setBoolean(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void insertLong(String spalte, String spalte2, String string, long value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO `" + this.TABLE + "` (" + spalte + ", "+ spalte2 + ") VALUES (?,?)");
			ps.setString(1, string);
			ps.setLong(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void insertFloat(String spalte, String spalte2, String string, float value) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT INTO `" + this.TABLE + "` (" + spalte + ", "+ spalte2 + ") VALUES (?,?)");
			ps.setString(1, string);
			ps.setFloat(2, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 * 
	 * @see insertString()
	 */
	public void updateString(String spalte, String spalte2, String string, String value) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("UPDATE `" + this.TABLE + "` SET `" + spalte2 + "`='" + value
							+ "' WHERE `" + spalte + "`='" + string + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void updateInt(String spalte, String spalte2, String string, int value) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("UPDATE `" + this.TABLE + "` SET `" + spalte2 + "`='" + value
							+ "' WHERE `" + spalte + "`='" + string + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void updateInt(String spalte, String spalte2, int string, int value) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("UPDATE `" + this.TABLE + "` SET `" + spalte2 + "`='" + value
							+ "' WHERE `" + spalte + "`='" + string + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void updateBoolean(String spalte, String spalte2, String string, boolean value) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("UPDATE `" + this.TABLE + "` SET `" + spalte2 + "`='" + value
							+ "' WHERE `" + spalte + "`='" + string + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void updateLong(String spalte, String spalte2, String string, long value) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("UPDATE `" + this.TABLE + "` SET `" + spalte2 + "`='" + value
							+ "' WHERE `" + spalte + "`='" + string + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param spalte Spalte 1 (bezogen auf string)
	 * @param spalte2 Spalte 2 (bezogen auf value)
	 * @param string objekt zum updaten
	 * @param value wert von objekt
	 */
	public void updateFloat(String spalte, String spalte2, String string, float value) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("UPDATE `" + this.TABLE + "` SET `" + spalte2 + "`='" + value
							+ "' WHERE `" + spalte + "`='" + string + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteString(String spalte, String wert) {		
		try {
			PreparedStatement ps = getConnection()
					.prepareStatement("DELETE FROM " + this.TABLE + " WHERE " + spalte + " ='" + wert + "'");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean prepareStateMent(PreparedStatement ps) {
		try {
			ps.executeQuery();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	

}
