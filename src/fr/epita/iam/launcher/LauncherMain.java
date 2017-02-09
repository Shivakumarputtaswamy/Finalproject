/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.JDBCIdentity;

/**
 * @author Shivakumar
 *
 */
public class LauncherMain {
	private static JDBCIdentity dao;

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		System.out.println("Hello, welcome to the IAM application");
		Scanner scanner = new Scanner(System.in);
		dao = new JDBCIdentity();
		
		
		
		//authentication
		System.out.println("Please enter username");
		String login = scanner.nextLine();
		System.out.println("Please enter password");
		String password = scanner.nextLine();
		
		if(!dao.validate(login, password)){
			scanner.close();
			return;
		}
		System.out.println("You're authenticated");
		boolean flag=true;
		while(flag) {
			// menu
			String choice = options(scanner);
			
			switch (choice) {
			case "a":
				// creation
				createIdentity(scanner);
				break;
			case "b":
				updateIdentity(scanner);
				break;
			case "c":
				deleteIdentity(scanner);
				break;
			case "d":
				getIdentities();
				break;
			default:
				System.out.println("Thank you for using this app");
				flag=false;
				break;
			}
		}
		
		scanner.close();

	}

	private static void deleteIdentity(Scanner scanner) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("You've selected : Identity Deletion");
		System.out.println("Please enter the Identity uid");
		String uid = scanner.nextLine();
		Identity newIdentity = new Identity();
		newIdentity.setUid(uid);
		dao.deleteIdentity(newIdentity);
		System.out.println("you succesfully deleted this identity :" + newIdentity);
	}

	private static void updateIdentity(Scanner scanner) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("You've selected : Identity Updation");
		System.out.println("Please enter the Identity uid");
		String uid = scanner.nextLine();
		System.out.println("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the Identity email");
		String email = scanner.nextLine();
		Identity newIdentity = new Identity();
		newIdentity.setUid(uid);
		newIdentity.setDisplayName(displayName);
		newIdentity.setEmail(email);
		dao.updateIdentity(newIdentity);
		System.out.println("you succesfully updated this identity :" + newIdentity);
	}

	/**
	 * @throws SQLException 
	 * 
	 */
	private static void getIdentities() throws SQLException {
		
		List<Identity> list = dao.getIdentities();
		int size = list.size();
		if(size>0){
			System.out.println("This is the list of all identities in the system");
		for(int i = 0; i < size; i++){
			System.out.println( i+ "." + list.get(i));
		}
		}
		else{
			System.out.println("No identities in the system");
		}
	}

	/**
	 * @param scanner
	 * @throws SQLException 
	 */
	private static void createIdentity(Scanner scanner) throws SQLException {
		System.out.println("You've selected : Identity Creation");
		System.out.println("Please enter the Identity uid");
		String uid = scanner.nextLine();
		System.out.println("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the Identity email");
		String email = scanner.nextLine();
		Identity newIdentity = new Identity();
		newIdentity.setUid(uid);
		newIdentity.setDisplayName(displayName);
		newIdentity.setEmail(email);
		dao.saveIdentity(newIdentity);
		System.out.println("you succesfully created this identity :" + newIdentity);
	}

	/**
	 * @param scanner
	 * @return
	 */
	private static String options(Scanner scanner) {
		
		System.out.println("Here are the actions you can perform :");
		System.out.println("a. Create an Identity");
		System.out.println("b. Modify an Identity");
		System.out.println("c. Delete an Identity");
		System.out.println("d. List Identities");
		System.out.println("e. quit");
		System.out.println("your choice (a|b|c|d|e) ? : ");
		String answer = scanner.nextLine();
		return answer;
	}

}
