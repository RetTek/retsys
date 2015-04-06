package org.rettek.rest;

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

import org.rettek.dto.CreditNoteDTO;
import org.rettek.model.CreditNote;
import org.rettek.model.CreditNoteDetail;
import org.rettek.model.Item;
import org.rettek.model.Vendor;

/**
 * 
 */
@Stateless
@Path("/creditnotes")
public class CreditNoteEndpoint {
	@PersistenceContext(unitName = "retsys-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public CreditNoteDTO create(CreditNote entity) {
		double totalCreditAmt = entity.getTotalAmount();
		double adjustCreditAmt = 0;
		if (entity.getId() != null) {
			CreditNote existingEntity = em.find(CreditNote.class,
					entity.getId());
			if (existingEntity.getTotalAmount() != totalCreditAmt) {
				adjustCreditAmt = totalCreditAmt
						- existingEntity.getTotalAmount();
				updateVendorCreditAmt(entity.getVendor(), adjustCreditAmt);
			}
		}else{
			updateVendorCreditAmt(entity.getVendor(), entity.getTotalAmount());
		}
		Iterator<CreditNoteDetail> it = entity.getCreditNoteDetails()
				.iterator();
		while (it.hasNext()) {
			CreditNoteDetail creditNoteDetails = (CreditNoteDetail) it.next();
			System.out.println(creditNoteDetails);
			processCreditNoteDetail(creditNoteDetails);
		}
		if (entity.getId() != null) {
			em.merge(entity);
		} else {
			em.persist(entity);
		}
		em.flush();
		entity = em.find(CreditNote.class, entity.getId()); // refresh ids
		return new CreditNoteDTO(entity);
	}

	private void updateVendorCreditAmt(Vendor vendor, double adjustCreditAmt) {
		vendor = em.find(Vendor.class, vendor.getId());
		vendor.setCredit(vendor.getCredit() + adjustCreditAmt);

		em.merge(vendor);

	}

	private void updateItemQuantity(Item item, double newQuantity,
			boolean reduce) {
		double updatedQuantity = 0.0d;

		if (reduce) {
			updatedQuantity = item.getQuantity() - newQuantity;
		} else {
			updatedQuantity = item.getQuantity() + newQuantity;
		}

		item.setQuantity(updatedQuantity);
		em.merge(item);
	}

	private void processCreditNoteDetail(CreditNoteDetail detail) {
		Item item = detail.getItem();
		int change = getItemQuantityChangeState(detail); // 0=no change,
															// 1=increase,
															// -1=reduce

		if (change != 0) {
			updateItemQuantity(em.find(Item.class, item.getId()),
					detail.getReturnQuantity(), change == -1);
		}

		if (isNewCreditNoteDetail(detail)) {
			em.persist(detail);
		} else {
			em.merge(detail);
		}
	}

	private boolean isNewCreditNoteDetail(CreditNoteDetail detail) {
		return !(detail.getId() != null && detail.getId() != 0);
	}

	private boolean returnFromStockToVendor(CreditNoteDetail detail) {
		CreditNoteDetail existingRec = null;
		boolean returnStock = true;
		boolean isConfirm = detail.isConfirm();

		existingRec = em.find(CreditNoteDetail.class, detail.getId());
		if (existingRec.isConfirm() && !isConfirm) {
			returnStock = false;
		}

		return returnStock;
	}

	private int getItemQuantityChangeState(CreditNoteDetail detail) {

		boolean isConfirm = detail.isConfirm();
		int changeBy = 0; // no change

		if (isNewCreditNoteDetail(detail)) {
			// new record
			changeBy = -1;
		} else {
			if (returnFromStockToVendor(detail)) {
				changeBy = -1;
			} else {
				changeBy = 1;
			}
		}

		return changeBy;
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		CreditNote entity = em.find(CreditNote.class, id);
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
		TypedQuery<CreditNote> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM CreditNote c LEFT JOIN FETCH c.vendor LEFT JOIN FETCH c.creditNoteDetails WHERE c.id = :entityId ORDER BY c.id",
						CreditNote.class);
		findByIdQuery.setParameter("entityId", id);
		CreditNote entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new CreditNoteDTO(entity)).build();
	}

	@GET
	@Produces("application/json")
	public List<CreditNote> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<CreditNote> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM CreditNote c LEFT JOIN FETCH c.vendor LEFT JOIN FETCH c.creditNoteDetails ORDER BY c.id",
						CreditNote.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<CreditNote> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(CreditNote entity) {
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
