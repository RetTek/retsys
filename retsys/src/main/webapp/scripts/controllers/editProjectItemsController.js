

angular.module('retsys').controller('EditProjectItemsController', function($scope, $routeParams, $location, ProjectItemsResource , ItemResource, ClientChallanResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.projectItems = new ProjectItemsResource(self.original);
            ItemResource.queryAll(function(items) {
                $scope.itemSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.projectItems.item && item.id == $scope.projectItems.item.id) {
                        $scope.itemSelection = labelObject;
                        $scope.projectItems.item = wrappedObject;
                        self.original.item = $scope.projectItems.item;
                    }
                    return labelObject;
                });
            });
            ClientChallanResource.queryAll(function(items) {
                $scope.challanSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.projectItems.challan && item.id == $scope.projectItems.challan.id) {
                        $scope.challanSelection = labelObject;
                        $scope.projectItems.challan = wrappedObject;
                        self.original.challan = $scope.projectItems.challan;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/ProjectItems");
        };
        ProjectItemsResource.get({ProjectItemsId:$routeParams.ProjectItemsId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.projectItems);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.projectItems.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/ProjectItems");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/ProjectItems");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.projectItems.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("itemSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.projectItems.item = {};
            $scope.projectItems.item.id = selection.value;
        }
    });
    $scope.$watch("challanSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.projectItems.challan = {};
            $scope.projectItems.challan.id = selection.value;
        }
    });
    
    $scope.get();
});