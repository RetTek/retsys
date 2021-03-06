package org.rettek.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.rettek.model.Project;

/**
 * 
 */
@Stateless
@Path("/projects")
public class ProjectEndpoint
{
   @PersistenceContext(unitName = "retsys-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public Project create(Project entity)
   {
	   if (entity.getId()==null)
		  {
	      em.persist(entity);
		  }
		  else
		  {
			  em.merge(entity);
		  }
		  
	      return entity;
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Project entity = em.find(Project.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<Project> findByIdQuery = em.createQuery("SELECT DISTINCT p FROM Project p WHERE p.id = :entityId ORDER BY p.id", Project.class);
      findByIdQuery.setParameter("entityId", id);
      Project entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Path("name/{name:^[a-zA-Z0-9_]*$}")
   @Produces("application/json")
   public List<Project> findByName(@PathParam("name") String name)
   {
      TypedQuery<Project> findByNameQuery = em.createQuery("SELECT DISTINCT p FROM Project p WHERE p.name like :entityName ORDER BY p.name", Project.class);
      findByNameQuery.setParameter("entityName", name+"%");
      List<Project> entity;
      try
      {
         entity = findByNameQuery.getResultList();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      
      return entity;
   }
   
   @GET
   @Produces("application/json")
   public List<Project> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<Project> findAllQuery = em.createQuery("SELECT DISTINCT p FROM Project p ORDER BY p.id", Project.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<Project> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Project entity)
   {
      try
      {
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }
}
