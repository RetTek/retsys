
angular.module('retsys').controller('NewPurchaseOrderController', function ($scope, $location, locationParser, PurchaseOrderResource , VendorResource, PurchaseOrderDetailResource, ProjectResource) {
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
    
    $scope.purchaseOrderDetailList = PurchaseOrderDetailResource.queryAll(function(items){
        $scope.purchaseOrderDetailSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("purchaseOrderDetailSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrder.purchaseOrderDetail = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.purchaseOrder.purchaseOrderDetail.push(collectionItem);
            });
        }
    });
    
    $scope.projectList = ProjectResource.queryAll(function(items){
        $scope.projectSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("projectSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.purchaseOrder.project = {};
            $scope.purchaseOrder.project.id = selection.value;
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