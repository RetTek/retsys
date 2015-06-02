			create	table	Audit	(					
							id	bigint	not	null,	
							createdBy	varchar(255)	not	null,	
							createdOn	date,			
							modifiedBy	varchar(255),			
							modifiedOn	date,			
							primary	key	(id)		
			);								
											
			create	table	Client	(					
							id	bigint	not	null,	
							address	longtext,			
							email	varchar(255),			
							mobile	varchar(255),			
							name	longtext	not	null,	
							phone	varchar(255),			
							remarks	longtext,			
							audit_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	CreditNote	(					
							id	bigint	not	null,	
							creationDate	date	not	null,	
							remarks	varchar(255),			
							totalAmount	double	precision	not	null,
							audit_id	bigint,			
							vendor_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	CreditNoteDetail	(					
							id	bigint	not	null,	
							amount	double	precision,		
							confirm	boolean,			
							returnQuantity	double	precision,		
							creditNote_id	bigint,			
							item_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	DeliveryChallan	(					
							id	bigint	not	null,	
							challanDate	date,	
							concernPerson varchar(255),	
							deliveryMode varchar(255),	
							isDelivery	boolean,			
							audit_id	bigint,			
							originalDeliveryChallan_id	bigint,			
							project_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	DeliveryChallanDetail	(					
							id	bigint	not	null,	
							amount	double	precision,		
							quantity	double	precision,		
							units	varchar(255),			
							deliveryChallan_id	bigint,			
							item_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	Item	(					
							id	bigint	not	null,	
							billno	varchar(255),			
							brand	varchar(255),			
							color	varchar(255),			
							discountPercentage	double	precision,		
							drawerNo	varchar(255),			
							godownName	varchar(255),			
							location1	varchar(255),			
							location2	varchar(255),			
							location3	varchar(255),			
							minReorder	double	precision,		
							name	longtext	not	null,	
							quantity	double	precision,		
							rate	double	precision,		
							remarks	longtext,			
							site	varchar(255),			
							size	varchar(255),			
							supervisor	varchar(255),			
							transportcharge	double	precision,		
							transportmode	varchar(255),			
							unit	varchar(255),			
							audit_id	bigint,			
							product_id	bigint,			
							vendor_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	Product	(					
							id	bigint	not	null,	
							name	longtext	not	null,	
							prodDesc	varchar(255),			
							remarks	longtext,			
							audit_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	Project	(					
							id	bigint	not	null,	
							name	longtext	not	null,	
							projectDesc	longtext	not	null,	
							remarks	longtext,			
							audit_id	bigint,			
							client_id	bigint	not	null,	
							primary	key	(id)		
			);								
						
											
			create	table	PurchaseOrder	(					
							id	bigint	not	null,	
							date	date,			
							deliveryAddress	longtext,			
							pending	boolean	not	null,	
							audit_id	bigint,			
							project_id	bigint,			
							vendor_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	PurchaseOrderDetail	(					
							id	bigint	not	null,	
							billNo	varchar(255),			
							confirm	varchar(1),			
							quantity	double	precision,		
							receivedDate	date,			
							supervisor	varchar(255),			
							item_id	bigint,			
							purchaseOrder_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	User	(					
							id	bigint	not	null,	
							name	varchar(255),			
							password	varchar(255),			
							usertype	varchar(1),			
							audit_id	bigint,			
							primary	key	(id)		
			);								
											
			create	table	Vendor	(					
							id	bigint	not	null,	
							address	longtext,			
							credit	double	precision,		
							email	varchar(255),			
							mobile	varchar(255),			
							name	longtext	not	null,	
							phone	varchar(255),			
							remarks	longtext,			
							audit_id	bigint,			
							primary	key	(id)		
			);								
											
			alter	table	Client						
							add	index	FK_2ifpqpmw3lgufy9n6j9v1sy8k	(audit_id),	
							add	constraint	FK_2ifpqpmw3lgufy9n6j9v1sy8k		
							foreign	key	(audit_id)		
							references	Audit	(id);		
											
			alter	table	CreditNote						
							add	index	FK_trvkpi7bgpo9w3haku2oym7r2	(audit_id),	
							add	constraint	FK_trvkpi7bgpo9w3haku2oym7r2		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	CreditNote						
							add	index	FK_aa57q78wfdexfsqli8u0j4bgv	(vendor_id),	
							add	constraint	FK_aa57q78wfdexfsqli8u0j4bgv		
							foreign	key	(vendor_id)		
							references	Vendor	(id)	;	
											
			alter	table	CreditNoteDetail						
							add	index	FK_l4pu9udugigb2dof1r2dyfqqu	(creditNote_id),	
							add	constraint	FK_l4pu9udugigb2dof1r2dyfqqu		
							foreign	key	(creditNote_id)		
							references	CreditNote	(id)	;	
											
			alter	table	CreditNoteDetail						
							add	index	FK_kxnp5jdxenq5a98920xkqnwpx	(item_id),	
							add	constraint	FK_kxnp5jdxenq5a98920xkqnwpx		
							foreign	key	(item_id)		
							references	Item	(id)	;	
											
			alter	table	DeliveryChallan						
							add	index	FK_2aqxk0b51g0h2cm2j4hmot2n6	(audit_id),	
							add	constraint	FK_2aqxk0b51g0h2cm2j4hmot2n6		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	DeliveryChallan						
							add	index	FK_n6nanngxrl2b3qnw4g3ra5g3r	(originalDeliveryChallan_id),	
							add	constraint	FK_n6nanngxrl2b3qnw4g3ra5g3r		
							foreign	key	(originalDeliveryChallan_id)		
							references	DeliveryChallan	(id)	;	
											
			alter	table	DeliveryChallan						
							add	index	FK_3ev9vfef6gqbg8b1w6ybw9jnr	(project_id),	
							add	constraint	FK_3ev9vfef6gqbg8b1w6ybw9jnr		
							foreign	key	(project_id)		
							references	Project	(id)	;	
											
			alter	table	DeliveryChallanDetail						
							add	index	FK_rilqj0muggyiaidy2hubfehmm	(deliveryChallan_id),	
							add	constraint	FK_rilqj0muggyiaidy2hubfehmm		
							foreign	key	(deliveryChallan_id)		
							references	DeliveryChallan	(id)	;	
											
			alter	table	DeliveryChallanDetail						
							add	index	FK_pa720xjh52feofotriron5m6j	(item_id),	
							add	constraint	FK_pa720xjh52feofotriron5m6j		
							foreign	key	(item_id)		
							references	Item	(id)	;	
											
			alter	table	Item						
							add	index	FK_17yb8htw6koh35kekaensqq6q	(audit_id),	
							add	constraint	FK_17yb8htw6koh35kekaensqq6q		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	Item						
							add	index	FK_i4xotma76q3ob250o90k3xgym	(product_id),	
							add	constraint	FK_i4xotma76q3ob250o90k3xgym		
							foreign	key	(product_id)		
							references	Product	(id)	;	
											
			alter	table	Item						
							add	index	FK_pxaapl12jgux03rwmed28drx4	(vendor_id),	
							add	constraint	FK_pxaapl12jgux03rwmed28drx4		
							foreign	key	(vendor_id)		
							references	Vendor	(id)	;	
											
			alter	table	Product						
							add	index	FK_nnkr4usxn967r1wpdqo4sb3x5	(audit_id),	
							add	constraint	FK_nnkr4usxn967r1wpdqo4sb3x5		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	Project						
							add	index	FK_86vtcy3ec4g3vf62p4fnwy2nw	(audit_id),	
							add	constraint	FK_86vtcy3ec4g3vf62p4fnwy2nw		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	Project						
							add	index	FK_1ymt3gken3ldnictcw72w5rib	(client_id),	
							add	constraint	FK_1ymt3gken3ldnictcw72w5rib		
							foreign	key	(client_id)		
							references	Client	(id)	;	
											
			alter	table	PurchaseOrder						
							add	index	FK_bgthuh4mq3uw2mox6fmgj4q36	(audit_id),	
							add	constraint	FK_bgthuh4mq3uw2mox6fmgj4q36		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	PurchaseOrder						
							add	index	FK_gp5piylhjb2rcfxlty03q6tfm	(project_id),	
							add	constraint	FK_gp5piylhjb2rcfxlty03q6tfm		
							foreign	key	(project_id)		
							references	Project	(id)	;	
											
			alter	table	PurchaseOrder						
							add	index	FK_a0h9o07klr7j8s1ohqvuiq3mw	(vendor_id),	
							add	constraint	FK_a0h9o07klr7j8s1ohqvuiq3mw		
							foreign	key	(vendor_id)		
							references	Vendor	(id)	;	
											
			alter	table	PurchaseOrderDetail						
							add	index	FK_556hf7eqo35glin61tfcrgk0o	(item_id),	
							add	constraint	FK_556hf7eqo35glin61tfcrgk0o		
							foreign	key	(item_id)		
							references	Item	(id)	;	
											
			alter	table	PurchaseOrderDetail						
							add	index	FK_20p5bnj4dylojw3v0u6wqw48i	(purchaseOrder_id),	
							add	constraint	FK_20p5bnj4dylojw3v0u6wqw48i		
							foreign	key	(purchaseOrder_id)		
							references	PurchaseOrder	(id)	;	
											
			alter	table	User						
							add	index	FK_exwnyin2ffuyb3yq58i0k6qth	(audit_id),	
							add	constraint	FK_exwnyin2ffuyb3yq58i0k6qth		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			alter	table	Vendor						
							add	index	FK_ajvkiqf1eya4pnr8fiwouu6xh	(audit_id),	
							add	constraint	FK_ajvkiqf1eya4pnr8fiwouu6xh		
							foreign	key	(audit_id)		
							references	Audit	(id)	;	
											
			create	table	hibernate_sequence	(					
								next_val	bigint		
			)	;							
											
			insert	into	hibernate_sequence	values	(	1	);		

