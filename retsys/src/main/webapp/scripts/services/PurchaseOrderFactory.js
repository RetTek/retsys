angular.module('retsys').factory('PurchaseOrderResource', function($resource){
    var resource = $resource('rest/purchaseorders/:PurchaseOrderId',{PurchaseOrderId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});