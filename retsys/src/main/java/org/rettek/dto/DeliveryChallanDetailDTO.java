package org.rettek.dto;

import org.rettek.model.DeliveryChallanDetail;
import org.rettek.model.Item;

public class DeliveryChallanDetailDTO {
	private Long id;
	private Item item;
	private Double quantity;
	private Double amount;
	private String units;
	
	

	public DeliveryChallanDetailDTO(DeliveryChallanDetail deliveryChallanDetail){
		this.setId(deliveryChallanDetail.getId());
		this.setItem(deliveryChallanDetail.getItem());
		this.setQuantity(deliveryChallanDetail.getQuantity());
		this.setAmount(deliveryChallanDetail.getAmount());
		this.setUnits(deliveryChallanDetail.getUnits());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
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

}
