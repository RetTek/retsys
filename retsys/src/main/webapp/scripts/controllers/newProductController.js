
angular.module('retsys').controller('NewProductController', function ($scope, $location, locationParser, ProductResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.product = $scope.product || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Products/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ProductResource.save($scope.product, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Products");
    };
});