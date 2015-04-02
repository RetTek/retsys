
angular.module('retsys').controller('NewClientController', function ($scope, $location, locationParser, ClientResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.client = $scope.client || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Clients/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ClientResource.save($scope.client, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Clients");
    };
});