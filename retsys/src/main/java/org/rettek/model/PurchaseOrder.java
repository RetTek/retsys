package org.rettek.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.rettek.model.Vendor;

@Entity
@XmlRootElement
public class PurchaseOrder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	private Vendor vendor;

	@ManyToOne
	private Project project;

	@Column(length = 4000)
	private String deliveryAddress;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseOrderDetail> purchaseOrderDetail = new ArrayList<PurchaseOrderDetail>();

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
		if (!(obj instanceof PurchaseOrder)) {
			return false;
		}
		PurchaseOrder other = (PurchaseOrder) obj;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		if (date != null)
			result += ", date: " + date;
		if (vendor != null)
			result += ", vendor: " + vendor;
		if (getProject() != null)
			result += ", project: " + getProject();
		if (deliveryAddress != null && !deliveryAddress.trim().isEmpty())
			result += ", deliveryAddress: " + deliveryAddress;
		return result;
	}

	public void newVendor() {
		this.vendor = new Vendor();
	}

	public void newProject() {
		this.setProject(new Project());
	}

	public List<PurchaseOrderDetail> getPurchaseOrderDetail() {
		return this.purchaseOrderDetail;
	}

	public void setPurchaseOrderDetail(
			final List<PurchaseOrderDetail> purchaseOrderDetail) {
		Iterator<PurchaseOrderDetail> it = purchaseOrderDetail.iterator();
		while (it.hasNext()) {
			PurchaseOrderDetail poDetail = (PurchaseOrderDetail) it.next();
			if (poDetail.getPurchaseOrder() != this) {
				poDetail.setPurchaseOrder(this);
			}
		}
		this.purchaseOrderDetail = purchaseOrderDetail;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}