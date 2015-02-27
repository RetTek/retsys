
angular.module('retsys').controller('NewItemController', function ($scope, $location, locationParser, ItemResource , VendorResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.item = $scope.item || {};
    
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
            $scope.item.vendor = {};
            $scope.item.vendor.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Items/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ItemResource.save($scope.item, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Items");
    };
});