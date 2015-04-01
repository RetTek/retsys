

angular.module('retsys').controller('EditVendorController', function($scope, $routeParams, $location, VendorResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.vendor = new VendorResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Vendors");
        };
        VendorResource.get({VendorId:$routeParams.VendorId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.vendor);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.vendor.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Vendors");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Vendors");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.vendor.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});