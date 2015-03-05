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
import org.rettek.model.Vendor;

/**
 * 
 */
@Stateless
@Path("/vendors")
public class VendorEndpoint
{
   @PersistenceContext(unitName = "retsys-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Vendor entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(VendorEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Vendor entity = em.find(Vendor.class, id);
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
      TypedQuery<Vendor> findByIdQuery = em.createQuery("SELECT DISTINCT v FROM Vendor v WHERE v.id = :entityId ORDER BY v.id", Vendor.class);
      findByIdQuery.setParameter("entityId", id);
      Vendor entity;
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
   public List<Vendor> findByName(@PathParam("name") String name)
   {
      TypedQuery<Vendor> findByNameQuery = em.createQuery("SELECT DISTINCT c FROM Vendor c WHERE c.name like :entityName ORDER BY c.name", Vendor.class);
      findByNameQuery.setParameter("entityName", name+"%");
      List<Vendor> entity;
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
   public List<Vendor> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<Vendor> findAllQuery = em.createQuery("SELECT DISTINCT v FROM Vendor v ORDER BY v.id", Vendor.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<Vendor> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Vendor entity)
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
