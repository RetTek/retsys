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

import org.rettek.model.DeliveryChallanDetail;
import org.rettek.model.DeliveryChallan;
import org.rettek.model.Item;

/**
 * Backing bean for ProjectItems entities.
 * <p/>
 * This class provides CRUD functionality for all ProjectItems entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DeliveryChallanDetailBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving ProjectItems entities
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

   private DeliveryChallanDetail projectItems;

   public DeliveryChallanDetail getProjectItems()
   {
      return this.projectItems;
   }

   public void setProjectItems(DeliveryChallanDetail projectItems)
   {
      this.projectItems = projectItems;
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
         this.projectItems = this.example;
      }
      else
      {
         this.projectItems = findById(getId());
      }
   }

   public DeliveryChallanDetail findById(Long id)
   {

      return this.entityManager.find(DeliveryChallanDetail.class, id);
   }

   /*
    * Support updating and deleting ProjectItems entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.projectItems);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.projectItems);
            return "view?faces-redirect=true&id=" + this.projectItems.getId();
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
         DeliveryChallanDetail deletableEntity = findById(getId());

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
    * Support searching ProjectItems entities with pagination
    */

   private int page;
   private long count;
   private List<DeliveryChallanDetail> pageItems;

   private DeliveryChallanDetail example = new DeliveryChallanDetail();

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

   public DeliveryChallanDetail getExample()
   {
      return this.example;
   }

   public void setExample(DeliveryChallanDetail example)
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
      Root<DeliveryChallanDetail> root = countCriteria.from(DeliveryChallanDetail.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<DeliveryChallanDetail> criteria = builder.createQuery(DeliveryChallanDetail.class);
      root = criteria.from(DeliveryChallanDetail.class);
      TypedQuery<DeliveryChallanDetail> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<DeliveryChallanDetail> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      Item item = this.example.getItem();
      if (item != null)
      {
         predicatesList.add(builder.equal(root.get("item"), item));
      }
      DeliveryChallan challan = this.example.getChallan();
      if (challan != null)
      {
         predicatesList.add(builder.equal(root.get("challan"), challan));
      }
      Double quantity = this.example.getQuantity();
      if (quantity != 0)
      {
         predicatesList.add(builder.equal(root.get("quantity"), quantity));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<DeliveryChallanDetail> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back ProjectItems entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<DeliveryChallanDetail> getAll()
   {

      CriteriaQuery<DeliveryChallanDetail> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(DeliveryChallanDetail.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(DeliveryChallanDetail.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final DeliveryChallanDetailBean ejbProxy = this.sessionContext.getBusinessObject(DeliveryChallanDetailBean.class);

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

            return String.valueOf(((DeliveryChallanDetail) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private DeliveryChallanDetail add = new DeliveryChallanDetail();

   public DeliveryChallanDetail getAdd()
   {
      return this.add;
   }

   public DeliveryChallanDetail getAdded()
   {
      DeliveryChallanDetail added = this.add;
      this.add = new DeliveryChallanDetail();
      return added;
   }
}
