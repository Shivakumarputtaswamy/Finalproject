/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;

/**
 * @author Shivakumar
 *
 */
public class JDBCIdentity {

	private Connection connection;
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public JDBCIdentity() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/iam-core-ref","root","");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(connection.getSchema());
	}
	
	/**
	 * @param login
	 * @param password
	 */
	public boolean validate(String login, String password) {

		// TODO replace this hardcoded check by the real authentication method
		PreparedStatement pstmt_select;
		try {
			pstmt_select = connection.prepareStatement("select * from admin where username='"+login+"' and password='"+password+"'");
			ResultSet rs = pstmt_select.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public void saveIdentity(Identity identity) throws SQLException {
		String insertStatement = "insert into IDENTITIES (IDENTITIES_UID,IDENTITIES_DISPLAYNAME, IDENTITIES_EMAIL) "
				+ "values(?, ?, ?)";
		PreparedStatement pstmt_insert = connection.prepareStatement(insertStatement);
		pstmt_insert.setString(1, identity.getUid());
		pstmt_insert.setString(2, identity.getDisplayName());
		pstmt_insert.setString(3, identity.getEmail());

		pstmt_insert.execute();

	}

	public List<Identity> getIdentities() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmt_select.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			Identity identity = new Identity();
			identity.setDisplayName(displayName);
			identity.setEmail(email);
			identity.setUid(uid);
			identities.add(identity);
		}
		return identities;

	}

	public void updateIdentity(Identity newIdentity) throws SQLException {
		// TODO Auto-generated method stub
		String updateStatement = "update IDENTITIES set IDENTITIES_DISPLAYNAME=?,IDENTITIES_EMAIL=? where IDENTITIES_UID=?";
		PreparedStatement pstmt_update = connection.prepareStatement(updateStatement);
		
		pstmt_update.setString(1, newIdentity.getDisplayName());
		pstmt_update.setString(2, newIdentity.getEmail());
		pstmt_update.setString(3, newIdentity.getUid());

		pstmt_update.execute();
	}

	public void deleteIdentity(Identity newIdentity) throws SQLException {
		// TODO Auto-generated method stub
		String deleteStatement = "delete from IDENTITIES where IDENTITIES_UID=?";
		PreparedStatement pstmt_delete = connection.prepareStatement(deleteStatement);
		
		pstmt_delete.setString(1, newIdentity.getUid());

		pstmt_delete.execute();
	}

}
