angular.module('retsys').factory('VendorResource', function($resource){
    var resource = $resource('rest/vendors/:VendorId',{VendorId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});