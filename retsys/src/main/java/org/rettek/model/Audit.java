package org.rettek.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Audit implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;

   @Column(nullable = false)
   private String createdBy;

   @Column
   @Temporal(TemporalType.DATE)
   private Date createdOn;

   @Column(nullable = true)
   private String modifiedBy;

   @Column
   @Temporal(TemporalType.DATE)
   private Date modifiedOn;

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
      if (!(obj instanceof Audit))
      {
         return false;
      }
      Audit other = (Audit) obj;
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

   public String getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(String createdBy)
   {
      this.createdBy = createdBy;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

   public String getModifiedBy()
   {
      return modifiedBy;
   }

   public void setModifiedBy(String modifier)
   {
      this.modifiedBy = modifier;
   }

   public Date getModifiedOn()
   {
      return modifiedOn;
   }

   public void setModifiedOn(Date modifiedOn)
   {
      this.modifiedOn = modifiedOn;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (createdBy != null && !createdBy.trim().isEmpty())
         result += "createdBy: " + createdBy;
      if (modifiedBy != null && !modifiedBy.trim().isEmpty())
         result += ", modifiedBy: " + modifiedBy;
      return result;
   }
}