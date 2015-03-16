

angular.module('retsys').controller('EditPurchaseOrderController', function($scope, $routeParams, $location, PurchaseOrderResource , VendorResource, PurchaseOrderDetailResource, ProjectResource) {
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
            PurchaseOrderDetailResource.queryAll(function(items) {
                $scope.purchaseOrderDetailSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.purchaseOrder.purchaseOrderDetail){
                        $.each($scope.purchaseOrder.purchaseOrderDetail, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.purchaseOrderDetailSelection.push(labelObject);
                                $scope.purchaseOrder.purchaseOrderDetail.push(wrappedObject);
                            }
                        });
                        self.original.purchaseOrderDetail = $scope.purchaseOrder.purchaseOrderDetail;
                    }
                    return labelObject;
                });
            });
            ProjectResource.queryAll(function(items) {
                $scope.projectSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.purchaseOrder.project && item.id == $scope.purchaseOrder.project.id) {
                        $scope.projectSelection = labelObject;
                        $scope.purchaseOrder.project = wrappedObject;
                        self.original.project = $scope.purchaseOrder.project;
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
    $scope.purchaseOrderDetailSelection = $scope.purchaseOrderDetailSelection || [];
    $scope.$watch("purchaseOrderDetailSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.purchaseOrder) {
            $scope.purchaseOrder.purchaseOrderDetail = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.purchaseOrder.purchaseOrderDetail.push(collectionItem);
            });
        }
    });
    $scope.$watch("projectSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.purchaseOrder.project = {};
            $scope.purchaseOrder.project.id = selection.value;
        }
    });
    
    $scope.get();
});