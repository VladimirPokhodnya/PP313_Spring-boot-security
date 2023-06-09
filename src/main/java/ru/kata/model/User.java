package ru.kata.model;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="username", unique=true)
	private String username;
	private String password;
	private boolean enabled;

	public User() {
	}

	public User(String username, String password, boolean enabled, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)

	private Set<Role> roles = new HashSet<>(); 

	public Long getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = password;
		String encodedPassword = encoder.encode(rawPassword);
		this.password = encodedPassword;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
