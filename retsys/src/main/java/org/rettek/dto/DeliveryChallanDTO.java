package org.rettek.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.rettek.model.Audit;
import org.rettek.model.DeliveryChallan;
import org.rettek.model.DeliveryChallanDetail;
import org.rettek.model.Project;
import org.rettek.model.PurchaseOrderDetail;

public class DeliveryChallanDTO {
	private Long id;
	private Date challanDate;
	private Project project;
	private Audit audit;
	private DeliveryChallan originalDeliveryChallan;
	
	private boolean isDelivery;
	private String deliveryMode;
	private String concernPerson;
	
	public DeliveryChallan getOriginalDeliveryChallan() {
		return originalDeliveryChallan;
	}
	public void setOriginalDeliveryChallan(DeliveryChallan originalDeliveryChallan) {
		this.originalDeliveryChallan = originalDeliveryChallan;
	}
	
	private Set<DeliveryChallanDetailDTO> deliveryChallanDetail = new HashSet<DeliveryChallanDetailDTO>();
	
	public DeliveryChallanDTO(DeliveryChallan deliveryChallan){
		this.id = deliveryChallan.getId();
		this.challanDate = deliveryChallan.getChallanDate();
		this.project = deliveryChallan.getProject();
		this.audit = deliveryChallan.getAudit();
		
		Iterator<DeliveryChallanDetail> dcDetail = deliveryChallan.getDeliveryChallanDetail().iterator();
		while (dcDetail.hasNext()) {
			DeliveryChallanDetail deliveryChallanDetail = (DeliveryChallanDetail) dcDetail
					.next();
			this.deliveryChallanDetail.add(new DeliveryChallanDetailDTO(deliveryChallanDetail));
		}
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Set<DeliveryChallanDetailDTO> getDeliveryChallanDetail() {
		return deliveryChallanDetail;
	}
	public void setDeliveryChallanDetail(
			Set<DeliveryChallanDetailDTO> deliveryChallanDTO) {
		this.deliveryChallanDetail = deliveryChallanDTO;
	}
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
	
}
