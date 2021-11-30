package main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class U2P5 {

	private final String sqliteDB = "jdbc:sqlite:/Users/joshuarojasgonzalez/veterinaria.db";

	public void extractMetadata() throws SQLException {
		
		Connection connection = DriverManager.getConnection(sqliteDB);

		if (connection != null) {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSetTables = metaData.getTables(null, "veterinaria", null, null);
//EXCTRACT TABLE METADATA
			while (resultSetTables.next()) {

				String table = resultSetTables.getString("TABLE_NAME");
				System.out.println("\nTable: "+ table+"\n");
	//FIND THE PRIMARY KEYS
				ResultSet resultSetPrimaryKeys = metaData.getPrimaryKeys(null, null, table);

				while(resultSetPrimaryKeys.next()) {
					String primaryKeyColumn = resultSetPrimaryKeys.getString("COLUMN_NAME");
					System.out.println("Primary key: " + primaryKeyColumn);
				}
	//FIND THE FOREING KEYS
				ResultSet resultSetForeingKeys = metaData.getExportedKeys(null, null, table);

				System.out.println("Foreing Keys: ");

				while (resultSetForeingKeys.next()) {
					
					String primaryTableName = resultSetForeingKeys.getString("PKTABLE_NAME");
					String foreingTableName = resultSetForeingKeys.getString("FKTABLE_NAME");
					String primaryColumnName = resultSetForeingKeys.getString("PKCOLUMN_NAME");
					String foreignColumnName = resultSetForeingKeys.getString("FKCOLUMN_NAME");
					
					System.out.println("\n\tPrimary key from: "+primaryTableName+"\n"+ "\tIn the column: "+primaryColumnName+				
										"\n\tForeing key from: "+foreingTableName+"\n"+"\tIn the column: "+foreignColumnName);
					
				}
	//EXTRACT INFORMATION ABOUT THE COLUMNS
				ResultSet resultSetColumns = metaData.getColumns(null, null, table, null);
				System.out.println("\nColumns:\n");
				while (resultSetColumns.next()) {
					String column = resultSetColumns.getString("COLUMN_NAME");
					String columnType = resultSetColumns.getString("TYPE_NAME");
					String columnAutoIncrement = resultSetColumns.getString("IS_AUTOINCREMENT");
					System.out.println("\t"+column+"\t"+" Type: "+ columnType+"\tAuto Increment: "+columnAutoIncrement);
				}
			}// While for the tables
		}// If our connection is not null
	}// Void to extract metadata

	public void prepareDDLQuery() throws SQLException {

	}

	public static void main(String[] args) {
		U2P5 query = new U2P5();

		try {
			query.extractMetadata();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
