package com.sample.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class User implements Serializable {

	private static final long serialVersionUID = 1750318871262380298L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userid;
	
	private String address1;
	
	private String address2;
	
	private Integer age;
	
	@Column(name = "alan_resorts_id")
	private String alanResortsId;
	
	private String city;
	
	private String country;
	
	@Column(name = "d_hotels_id")
	private String dHotelsId;
	
	private String emailaddress;
	
	private String firstname;
	
	private Integer frequentflyernumber;
	
	private String lastname;
	
	private String loyaltylevel;
	
	private String middlename;
	
	private String password;
	
	private Integer phonenumber;
	
	@Column(name = "state_prov")
	private String stateProv;
	
	private String username;
	
	@Column(name = "zip_post")
	private String zipPost;
	
	private Integer miles = 0;
	
	@OneToMany(mappedBy = "user")
	private Set<Reservation> reservations;
	
	public User() {
		
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAlanResortsId() {
		return alanResortsId;
	}

	public void setAlanResortsId(String alanResortsId) {
		this.alanResortsId = alanResortsId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getdHotelsId() {
		return dHotelsId;
	}

	public void setdHotelsId(String dHotelsId) {
		this.dHotelsId = dHotelsId;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Integer getFrequentflyernumber() {
		return frequentflyernumber;
	}

	public void setFrequentflyernumber(Integer frequentflyernumber) {
		this.frequentflyernumber = frequentflyernumber;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLoyaltylevel() {
		return loyaltylevel;
	}

	public void setLoyaltylevel(String loyaltylevel) {
		this.loyaltylevel = loyaltylevel;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(Integer phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getStateProv() {
		return stateProv;
	}

	public void setStateProv(String stateProv) {
		this.stateProv = stateProv;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZipPost() {
		return zipPost;
	}

	public void setZipPost(String zipPost) {
		this.zipPost = zipPost;
	}

	public Integer getMiles() {
		return miles;
	}

	public void setMiles(Integer miles) {
		this.miles = miles;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		return this.firstname + " " + this.lastname ;
	}
}
