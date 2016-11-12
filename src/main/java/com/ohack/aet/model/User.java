package com.ohack.aet.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "users")
public class User {
	
	@Id
	private String aadharNo;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date dob;
	
	private String caste;
	
	private String gender;
	
    private String qualification;
    
    private String address;
    
    private boolean isRural;
    
    private long phoneNo;
    
    private long annualIncome;
	
    private int noOfChildren;
    
    private String maritalStatus;
    
    private List<TrainingEvent> enrolledEvents;
    
    private List<TrainingEvent> completedEvents;
    
    private List<String> skills;
    
    private String role;
    
    private byte[] userPic;

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isRural() {
		return isRural;
	}

	public void setRural(boolean isRural) {
		this.isRural = isRural;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public long getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(long annualIncome) {
		this.annualIncome = annualIncome;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public List<TrainingEvent> getEnrolledEvents() {
		return enrolledEvents;
	}

	public void setEnrolledEvents(List<TrainingEvent> enrolledEvents) {
		this.enrolledEvents = enrolledEvents;
	}

	public List<TrainingEvent> getCompletedEvents() {
		return completedEvents;
	}

	public void setCompletedEvents(List<TrainingEvent> completedEvents) {
		this.completedEvents = completedEvents;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public byte[] getUserPic() {
		return userPic;
	}

	public void setUserPic(byte[] userPic) {
		this.userPic = userPic;
	}
    

}
