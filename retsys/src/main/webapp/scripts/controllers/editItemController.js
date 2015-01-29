

angular.module('retsys').controller('EditItemController', function($scope, $routeParams, $location, ItemResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.item = new ItemResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Items");
        };
        ItemResource.get({ItemId:$routeParams.ItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.item);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.item.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Items");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Items");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.item.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});