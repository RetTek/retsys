package org.rettek.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.rettek.model.Vendor;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Item implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column(length = 1000, nullable = false)
   private String name;

   @Column
   private double rate;

   @Column
   private String brand;

   @Column
   private String color;

   @Column
   private String units;

   @Column
   private String size;

   @Column
   private String billno;

   @Column
   private String site;

   @Column(length = 2000)
   private String remarks;

   @Column
   private double quantity;

   @Column
   private Vendor vendor;

   @Column
   private String transportmode;

   @Column
   private double transportcharge;

   @Column
   private String supervisor;

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
      if (!(obj instanceof Item))
      {
         return false;
      }
      Item other = (Item) obj;
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

   public double getRate()
   {
      return rate;
   }

   public void setRate(double rate)
   {
      this.rate = rate;
   }

   public String getBrand()
   {
      return brand;
   }

   public void setBrand(String brand)
   {
      this.brand = brand;
   }

   public String getColor()
   {
      return color;
   }

   public void setColor(String color)
   {
      this.color = color;
   }

   public String getUnits()
   {
      return units;
   }

   public void setUnits(String units)
   {
      this.units = units;
   }

   public String getSize()
   {
      return size;
   }

   public void setSize(String size)
   {
      this.size = size;
   }

   public String getBillno()
   {
      return billno;
   }

   public void setBillno(String billno)
   {
      this.billno = billno;
   }

   public String getSite()
   {
      return site;
   }

   public void setSite(String site)
   {
      this.site = site;
   }

   public String getRemarks()
   {
      return remarks;
   }

   public void setRemarks(String remarks)
   {
      this.remarks = remarks;
   }

   public double getQuantity()
   {
      return quantity;
   }

   public void setQuantity(double quantity)
   {
      this.quantity = quantity;
   }

   public Vendor getVendor()
   {
      return vendor;
   }

   public void setVendor(Vendor vendor)
   {
      this.vendor = vendor;
   }

   public String getTransportmode()
   {
      return transportmode;
   }

   public void setTransportmode(String transportmode)
   {
      this.transportmode = transportmode;
   }

   public double getTransportcharge()
   {
      return transportcharge;
   }

   public void setTransportcharge(double transportcharge)
   {
      this.transportcharge = transportcharge;
   }

   public String getSupervisor()
   {
      return supervisor;
   }

   public void setSupervisor(String supervisor)
   {
      this.supervisor = supervisor;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      result += ", version: " + version;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      result += ", rate: " + rate;
      if (brand != null && !brand.trim().isEmpty())
         result += ", brand: " + brand;
      if (color != null && !color.trim().isEmpty())
         result += ", color: " + color;
      if (units != null && !units.trim().isEmpty())
         result += ", units: " + units;
      if (size != null && !size.trim().isEmpty())
         result += ", size: " + size;
      if (billno != null && !billno.trim().isEmpty())
         result += ", billno: " + billno;
      if (site != null && !site.trim().isEmpty())
         result += ", site: " + site;
      if (remarks != null && !remarks.trim().isEmpty())
         result += ", remarks: " + remarks;
      result += ", quantity: " + quantity;
      if (vendor != null)
         result += ", vendor: " + vendor;
      if (transportmode != null && !transportmode.trim().isEmpty())
         result += ", transportmode: " + transportmode;
      result += ", transportcharge: " + transportcharge;
      if (supervisor != null && !supervisor.trim().isEmpty())
         result += ", supervisor: " + supervisor;
      return result;
   }
}