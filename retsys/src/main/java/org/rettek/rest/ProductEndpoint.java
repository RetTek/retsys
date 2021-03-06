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
import org.rettek.model.Product;

/**
 * 
 */
@Stateless
@Path("/products")
public class ProductEndpoint
{
   @PersistenceContext(unitName = "retsys-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public Product create(Product entity)
   {
	   if (entity.getId()==null)
		  {
	      em.persist(entity);
		  }
		  else
		  {
			  em.merge(entity);
		  }
      //return Response.created(UriBuilder.fromResource(ProductEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
	   return entity;
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Product entity = em.find(Product.class, id);
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
      TypedQuery<Product> findByIdQuery = em.createQuery("SELECT DISTINCT p FROM Product p WHERE p.id = :entityId ORDER BY p.id", Product.class);
      findByIdQuery.setParameter("entityId", id);
      Product entity;
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
   @Path("/name/{name:[0-9a-zA-Z][0-9a-zA-Z]*}")
   @Produces("application/json")
   public List<Product> findByName(@PathParam("name") String name)
   {
	   TypedQuery<Product> findAllQuery = em.createQuery("SELECT DISTINCT p FROM Product p where name LIKE :searchKeyword ORDER BY p.name", Product.class);
	   findAllQuery.setParameter("searchKeyword", name+"%");	      
	   	  
	      final List<Product> results = findAllQuery.getResultList();
	      return results;
   }

   @GET
   @Produces("application/json")
   public List<Product> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<Product> findAllQuery = em.createQuery("SELECT DISTINCT p FROM Product p ORDER BY p.id", Product.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<Product> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Product entity)
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
