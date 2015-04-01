angular.module('retsys').factory('ProjectItemsResource', function($resource){
    var resource = $resource('rest/projectitems/:ProjectItemsId',{ProjectItemsId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});