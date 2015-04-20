package org.rettek.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.rettek.model.Item;
import javax.persistence.ManyToOne;
import org.rettek.model.DeliveryChallan;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class DeliveryChallanDetail implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	private Item item;

	@ManyToOne
	private DeliveryChallan deliveryChallan;

	@Column
	private Double quantity;

	@Column
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Column
	private String units;

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
		if (!(obj instanceof DeliveryChallanDetail)) {
			return false;
		}
		DeliveryChallanDetail other = (DeliveryChallanDetail) obj;
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

	public Item getItem() {
		return this.item;
	}

	public void setItem(final Item item) {
		this.item = item;
	}

	public DeliveryChallan getChallan() {
		return this.deliveryChallan;
	}

	public void setChallan(final DeliveryChallan deliveryChallan) {
		this.deliveryChallan = deliveryChallan;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		result += "quantity: " + quantity;
		return result;
	}
}