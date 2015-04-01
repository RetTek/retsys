package org.rettek.dto;

import org.rettek.model.CreditNoteDetail;
import org.rettek.model.Item;

public class CreditNoteDetailsDTO {
	private Long id;
	private Item item;
	private double returnQuantity;
	private double amount;
	private boolean confirm;

	public CreditNoteDetailsDTO(CreditNoteDetail details) {
		this.setId(details.getId());
		this.setItem(details.getItem());
		this.setReturnQuantity(details.getReturnQuantity());
		this.setAmount(details.getAmount());
		this.setConfirm(details.isConfirm());
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public double getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(double returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
}
