
angular.module('retsys').controller('NewItemController', function ($scope, $location, locationParser, ItemResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.item = $scope.item || {};
    

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