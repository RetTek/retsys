

angular.module('retsys').controller('EditPurchaseOrderController', function($scope, $routeParams, $location, PurchaseOrderResource , VendorResource, ClientResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.purchaseOrder = new PurchaseOrderResource(self.original);
            VendorResource.queryAll(function(items) {
                $scope.vendorSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.purchaseOrder.vendor && item.id == $scope.purchaseOrder.vendor.id) {
                        $scope.vendorSelection = labelObject;
                        $scope.purchaseOrder.vendor = wrappedObject;
                        self.original.vendor = $scope.purchaseOrder.vendor;
                    }
                    return labelObject;
                });
            });
            ClientResource.queryAll(function(items) {
                $scope.clientSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.purchaseOrder.client && item.id == $scope.purchaseOrder.client.id) {
                        $scope.clientSelection = labelObject;
                        $scope.purchaseOrder.client = wrappedObject;
                        self.original.client = $scope.purchaseOrder.client;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/PurchaseOrders");
        };
        PurchaseOrderResource.get({PurchaseOrderId:$routeParams.PurchaseOrderId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.purchaseOrder);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.purchaseOrder.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/PurchaseOrders");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/PurchaseOrders");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.purchaseOrder.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("vendorSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrder.vendor = {};
            $scope.purchaseOrder.vendor.id = selection.value;
        }
    });
    $scope.$watch("clientSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrder.client = {};
            $scope.purchaseOrder.client.id = selection.value;
        }
    });
    
    $scope.get();
});