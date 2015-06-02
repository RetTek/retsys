package org.rettek.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import java.lang.Override;

import org.rettek.model.Project;

import javax.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class DeliveryChallan implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	private Project project;

	@Column
	@Temporal(TemporalType.DATE)
	private Date challanDate;

	@Column(columnDefinition = "BIT")
	private boolean isDelivery;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "deliveryChallan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DeliveryChallanDetail> deliveryChallanDetail = new ArrayList<DeliveryChallanDetail>();

	@ManyToOne
	private DeliveryChallan originalDeliveryChallan;
	
	@Column
	private String deliveryMode;
	
	@Column
	private String concernPerson;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Audit audit;

	public boolean isDelivery() {
		return isDelivery;
	}

	public void setDelivery(boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getConcernPerson() {
		return concernPerson;
	}

	public void setConcernPerson(String concernPerson) {
		this.concernPerson = concernPerson;
	}


	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public DeliveryChallan getOriginalDeliveryChallan() {
		return originalDeliveryChallan;
	}

	public void setOriginalDeliveryChallan(
			DeliveryChallan originalDeliveryChallan) {
		this.originalDeliveryChallan = originalDeliveryChallan;
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
		if (!(obj instanceof DeliveryChallan)) {
			return false;
		}
		DeliveryChallan other = (DeliveryChallan) obj;
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

	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

	public Date getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}

	public boolean isIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		result += "isDelivery: " + isDelivery;
		return result;
	}

	public List<DeliveryChallanDetail> getDeliveryChallanDetail() {
		return this.deliveryChallanDetail;
	}

	public void setDeliveryChallanDetail(
			final List<DeliveryChallanDetail> deliveryChallanDetail) {
		Iterator<DeliveryChallanDetail> it = deliveryChallanDetail.iterator();
		while (it.hasNext()) {
			DeliveryChallanDetail dcDetail = (DeliveryChallanDetail) it.next();
			if (dcDetail.getChallan() != this) {
				dcDetail.setChallan(this);
			}
		}
		this.deliveryChallanDetail = deliveryChallanDetail;
	}
}