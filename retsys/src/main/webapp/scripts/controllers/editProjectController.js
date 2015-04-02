

angular.module('retsys').controller('EditProjectController', function($scope, $routeParams, $location, ProjectResource , ClientResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.project = new ProjectResource(self.original);
            ClientResource.queryAll(function(items) {
                $scope.clientSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.project.client && item.id == $scope.project.client.id) {
                        $scope.clientSelection = labelObject;
                        $scope.project.client = wrappedObject;
                        self.original.client = $scope.project.client;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Projects");
        };
        ProjectResource.get({ProjectId:$routeParams.ProjectId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.project);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.project.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Projects");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Projects");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.project.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("clientSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.project.client = {};
            $scope.project.client.id = selection.value;
        }
    });
    
    $scope.get();
});