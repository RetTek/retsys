
angular.module('retsys').controller('NewProjectController', function ($scope, $location, locationParser, ProjectResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.project = $scope.project || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Projects/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ProjectResource.save($scope.project, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Projects");
    };
});