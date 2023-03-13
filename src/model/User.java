package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userCod;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String userPassword;
	
	private String userRole;

	private String email;

	private boolean isEnable;
	
	private Date signInDate;
	

	public User() {
		
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUserCod() {
		return this.userCod;
	}

	public void setUserCod(int userCod) {
		this.userCod = userCod;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public Date getSignInDate() {
		return this.signInDate;
	}

	public void setSignInDate(Date userSignInDate) {
		this.signInDate = userSignInDate;
	}


	@Override
	public String toString() {
		return "User [userCod=" + userCod + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", userRole=" + userRole + ", email=" + email + ", isEnable=" + isEnable + ", signInDate="
				+ signInDate + "]";
	}
	
}