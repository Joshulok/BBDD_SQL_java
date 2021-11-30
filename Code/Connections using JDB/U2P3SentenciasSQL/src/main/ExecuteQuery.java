package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQuery {
	
	private String databaseConnection = "jdbc:mysql://172.26.80.40:3306/starwars";
	private String databaseUser = "star";
	private String databasePassword = "wars";

// Method to list the information of all planets
	public void listPlanets() throws SQLException {
		
		Connection connection = DriverManager.getConnection(databaseConnection, databaseUser, databasePassword);
		String query = "SELECT * FROM `planets`;";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while (rs.next()) {
			
			System.out.println(rs.getString(2));
			System.out.println("\t"+
						"ID: "+ rs.getInt(1)+" - "+
						"Name: " + rs.getString(2)+" - "+
						"Rotation Period: "+ rs.getInt(3)+" - "+
						"Orbital Rotation: "+rs.getString(4)+" - "+
						"Diameter: "+rs.getInt(5)+" "
					);
			
			System.out.println("\t"+
						"Climate: "+rs.getString(6)+" - "+
						"Gravity: "+rs.getString(7)+" - "+
						"Terrain: "+rs.getString(8)+" - "+
						"Surface Water: "+rs.getInt(9)
					);
			System.out.println("\t"+
						"Population: "+rs.getLong(10)+" - "+
						"Created date: "+rs.getDate(11)+" - "+
						"Updated date: "+rs.getDate(12) + " - "+
						"URL: "+rs.getString(13)
					);
		}
		statement.close();
		rs.close();
		connection.close();
	}

/* Insert three new records from the table films
with the data from episodes VII, VIII and IX
*/
	public void insertNewEpisodes() throws SQLException {

		Connection connection = DriverManager.getConnection(databaseConnection, databaseUser, databasePassword);
		String query = "INSERT INTO films (id,episode,title ) VALUES (7, 'Episode VII', 'The Force Awakens'),(8, 'Episode VIII', 'The Last Jedi'),(9, 'Episode IX', 'The Rise of Skywalker');";
		Statement statement = connection.createStatement();
		int rowsAfected = statement.executeUpdate(query);

		if (rowsAfected > 0) {
			System.out.println("Operation completed!");
			System.out.println("Rows afected: "+rowsAfected);
		} else {
			System.out.println("Error!");
			System.out.println("Rows afected: "+rowsAfected);
		}
		statement.close();
		connection.close();
	}

// List the information of the characters that belong the Jedi Order:
	
	public void listJedis() throws SQLException {
		
		Connection connection = DriverManager.getConnection(databaseConnection, databaseUser, databasePassword);
		String query = "SELECT C.id, C.name, CA.id_character , CA.id_affiliation FROM characters C, character_affiliations CA WHERE C.id = CA.id_character AND CA.id_affiliation = 1;";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		System.out.println("The Jedi Order Members: ");
		while(resultSet.next()) {
			System.out.print("ID: "+resultSet.getInt(1)+" - ");
			System.out.print("Name: "+resultSet.getString(2)+"\n");
		}
		
		statement.close();
		resultSet.close();
		connection.close();
	}

// List the characters death in episode III and their killers:
	public void killAndKillers() throws SQLException {
		
		Connection connection = DriverManager.getConnection(databaseConnection, databaseUser, databasePassword);
		String query = "SELECT characters.name, ch.name FROM characters JOIN deaths on characters.id = deaths.id_character, characters ch JOIN deaths de on ch.id = de.id_killer WHERE deaths.id = de.id AND deaths.id_film = 3;";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		
		System.out.println("Character deaths in Episode 3: ");
		
		while (resultSet.next()) {
			
			System.out.println("RIP "+resultSet.getString(1)+"\n\t- Killer: "+ resultSet.getString(2)+"\n");
			
		}
	}
	
	public static void main(String[] args) {
		//An instance of the class to make the queries
		ExecuteQuery query = new ExecuteQuery();
		
		try {
			
			//query.listPlanets();
			//query.insertNewEpisodes();
			//query.listJedis();
			query.killAndKillers();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}