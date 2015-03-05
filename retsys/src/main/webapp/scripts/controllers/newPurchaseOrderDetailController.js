
angular.module('retsys').controller('NewPurchaseOrderDetailController', function ($scope, $location, locationParser, PurchaseOrderDetailResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.purchaseOrderDetail = $scope.purchaseOrderDetail || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/PurchaseOrderDetails/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PurchaseOrderDetailResource.save($scope.purchaseOrderDetail, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/PurchaseOrderDetails");
    };
});