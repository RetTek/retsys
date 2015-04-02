angular.module('retsys').factory('PurchaseOrderDetailResource', function($resource){
    var resource = $resource('rest/purchaseorderdetails/:PurchaseOrderDetailId',{PurchaseOrderDetailId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});