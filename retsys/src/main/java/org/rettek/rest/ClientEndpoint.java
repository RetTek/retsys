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
import org.rettek.model.Client;

/**
 * 
 */
@Stateless
@Path("/clients")
public class ClientEndpoint
{
   @PersistenceContext(unitName = "retsys-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Client entity)
   {
      if(entity.getId()!=null){
    	em.merge(entity);  
      }else{
    	  em.persist(entity);  
      }
	   
      return Response.created(UriBuilder.fromResource(ClientEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Client entity = em.find(Client.class, id);
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
      TypedQuery<Client> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM Client c WHERE c.id = :entityId ORDER BY c.id", Client.class);
      findByIdQuery.setParameter("entityId", id);
      Client entity;
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
   @Path("/name/{name:^[a-zA-Z0-9_]*$}")
   @Produces("application/json")
   public List<Client> findByName(@PathParam("name") String name)
   {
      TypedQuery<Client> findByNameQuery = em.createQuery("SELECT DISTINCT c FROM Client c WHERE c.name like :entityName ORDER BY c.name", Client.class);
      findByNameQuery.setParameter("entityName", name+"%");
      List<Client> entity;
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
   public List<Client> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<Client> findAllQuery = em.createQuery("SELECT DISTINCT c FROM Client c ORDER BY c.id", Client.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<Client> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Client entity)
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
