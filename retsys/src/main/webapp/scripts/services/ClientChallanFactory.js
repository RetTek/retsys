angular.module('retsys').factory('ClientChallanResource', function($resource){
    var resource = $resource('rest/clientchallans/:ClientChallanId',{ClientChallanId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});