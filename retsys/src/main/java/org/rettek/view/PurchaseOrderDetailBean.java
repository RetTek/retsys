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

import org.rettek.model.PurchaseOrderDetail;
import org.rettek.model.PurchaseOrder;

/**
 * Backing bean for PurchaseOrderDetail entities.
 * <p/>
 * This class provides CRUD functionality for all PurchaseOrderDetail entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class PurchaseOrderDetailBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving PurchaseOrderDetail entities
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

   private PurchaseOrderDetail purchaseOrderDetail;

   public PurchaseOrderDetail getPurchaseOrderDetail()
   {
      return this.purchaseOrderDetail;
   }

   public void setPurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail)
   {
      this.purchaseOrderDetail = purchaseOrderDetail;
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
         this.purchaseOrderDetail = this.example;
      }
      else
      {
         this.purchaseOrderDetail = findById(getId());
      }
   }

   public PurchaseOrderDetail findById(Long id)
   {

      return this.entityManager.find(PurchaseOrderDetail.class, id);
   }

   /*
    * Support updating and deleting PurchaseOrderDetail entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.purchaseOrderDetail);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.purchaseOrderDetail);
            return "view?faces-redirect=true&id=" + this.purchaseOrderDetail.getId();
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
         PurchaseOrderDetail deletableEntity = findById(getId());

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
    * Support searching PurchaseOrderDetail entities with pagination
    */

   private int page;
   private long count;
   private List<PurchaseOrderDetail> pageItems;

   private PurchaseOrderDetail example = new PurchaseOrderDetail();

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

   public PurchaseOrderDetail getExample()
   {
      return this.example;
   }

   public void setExample(PurchaseOrderDetail example)
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
      Root<PurchaseOrderDetail> root = countCriteria.from(PurchaseOrderDetail.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<PurchaseOrderDetail> criteria = builder.createQuery(PurchaseOrderDetail.class);
      root = criteria.from(PurchaseOrderDetail.class);
      TypedQuery<PurchaseOrderDetail> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<PurchaseOrderDetail> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      PurchaseOrder purchaseOrder = this.example.getPurchaseOrder();
      if (purchaseOrder != null)
      {
         predicatesList.add(builder.equal(root.get("purchaseOrder"), purchaseOrder));
      }
      Integer quantity = this.example.getQuantity();
      if (quantity != null && quantity.intValue() != 0)
      {
         predicatesList.add(builder.equal(root.get("quantity"), quantity));
      }
      String confirm = this.example.getConfirm();
      if (confirm != null && !"".equals(confirm))
      {
         predicatesList.add(builder.like(builder.lower(root.<String> get("confirm")), '%' + confirm.toLowerCase() + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<PurchaseOrderDetail> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back PurchaseOrderDetail entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<PurchaseOrderDetail> getAll()
   {

      CriteriaQuery<PurchaseOrderDetail> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(PurchaseOrderDetail.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(PurchaseOrderDetail.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final PurchaseOrderDetailBean ejbProxy = this.sessionContext.getBusinessObject(PurchaseOrderDetailBean.class);

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

            return String.valueOf(((PurchaseOrderDetail) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private PurchaseOrderDetail add = new PurchaseOrderDetail();

   public PurchaseOrderDetail getAdd()
   {
      return this.add;
   }

   public PurchaseOrderDetail getAdded()
   {
      PurchaseOrderDetail added = this.add;
      this.add = new PurchaseOrderDetail();
      return added;
   }
}
