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

import org.rettek.model.User;

/**
 * 
 */
@Stateless
@Path("/users")
public class UserEndpoint
{
   @PersistenceContext(unitName = "retsys-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(User entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(UserEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      User entity = em.find(User.class, id);
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
      TypedQuery<User> findByIdQuery = em.createQuery("SELECT DISTINCT u FROM User u WHERE u.id = :entityId ORDER BY u.id", User.class);
      findByIdQuery.setParameter("entityId", id);
      User entity;
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
   public List<User> findByName(@PathParam("name") String name)
   {
      TypedQuery<User> findByNameQuery = em.createQuery("SELECT DISTINCT u FROM User u WHERE i.name like :entityName ORDER BY u.id", User.class);
      findByNameQuery.setParameter("entityName", name+"%");
      final List<User> results = findByNameQuery.getResultList();
      return results;
   }


   @GET
   @Produces("application/json")
   public List<User> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<User> findAllQuery = em.createQuery("SELECT DISTINCT u FROM User u ORDER BY u.id", User.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<User> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(User entity)
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