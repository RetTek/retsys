

angular.module('retsys').controller('EditPurchaseOrderDetailController', function($scope, $routeParams, $location, PurchaseOrderDetailResource , ItemResource, PurchaseOrderResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.purchaseOrderDetail = new PurchaseOrderDetailResource(self.original);
            ItemResource.queryAll(function(items) {
                $scope.itemSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.purchaseOrderDetail.item && item.id == $scope.purchaseOrderDetail.item.id) {
                        $scope.itemSelection = labelObject;
                        $scope.purchaseOrderDetail.item = wrappedObject;
                        self.original.item = $scope.purchaseOrderDetail.item;
                    }
                    return labelObject;
                });
            });
            PurchaseOrderResource.queryAll(function(items) {
                $scope.purchaseOrderSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.purchaseOrderDetail.purchaseOrder && item.id == $scope.purchaseOrderDetail.purchaseOrder.id) {
                        $scope.purchaseOrderSelection = labelObject;
                        $scope.purchaseOrderDetail.purchaseOrder = wrappedObject;
                        self.original.purchaseOrder = $scope.purchaseOrderDetail.purchaseOrder;
                    }
                    return labelObject;
                });
            });
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
    
    $scope.$watch("itemSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrderDetail.item = {};
            $scope.purchaseOrderDetail.item.id = selection.value;
        }
    });
    $scope.$watch("purchaseOrderSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrderDetail.purchaseOrder = {};
            $scope.purchaseOrderDetail.purchaseOrder.id = selection.value;
        }
    });
    
    $scope.get();
});