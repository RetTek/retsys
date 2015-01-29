

angular.module('retsys').controller('EditProductController', function($scope, $routeParams, $location, ProductResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.product = new ProductResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Products");
        };
        ProductResource.get({ProductId:$routeParams.ProductId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.product);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.product.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Products");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Products");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.product.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});