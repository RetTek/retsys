package org.rettek.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class PurchaseOrder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	private Vendor vendor;

	@ManyToOne
	private Client client;

	@Column(length = 4000)
	private String deliveryAddress;

	@OneToMany(cascade = CascadeType.ALL)
	private List<PurchaseOrderDetail> purchaseOrderDetail;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
		result += ", version: " + version;
		if (date != null)
			result += ", date: " + date;
		if (vendor != null)
			result += ", vendor: " + vendor;
		if (client != null)
			result += ", client: " + client;
		if (deliveryAddress != null && !deliveryAddress.trim().isEmpty())
			result += ", deliveryAddress: " + deliveryAddress;
		return result;
	}

	public void newVendor() {
		this.vendor = new Vendor();
	}

	public void newClient() {
		this.client = new Client();
	}

	private List<PurchaseOrderDetail> getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}

	private void setPurchaseOrderDetail(
			List<PurchaseOrderDetail> purchaseOrderDetail) {
		this.purchaseOrderDetail = purchaseOrderDetail;
	}
}