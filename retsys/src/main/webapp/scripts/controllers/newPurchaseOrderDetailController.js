
angular.module('retsys').controller('NewPurchaseOrderDetailController', function ($scope, $location, locationParser, PurchaseOrderDetailResource , PurchaseOrderResource, ItemResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.purchaseOrderDetail = $scope.purchaseOrderDetail || {};
    
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
    
    $scope.itemList = ItemResource.queryAll(function(items){
        $scope.itemSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("itemSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrderDetail.item = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.purchaseOrderDetail.item.push(collectionItem);
            });
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