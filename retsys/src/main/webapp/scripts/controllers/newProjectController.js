
angular.module('retsys').controller('NewProjectController', function ($scope, $location, locationParser, ProjectResource , ClientResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.project = $scope.project || {};
    
    $scope.clientList = ClientResource.queryAll(function(items){
        $scope.clientSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("clientSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.project.client = {};
            $scope.project.client.id = selection.value;
        }
    });
    

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