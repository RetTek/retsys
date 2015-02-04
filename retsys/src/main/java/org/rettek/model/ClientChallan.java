package org.rettek.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.rettek.model.Project;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ClientChallan implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @ManyToOne
   private Project project;

   @Column
   @Temporal(TemporalType.DATE)
   private Date challanDate;

   @Column
   private boolean isDelivery;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof ClientChallan))
      {
         return false;
      }
      ClientChallan other = (ClientChallan) obj;
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

   public Project getProject()
   {
      return this.project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public Date getChallanDate()
   {
      return challanDate;
   }

   public void setChallanDate(Date challanDate)
   {
      this.challanDate = challanDate;
   }

   public boolean isIsDelivery()
   {
      return isDelivery;
   }

   public void setIsDelivery(boolean isDelivery)
   {
      this.isDelivery = isDelivery;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "isDelivery: " + isDelivery;
      return result;
   }
}