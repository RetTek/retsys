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

import org.rettek.dto.PurchaseOrderDTO;
import org.rettek.model.Item;
import org.rettek.model.PurchaseOrder;
import org.rettek.model.PurchaseOrderDetail;

/**
 * 
 */
@Stateless
@Path("/purchaseorders")
public class PurchaseOrderEndpoint {
	@PersistenceContext(unitName = "retsys-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public PurchaseOrderDTO create(PurchaseOrder entity) {
		if (entity.getId() == null) {
			entity.setPending(true);
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		return new PurchaseOrderDTO(entity);
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		PurchaseOrder entity = em.find(PurchaseOrder.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<PurchaseOrder> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM PurchaseOrder p LEFT JOIN FETCH p.vendor LEFT JOIN FETCH p.project LEFT JOIN FETCH p.purchaseOrderDetail WHERE p.id = :entityId ORDER BY p.id",
						PurchaseOrder.class);
		findByIdQuery.setParameter("entityId", id);
		PurchaseOrder entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Path("/name/{name:^[a-zA-Z0-9_]*$}")
	@Produces("application/json")
	public List<PurchaseOrderDTO> findByName(@PathParam("name") String name) {
		TypedQuery<PurchaseOrder> findByNameQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM PurchaseOrder p LEFT JOIN FETCH p.vendor LEFT JOIN FETCH p.project LEFT JOIN FETCH p.purchaseOrderDetail WHERE p.project.name like :entityName ORDER BY p.project.name",
						PurchaseOrder.class);
		findByNameQuery.setParameter("entityName", name + "%");
		List<PurchaseOrder> entity;
		List<PurchaseOrderDTO> entityDTOs = new ArrayList<>();
		try {
			entity = findByNameQuery.getResultList();
		} catch (NoResultException nre) {
			entity = null;
		}

		if (entity != null) {
			for (PurchaseOrder po : entity) {
				entityDTOs.add(new PurchaseOrderDTO(po));
			}
		}
		return entityDTOs;
	}

	@GET
	@Produces("application/json")
	public List<PurchaseOrder> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<PurchaseOrder> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM PurchaseOrder p LEFT JOIN FETCH p.vendor LEFT JOIN FETCH p.project LEFT JOIN FETCH p.purchaseOrderDetail ORDER BY p.id",
						PurchaseOrder.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<PurchaseOrder> results = findAllQuery.getResultList();
		return results;
	}

	@GET
	@Path("/pendingpo")
	@Produces("application/json")
	public List<PurchaseOrderDTO> pendingPO(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<PurchaseOrder> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM PurchaseOrder p LEFT JOIN FETCH p.vendor LEFT JOIN FETCH p.project LEFT JOIN FETCH p.purchaseOrderDetail WHERE p.pending='1' ORDER BY p.id",
						PurchaseOrder.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<PurchaseOrder> results = findAllQuery.getResultList();
		List<PurchaseOrderDTO> entityDTOs = new ArrayList<>();
		if (results != null) {
			for (PurchaseOrder po : results) {
				entityDTOs.add(new PurchaseOrderDTO(po));
			}
		}
		return entityDTOs;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(PurchaseOrder entity) {
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}

	@PUT
	@Path("/confirm")
	@Consumes("application/json")
	public Response confirm(PurchaseOrder entity) {
		try {
			boolean isFullyConfirmed = true;
			Iterator<PurchaseOrderDetail> poDetails = entity
					.getPurchaseOrderDetail().iterator();
			while (poDetails.hasNext()) {
				PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail) poDetails
						.next();
				if ("N".equals(purchaseOrderDetail.getConfirm())) {
					isFullyConfirmed = false;
				}
				Item item = null;
				item = em.find(Item.class, purchaseOrderDetail.getItem()
						.getId());

				if (item != null) {
					if ("Y".equals(purchaseOrderDetail.getConfirm())) {
						// get existing record
						PurchaseOrderDetail existingRecord = em.find(
								PurchaseOrderDetail.class,
								purchaseOrderDetail.getId());
						// add to existing item quantity
						// if not already confirmed
						if ("N".equals(existingRecord.getConfirm())) {
							item.setQuantity(item.getQuantity()
									+ purchaseOrderDetail.getQuantity());
						} else {
							continue;
						}
					} else {
						if ("Y".equals(em.find(PurchaseOrderDetail.class,
								purchaseOrderDetail.getId()).getConfirm())) {
							item.setQuantity(item.getQuantity()
									- purchaseOrderDetail.getQuantity());
						} else {
							continue;
						}
					}
					em.merge(item);
				}
			}
			if (isFullyConfirmed) {
				entity.setPending(false);
			} else {
				entity.setPending(true);
			}

			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			System.out.println("entity causing failure: " + e.getEntity());
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("everything is fine!");
		return Response.noContent().build();
	}
}
