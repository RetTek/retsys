package org.rettek.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.rettek.model.PurchaseOrder;
import javax.persistence.ManyToOne;
import org.rettek.model.Item;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class PurchaseOrderDetail implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @ManyToOne
   private PurchaseOrder purchaseOrder;

   @ManyToMany
   private Set<Item> item = new HashSet<Item>();

   @Column
   private Integer quantity;

   @Column(length = 1)
   private String confirm;

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
      if (!(obj instanceof PurchaseOrderDetail))
      {
         return false;
      }
      PurchaseOrderDetail other = (PurchaseOrderDetail) obj;
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

   public PurchaseOrder getPurchaseOrder()
   {
      return this.purchaseOrder;
   }

   public void setPurchaseOrder(final PurchaseOrder purchaseOrder)
   {
      this.purchaseOrder = purchaseOrder;
   }

   public Set<Item> getItem()
   {
      return this.item;
   }

   public void setItem(final Set<Item> item)
   {
      this.item = item;
   }

   public Integer getQuantity()
   {
      return quantity;
   }

   public void setQuantity(Integer quantity)
   {
      this.quantity = quantity;
   }

   public String getConfirm()
   {
      return confirm;
   }

   public void setConfirm(String confirm)
   {
      this.confirm = confirm;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      result += ", version: " + version;
      if (purchaseOrder != null)
         result += ", purchaseOrder: " + purchaseOrder;
      if (item != null)
         result += ", item: " + item;
      if (quantity != null)
         result += ", quantity: " + quantity;
      if (confirm != null && !confirm.trim().isEmpty())
         result += ", confirm: " + confirm;
      return result;
   }
}