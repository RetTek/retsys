package org.rettek.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import org.rettek.model.Product;
import java.lang.Override;

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
   private String unit;

   @Column
   private String size;

   @Column
   private String billno;

   @Column
   private String site;

   @Column(length = 2000)
   private String remarks;

   @Column
   private Double quantity;

   @Column
   private String transportmode;

   @Column
   private double transportcharge;

   @Column
   private String supervisor;

   @ManyToOne
   private Vendor vendor;

   @Column
   private String godownName;

   @Column
   private String location1;

   @Column
   private String location2;

   @Column
   private String location3;

   @Column
   private String drawerNo;

   @ManyToOne
   private Product product;

   @Column
   private Float minreorder;

   @Column
   private Float discount_percentage;

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

   public String getUnit()
   {
      return unit;
   }

   public void setUnit(String unit)
   {
      this.unit = unit;
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

   public void setQuantity(Double quantity)
   {
      this.quantity = quantity;
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

   public Vendor getVendor()
   {
      return this.vendor;
   }

   public void setVendor(final Vendor vendor)
   {
      this.vendor = vendor;
   }

   public String getGodownName()
   {
      return godownName;
   }

   public void setGodownName(String godownName)
   {
      this.godownName = godownName;
   }

   public String getLocation1()
   {
      return location1;
   }

   public void setLocation1(String location1)
   {
      this.location1 = location1;
   }

   public String getLocation2()
   {
      return location2;
   }

   public void setLocation2(String location2)
   {
      this.location2 = location2;
   }

   public String getLocation3()
   {
      return location3;
   }

   public void setLocation3(String location3)
   {
      this.location3 = location3;
   }

   public String getDrawerNo()
   {
      return drawerNo;
   }

   public void setDrawerNo(String drawerNo)
   {
      this.drawerNo = drawerNo;
   }

   public Product getProduct()
   {
      return this.product;
   }

   public void setProduct(final Product product)
   {
      this.product = product;
   }

   public Float getMinreorder()
   {
      return minreorder;
   }

   public void setMinreorder(Float minreorder)
   {
      this.minreorder = minreorder;
   }

   public Float getDiscount_percentage()
   {
      return discount_percentage;
   }

   public void setDiscount_percentage(Float discount_percentage)
   {
      this.discount_percentage = discount_percentage;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      result += ", rate: " + rate;
      if (brand != null && !brand.trim().isEmpty())
         result += ", brand: " + brand;
      if (color != null && !color.trim().isEmpty())
         result += ", color: " + color;
      if (unit != null && !unit.trim().isEmpty())
         result += ", unit: " + unit;
      if (size != null && !size.trim().isEmpty())
         result += ", size: " + size;
      if (billno != null && !billno.trim().isEmpty())
         result += ", billno: " + billno;
      if (site != null && !site.trim().isEmpty())
         result += ", site: " + site;
      if (remarks != null && !remarks.trim().isEmpty())
         result += ", remarks: " + remarks;
      if (quantity != null)
         result += ", quantity: " + quantity;
      if (transportmode != null && !transportmode.trim().isEmpty())
         result += ", transportmode: " + transportmode;
      result += ", transportcharge: " + transportcharge;
      if (supervisor != null && !supervisor.trim().isEmpty())
         result += ", supervisor: " + supervisor;
      if (godownName != null && !godownName.trim().isEmpty())
         result += ", godownName: " + godownName;
      if (location1 != null && !location1.trim().isEmpty())
         result += ", location1: " + location1;
      if (location2 != null && !location2.trim().isEmpty())
         result += ", location2: " + location2;
      if (location3 != null && !location3.trim().isEmpty())
         result += ", location3: " + location3;
      if (drawerNo != null && !drawerNo.trim().isEmpty())
         result += ", drawerNo: " + drawerNo;
      if (minreorder != null)
         result += ", minreorder: " + minreorder;
      if (discount_percentage != null)
         result += ", discount_percentage: " + discount_percentage;
      return result;
   }

}
