package org.rettek.dto;

import java.util.Date;

import javax.persistence.Column;

import org.rettek.model.Item;
import org.rettek.model.PurchaseOrderDetail;

public class PurchaseOrderDetailDTO {
	private Long id;
	private Item item;
	private Double quantity;
	private String confirm;
	private Date receivedDate;
	private String billNo;
	private String supervisor;

	public PurchaseOrderDetailDTO(PurchaseOrderDetail purchaseOrderDetail) {
		setId(purchaseOrderDetail.getId());
		setItem(purchaseOrderDetail.getItem());
		setQuantity(purchaseOrderDetail.getQuantity());
		setConfirm(purchaseOrderDetail.getConfirm());
		setReceivedDate(purchaseOrderDetail.getReceivedDate());
		setBillNo(purchaseOrderDetail.getBillNo());
		setSupervisor(purchaseOrderDetail.getSupervisor());
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

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

}
