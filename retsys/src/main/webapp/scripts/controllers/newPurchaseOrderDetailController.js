
angular.module('retsys').controller('NewPurchaseOrderDetailController', function ($scope, $location, locationParser, PurchaseOrderDetailResource , ItemResource, PurchaseOrderResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.purchaseOrderDetail = $scope.purchaseOrderDetail || {};
    
    $scope.itemList = ItemResource.queryAll(function(items){
        $scope.itemSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("itemSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.purchaseOrderDetail.item = {};
            $scope.purchaseOrderDetail.item.id = selection.value;
        }
    });
    
    $scope.purchaseOrderList = PurchaseOrderResource.queryAll(function(items){
        $scope.purchaseOrderSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("purchaseOrderSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.purchaseOrderDetail.purchaseOrder = {};
            $scope.purchaseOrderDetail.purchaseOrder.id = selection.value;
        }
    });
    

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