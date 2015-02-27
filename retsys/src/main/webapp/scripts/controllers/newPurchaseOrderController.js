
angular.module('retsys').controller('NewPurchaseOrderController', function ($scope, $location, locationParser, PurchaseOrderResource , VendorResource, ClientResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.purchaseOrder = $scope.purchaseOrder || {};
    
    $scope.vendorList = VendorResource.queryAll(function(items){
        $scope.vendorSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("vendorSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.purchaseOrder.vendor = {};
            $scope.purchaseOrder.vendor.id = selection.value;
        }
    });
    
    $scope.clientList = ClientResource.queryAll(function(items){
        $scope.clientSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("clientSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.purchaseOrder.client = {};
            $scope.purchaseOrder.client.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/PurchaseOrders/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PurchaseOrderResource.save($scope.purchaseOrder, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/PurchaseOrders");
    };
});