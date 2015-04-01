package org.rettek.dto;

import org.rettek.model.Item;
import org.rettek.model.PurchaseOrderDetail;

public class PurchaseOrderDetailDTO {
	private Long id;
	private Item item;
	private Double quantity;
	private String confirm;

	public PurchaseOrderDetailDTO(PurchaseOrderDetail purchaseOrderDetail) {
		this.setId(purchaseOrderDetail.getId());
		this.setItem(purchaseOrderDetail.getItem());
		this.setQuantity(purchaseOrderDetail.getQuantity());
		this.setConfirm(purchaseOrderDetail.getConfirm());
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

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

}
