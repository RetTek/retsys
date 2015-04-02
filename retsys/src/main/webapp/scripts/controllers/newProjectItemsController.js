
angular.module('retsys').controller('NewProjectItemsController', function ($scope, $location, locationParser, ProjectItemsResource , ItemResource, ClientChallanResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.projectItems = $scope.projectItems || {};
    
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
            $scope.projectItems.item = {};
            $scope.projectItems.item.id = selection.value;
        }
    });
    
    $scope.challanList = ClientChallanResource.queryAll(function(items){
        $scope.challanSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("challanSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.projectItems.challan = {};
            $scope.projectItems.challan.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/ProjectItems/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ProjectItemsResource.save($scope.projectItems, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/ProjectItems");
    };
});