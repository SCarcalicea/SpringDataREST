package net.springjpa.mvc.rac.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.springframework.lang.NonNull;

@Entity
@Table(name="users")
@Proxy(lazy = false)
public class User {
	
	@Id
	@NonNull
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Integer ID;
	private String firstName;
	private String lastName;
	private String email;
	
	public User() {
	}
	
	public User(Integer Id) {
		this(Id, "", "", "");
	}
	
	public User(Integer Id, String first, String last, String email) {
		this.ID = Id;
		this.firstName = first;
		this.lastName = last;
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (ID ^ (ID >>> 32));
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (ID != other.ID)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "User [id=" + ID + ", name=" + firstName + ", lastName=" + lastName
                + ", email=" + email + "]";
    }

}
