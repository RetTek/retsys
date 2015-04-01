package org.rettek.rest;

import java.util.ArrayList;
import java.util.Iterator;
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

import org.rettek.dto.DeliveryChallanDTO;

import org.rettek.model.DeliveryChallan;
import org.rettek.model.Item;
import org.rettek.model.PurchaseOrder;
import org.rettek.model.DeliveryChallanDetail;

/**
 * 
 */
@Stateless
@Path("/deliverychallans")
public class DeliveryChallanEndpoint
{
   @PersistenceContext(unitName = "retsys-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(DeliveryChallan entity)
   {
      
      
      Iterator<DeliveryChallanDetail> dcDetails = entity
				.getDeliveryChallanDetail().iterator();
		while (dcDetails.hasNext()) {
			DeliveryChallanDetail deliveryChallanDetail = (DeliveryChallanDetail) dcDetails
					.next();
			Item item = null;
			item = em.find(Item.class, deliveryChallanDetail.getItem()
					.getId());

			if (item != null) {
				if(entity.isIsDelivery())
					item.setQuantity(item.getQuantity()
							- deliveryChallanDetail.getQuantity());
				else
					item.setQuantity(item.getQuantity()
							+ deliveryChallanDetail.getQuantity());					
				em.merge(item);
			}
			
		}
	  em.persist(entity);
	  System.out.println("everything is fine!");
      return Response.created(UriBuilder.fromResource(DeliveryChallanEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      DeliveryChallan entity = em.find(DeliveryChallan.class, id);
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
      TypedQuery<DeliveryChallan> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM DeliveryChallan c LEFT JOIN FETCH c.project LEFT JOIN FETCH c.originalDeliveryChallan WHERE c.id = :entityId ORDER BY c.id", DeliveryChallan.class);
      findByIdQuery.setParameter("entityId", id);
      DeliveryChallan entity;
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
   @Produces("application/json")
   public List<DeliveryChallan> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<DeliveryChallan> findAllQuery = em.createQuery("SELECT DISTINCT c FROM DeliveryChallan c LEFT JOIN FETCH c.project LEFT JOIN FETCH c.originalDeliveryChallan ORDER BY c.id", DeliveryChallan.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<DeliveryChallan> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(DeliveryChallan entity)
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
   
   @GET
	@Path("/name/{name:^[a-zA-Z0-9_]*$}")
	@Produces("application/json")
	public List<DeliveryChallanDTO> findByName(@PathParam("name") String name) {
		TypedQuery<DeliveryChallan> findByNameQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM DeliveryChallan c LEFT JOIN FETCH c.project LEFT JOIN FETCH c.originalDeliveryChallan WHERE c.project.name like :entityName and c.isDelivery = 1 ORDER BY c.project.name",
						DeliveryChallan.class);
		findByNameQuery.setParameter("entityName", name + "%");
		List<DeliveryChallan> entity;
		List<DeliveryChallanDTO> entityDTOs = new ArrayList<>();
		try {
			entity = findByNameQuery.getResultList();
		} catch (NoResultException nre) {
			entity = null;
		}

		if (entity != null) {
			for (DeliveryChallan po : entity) {
				entityDTOs.add(new DeliveryChallanDTO(po));
			}
		}
		return entityDTOs;
	}
}
