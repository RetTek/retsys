

angular.module('retsys').controller('EditItemController', function($scope, $routeParams, $location, ItemResource , VendorResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.item = new ItemResource(self.original);
            VendorResource.queryAll(function(items) {
                $scope.vendorSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.item.vendor && item.id == $scope.item.vendor.id) {
                        $scope.vendorSelection = labelObject;
                        $scope.item.vendor = wrappedObject;
                        self.original.vendor = $scope.item.vendor;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Items");
        };
        ItemResource.get({ItemId:$routeParams.ItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.item);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.item.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Items");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Items");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.item.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("vendorSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.item.vendor = {};
            $scope.item.vendor.id = selection.value;
        }
    });
    
    $scope.get();
});