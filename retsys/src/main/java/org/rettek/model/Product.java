package org.rettek.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Product implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;

   @Column(length = 1000, nullable = false)
   private String name;

   @Column(length = 2000)
   private String remarks;

   @Column
   private String prodDesc;

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
      if (!(obj instanceof Product))
      {
         return false;
      }
      Product other = (Product) obj;
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

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getRemarks()
   {
      return remarks;
   }

   public void setRemarks(String remarks)
   {
      this.remarks = remarks;
   }

   public String getProdDesc()
   {
      return prodDesc;
   }

   public void setProdDesc(String prodDesc)
   {
      this.prodDesc = prodDesc;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (remarks != null && !remarks.trim().isEmpty())
         result += ", remarks: " + remarks;
      if (prodDesc != null && !prodDesc.trim().isEmpty())
         result += ", prodDesc: " + prodDesc;
      return result;
   }
}