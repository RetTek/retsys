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

import org.rettek.model.ClientChallan;
import org.rettek.model.Project;

/**
 * Backing bean for ClientChallan entities.
 * <p/>
 * This class provides CRUD functionality for all ClientChallan entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class ClientChallanBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving ClientChallan entities
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

   private ClientChallan clientChallan;

   public ClientChallan getClientChallan()
   {
      return this.clientChallan;
   }

   public void setClientChallan(ClientChallan clientChallan)
   {
      this.clientChallan = clientChallan;
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
         this.clientChallan = this.example;
      }
      else
      {
         this.clientChallan = findById(getId());
      }
   }

   public ClientChallan findById(Long id)
   {

      return this.entityManager.find(ClientChallan.class, id);
   }

   /*
    * Support updating and deleting ClientChallan entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.clientChallan);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.clientChallan);
            return "view?faces-redirect=true&id=" + this.clientChallan.getId();
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
         ClientChallan deletableEntity = findById(getId());

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
    * Support searching ClientChallan entities with pagination
    */

   private int page;
   private long count;
   private List<ClientChallan> pageItems;

   private ClientChallan example = new ClientChallan();

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

   public ClientChallan getExample()
   {
      return this.example;
   }

   public void setExample(ClientChallan example)
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
      Root<ClientChallan> root = countCriteria.from(ClientChallan.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<ClientChallan> criteria = builder.createQuery(ClientChallan.class);
      root = criteria.from(ClientChallan.class);
      TypedQuery<ClientChallan> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<ClientChallan> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      Project project = this.example.getProject();
      if (project != null)
      {
         predicatesList.add(builder.equal(root.get("project"), project));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<ClientChallan> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back ClientChallan entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<ClientChallan> getAll()
   {

      CriteriaQuery<ClientChallan> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(ClientChallan.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(ClientChallan.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final ClientChallanBean ejbProxy = this.sessionContext.getBusinessObject(ClientChallanBean.class);

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

            return String.valueOf(((ClientChallan) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private ClientChallan add = new ClientChallan();

   public ClientChallan getAdd()
   {
      return this.add;
   }

   public ClientChallan getAdded()
   {
      ClientChallan added = this.add;
      this.add = new ClientChallan();
      return added;
   }
}
