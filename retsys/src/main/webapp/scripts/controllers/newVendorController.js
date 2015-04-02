
angular.module('retsys').controller('NewVendorController', function ($scope, $location, locationParser, VendorResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.vendor = $scope.vendor || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Vendors/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        VendorResource.save($scope.vendor, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Vendors");
    };
});