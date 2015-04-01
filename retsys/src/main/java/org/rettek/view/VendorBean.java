package org.rettek.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.rettek.model.Vendor;

/**
 * Backing bean for Vendor entities.
 * <p/>
 * This class provides CRUD functionality for all Vendor entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class VendorBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Vendor entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private Vendor vendor;

   public Vendor getVendor()
   {
      return this.vendor;
   }

   public void setVendor(Vendor vendor)
   {
      this.vendor = vendor;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(unitName = "retsys-persistence-unit", type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      this.conversation.setTimeout(1800000L);
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
         this.conversation.setTimeout(1800000L);
      }

      if (this.id == null)
      {
         this.vendor = this.example;
      }
      else
      {
         this.vendor = findById(getId());
      }
   }

   public Vendor findById(Long id)
   {

      return this.entityManager.find(Vendor.class, id);
   }

   /*
    * Support updating and deleting Vendor entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.vendor);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.vendor);
            return "view?faces-redirect=true&id=" + this.vendor.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         Vendor deletableEntity = findById(getId());

         this.entityManager.remove(deletableEntity);
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Vendor entities with pagination
    */

   private int page;
   private long count;
   private List<Vendor> pageItems;

   private Vendor example = new Vendor();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Vendor getExample()
   {
      return this.example;
   }

   public void setExample(Vendor example)
   {
      this.example = example;
   }

   public String search()
   {
      this.page = 0;
      return null;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Vendor> root = countCriteria.from(Vendor.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Vendor> criteria = builder.createQuery(Vendor.class);
      root = criteria.from(Vendor.class);
      TypedQuery<Vendor> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Vendor> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String name = this.example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("name")), '%' + name.toLowerCase() + '%'));
      }
      String address = this.example.getAddress();
      if (address != null && !"".equals(address))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("address")), '%' + address.toLowerCase() + '%'));
      }
      String phone = this.example.getPhone();
      if (phone != null && !"".equals(phone))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("phone")), '%' + phone.toLowerCase() + '%'));
      }
      String mobile = this.example.getMobile();
      if (mobile != null && !"".equals(mobile))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("mobile")), '%' + mobile.toLowerCase() + '%'));
      }
      String remarks = this.example.getRemarks();
      if (remarks != null && !"".equals(remarks))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("remarks")), '%' + remarks.toLowerCase() + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Vendor> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Vendor entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Vendor> getAll()
   {

      CriteriaQuery<Vendor> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Vendor.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Vendor.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final VendorBean ejbProxy = this.sessionContext.getBusinessObject(VendorBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Vendor) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Vendor add = new Vendor();

   public Vendor getAdd()
   {
      return this.add;
   }

   public Vendor getAdded()
   {
      Vendor added = this.add;
      this.add = new Vendor();
      return added;
   }
}
