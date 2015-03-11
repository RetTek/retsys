package org.rettek.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.rettek.model.Client;
import org.rettek.model.Project;
import org.rettek.model.PurchaseOrder;
import org.rettek.model.PurchaseOrderDetail;
import org.rettek.model.Vendor;

public class PurchaseOrderDTO {
	private Long id;
	private int version;
	private Date date;
	private Vendor vendor;
	private Project project;
	private String deliveryAddress;
	private Set<PurchaseOrderDetailDTO> purchaseOrderDetail = new HashSet<PurchaseOrderDetailDTO>();
	
	public PurchaseOrderDTO(PurchaseOrder po) {
		this.id=po.getId();
		this.version=po.getVersion();
		this.date=po.getDate();
		this.vendor=po.getVendor();
		this.setProject(po.getProject());
		this.deliveryAddress=po.getDeliveryAddress();
		
		Iterator<PurchaseOrderDetail> poDetail = po.getPurchaseOrderDetail().iterator();
		while (poDetail.hasNext()) {
			PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail) poDetail
					.next();
			this.purchaseOrderDetail.add(new PurchaseOrderDetailDTO(purchaseOrderDetail));
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
	public Set<PurchaseOrderDetailDTO> getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}
	public void setPurchaseOrderDetail(Set<PurchaseOrderDetailDTO> purchaseOrderDetail) {
		
		this.purchaseOrderDetail = purchaseOrderDetail;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
