package org.rettek.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import java.lang.Override;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Vendor implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(length = 1000, nullable = false)
	private String name;

	@Column(length = 2000)
	private String address;

	@Column
	private String phone;

	@Column
	private String mobile;

	@Column(length = 2000)
	private String remarks;

	@Column
	private String email;
	@Column
	private double credit;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Audit audit;

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Vendor)) {
			return false;
		}
		Vendor other = (Vendor) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		if (name != null && !name.trim().isEmpty())
			result += ", name: " + name;
		if (address != null && !address.trim().isEmpty())
			result += ", address: " + address;
		if (phone != null && !phone.trim().isEmpty())
			result += ", phone: " + phone;
		if (mobile != null && !mobile.trim().isEmpty())
			result += ", mobile: " + mobile;
		if (remarks != null && !remarks.trim().isEmpty())
			result += ", remarks: " + remarks;
		if (email != null && !email.trim().isEmpty())
			result += ", email: " + email;
		result += ", credit: " + credit;
		return result;
	}

}