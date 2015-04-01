angular.module('retsys').factory('ClientResource', function($resource){
    var resource = $resource('rest/clients/:ClientId',{ClientId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});