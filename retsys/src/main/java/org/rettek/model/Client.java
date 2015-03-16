package org.rettek.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.Override;

@Entity
@XmlRootElement
public class Client implements Serializable
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

   @Column(length = 2000)
   private String address;

   @Column
   private String phone;

   @Column
   private String mobile;

   @Column(length = 2000)
   private String remarks;

   @Column
   private String email;

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
      if (!(obj instanceof Client))
      {
         return false;
      }
      Client other = (Client) obj;
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

   public void setName(String Name)
   {
      this.name = Name;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress(String Address)
   {
      this.address = Address;
   }

   public String getPhone()
   {
      return phone;
   }

   public void setPhone(String Phone)
   {
      this.phone = Phone;
   }

   public String getMobile()
   {
      return mobile;
   }

   public void setMobile(String Mobile)
   {
      this.mobile = Mobile;
   }

   public String getRemarks()
   {
      return remarks;
   }

   public void setRemarks(String remarks)
   {
      this.remarks = remarks;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
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
      if (address != null && !address.trim().isEmpty())
         result += ", address: " + address;
      if (phone != null && !phone.trim().isEmpty())
         result += ", phone: " + phone;
      if (mobile != null && !mobile.trim().isEmpty())
         result += ", mobile: " + mobile;
      if (remarks != null && !remarks.trim().isEmpty())
         result += ", remarks: " + remarks;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      return result;
   }
}