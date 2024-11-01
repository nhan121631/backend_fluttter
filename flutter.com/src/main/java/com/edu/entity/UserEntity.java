package com.edu.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "createddate")
	@CreatedDate
	private Date createdDate;
	
	@Column(name = "modifieddate")
	@LastModifiedDate
	private Date modifiedDate;
	
	@Column(name = "createby")
	@CreatedBy
	private String createBy;
	
	@Column(name = "modifiedby")
	@LastModifiedBy
	private String modifiedBy;

	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "resetpasswordcode")
	private String resetPasswordCode;
	
	@Column(name = "email", nullable = false, unique = true)
    private String email;
	
	@Column(name="resetpasswordcodecreationtime")
	private LocalDateTime resetPasswordCodeCreationTime; // Thêm trường này

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<OrderEntity> orders = new ArrayList<>();
	
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CartEntity cart;  
    
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<ReviewEntity> reviews = new ArrayList<>();
	

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "user_role", 
	  joinColumns = @JoinColumn(name = "userid"), 
	  inverseJoinColumns = @JoinColumn(name = "roleid"))
    @JsonManagedReference // Đánh dấu phần cha trong quan hệ
	private List<RoleEntity> roles = new ArrayList<>();
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getResetPasswordCodeCreationTime() {
		return resetPasswordCodeCreationTime;
	}

	public void setResetPasswordCodeCreationTime(LocalDateTime resetPasswordCodeCreationTime) {
		this.resetPasswordCodeCreationTime = resetPasswordCodeCreationTime;
	}

	
}