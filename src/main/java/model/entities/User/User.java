package model.entities.User;

import java.security.MessageDigest;

public class User {

	private Long id;
	private String name;
	private String email;
	private String password;
	private UserType type;

	public User() {
	}

	public User(Long id, String name, String email, String password, UserType type) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
	}

	public User(String name, String email, String password, UserType type) {
		this.name = name;
		this.email = email;
		setPassword(password);
		this.type = type;
	}
	
	public static boolean autenticate(User onSystem, String password){
        return onSystem.password.equals(onSystem.encryptPassword(password));
    }
	
	protected String encryptPassword(String passwd){
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwd.getBytes());
            byte[] digest = md.digest();
            for(byte b : digest){
                sb.append(String.format("%02x", b & 0xff));
            }
        }catch (Exception e){
            sb = new StringBuilder();
        }
        return sb.toString();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryptPassword(password);
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	

}
