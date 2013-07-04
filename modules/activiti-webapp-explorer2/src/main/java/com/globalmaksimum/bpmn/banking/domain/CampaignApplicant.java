package com.globalmaksimum.bpmn.banking.domain;

import java.io.Serializable;
import java.util.Date;

public class CampaignApplicant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4303003335667538164L;
	private String name;
	private String surname;
	private String customerid;
	private Date creationDate;
	private Boolean hasKMH;
	private Boolean hasOpenCredit;
	private Boolean finansMaasCustomer;
	private Boolean maasIntervalApproved;

	public Boolean getHasOpenCredit() {
		return hasOpenCredit;
	}

	public void setHasOpenCredit(Boolean hasOpenCredit) {
		this.hasOpenCredit = hasOpenCredit;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getHasKMH() {
		return hasKMH;
	}

	public void setHasKMH(Boolean hasKMH) {
		this.hasKMH = hasKMH;
	}

	public Boolean getFinansMaasCustomer() {
		return finansMaasCustomer;
	}

	public void setFinansMaasCustomer(Boolean finansMaasCustomer) {
		this.finansMaasCustomer = finansMaasCustomer;
	}

	public Boolean getMaasIntervalApproved() {
		return maasIntervalApproved;
	}

	public void setMaasIntervalApproved(Boolean maasIntervalApproved) {
		this.maasIntervalApproved = maasIntervalApproved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "CampaignApplicant [name=" + name + ", surname=" + surname
				+ ", customerid=" + customerid + ", creationDate="
				+ creationDate + ", hasKMH=" + hasKMH + ", hasOpenCredit="
				+ hasOpenCredit + ", finansMaasCustomer=" + finansMaasCustomer
				+ ", maasIntervalApproved=" + maasIntervalApproved
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((customerid == null) ? 0 : customerid.hashCode());
		result = prime
				* result
				+ ((finansMaasCustomer == null) ? 0 : finansMaasCustomer
						.hashCode());
		result = prime * result + ((hasKMH == null) ? 0 : hasKMH.hashCode());
		result = prime * result
				+ ((hasOpenCredit == null) ? 0 : hasOpenCredit.hashCode());
		result = prime
				* result
				+ ((maasIntervalApproved == null) ? 0 : maasIntervalApproved
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		CampaignApplicant other = (CampaignApplicant) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (customerid == null) {
			if (other.customerid != null)
				return false;
		} else if (!customerid.equals(other.customerid))
			return false;
		if (finansMaasCustomer == null) {
			if (other.finansMaasCustomer != null)
				return false;
		} else if (!finansMaasCustomer.equals(other.finansMaasCustomer))
			return false;
		if (hasKMH == null) {
			if (other.hasKMH != null)
				return false;
		} else if (!hasKMH.equals(other.hasKMH))
			return false;
		if (hasOpenCredit == null) {
			if (other.hasOpenCredit != null)
				return false;
		} else if (!hasOpenCredit.equals(other.hasOpenCredit))
			return false;
		if (maasIntervalApproved == null) {
			if (other.maasIntervalApproved != null)
				return false;
		} else if (!maasIntervalApproved.equals(other.maasIntervalApproved))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

}
