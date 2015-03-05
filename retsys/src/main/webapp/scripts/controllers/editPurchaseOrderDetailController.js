

angular.module('retsys').controller('EditPurchaseOrderDetailController', function($scope, $routeParams, $location, PurchaseOrderDetailResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.purchaseOrderDetail = new PurchaseOrderDetailResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/PurchaseOrderDetails");
        };
        PurchaseOrderDetailResource.get({PurchaseOrderDetailId:$routeParams.PurchaseOrderDetailId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.purchaseOrderDetail);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.purchaseOrderDetail.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/PurchaseOrderDetails");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/PurchaseOrderDetails");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.purchaseOrderDetail.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});