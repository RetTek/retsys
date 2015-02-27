
angular.module('retsys').controller('NewClientChallanController', function ($scope, $location, locationParser, ClientChallanResource , ProjectResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.clientChallan = $scope.clientChallan || {};
    
    $scope.projectList = ProjectResource.queryAll(function(items){
        $scope.projectSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("projectSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.clientChallan.project = {};
            $scope.clientChallan.project.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/ClientChallans/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ClientChallanResource.save($scope.clientChallan, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/ClientChallans");
    };
});