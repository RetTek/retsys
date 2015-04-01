package org.rettek.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.lang.Override;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@XmlRootElement
public class CreditNote implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;

   @ManyToOne
   private Vendor vendor;

   @Column(nullable = false)
   private double totalAmount;

   @Column
   private String remarks;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditNote", orphanRemoval = true)
   private List<CreditNoteDetail> creditNoteDetails = new ArrayList<CreditNoteDetail>();

   @Column(nullable = false)
   @Temporal(TemporalType.DATE)
   private Date creationDate;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof CreditNote))
      {
         return false;
      }
      CreditNote other = (CreditNote) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   public Vendor getVendor()
   {
      return this.vendor;
   }

   public void setVendor(final Vendor vendor)
   {
      this.vendor = vendor;
   }

   public double getTotalAmount()
   {
      return totalAmount;
   }

   public void setTotalAmount(double totalAmount)
   {
      this.totalAmount = totalAmount;
   }

   public String getRemarks()
   {
      return remarks;
   }

   public void setRemarks(String remarks)
   {
      this.remarks = remarks;
   }

   public List<CreditNoteDetail> getCreditNoteDetails()
   {
      return this.creditNoteDetails;
   }

   public void setCreditNoteDetails(
         final List<CreditNoteDetail> creditNoteDetails)
   {
      Iterator<CreditNoteDetail> details = creditNoteDetails.iterator();
      while (details.hasNext())
      {
         CreditNoteDetail detail = (CreditNoteDetail) details.next();
         if (detail.getCreditNote() != this)
         {
            detail.setCreditNote(this);
         }
      }

      this.creditNoteDetails = creditNoteDetails;
   }

   public Date getCreationDate()
   {
      return creationDate;
   }

   public void setCreationDate(Date creationDate)
   {
      this.creationDate = creationDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "totalAmount: " + totalAmount;
      if (remarks != null && !remarks.trim().isEmpty())
         result += ", remarks: " + remarks;
      return result;
   }
}