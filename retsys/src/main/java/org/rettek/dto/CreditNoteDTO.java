package org.rettek.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.rettek.model.CreditNote;
import org.rettek.model.CreditNoteDetail;
import org.rettek.model.Vendor;

public class CreditNoteDTO {
	private Long id;
	private Date creationDate;
	private Vendor vendor;
	private double totalAmount;
	private String remarks;
	private List<CreditNoteDetailsDTO> creditNoteDetails = new ArrayList<CreditNoteDetailsDTO>();

	public CreditNoteDTO(CreditNote creditNote) {
		this.setId(creditNote.getId());
		this.setVendor(creditNote.getVendor());
		this.setTotalAmount(creditNote.getTotalAmount());
		this.setRemarks(creditNote.getRemarks());
		this.setCreationDate(creditNote.getCreationDate());

		Iterator<CreditNoteDetail> details = creditNote.getCreditNoteDetails()
				.iterator();
		while (details.hasNext()) {
			CreditNoteDetail creditNoteDetails = (CreditNoteDetail) details
					.next();
			this.getCreditNoteDetails().add(
					new CreditNoteDetailsDTO(creditNoteDetails));
		}
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<CreditNoteDetailsDTO> getCreditNoteDetails() {
		return creditNoteDetails;
	}

	public void setCreditNoteDetails(
			List<CreditNoteDetailsDTO> creditNoteDetails) {
		this.creditNoteDetails = creditNoteDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
