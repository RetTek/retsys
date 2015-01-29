

angular.module('retsys').controller('EditProjectController', function($scope, $routeParams, $location, ProjectResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.project = new ProjectResource(self.original);
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
    
    
    $scope.get();
});