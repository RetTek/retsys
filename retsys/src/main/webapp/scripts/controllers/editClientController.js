

angular.module('retsys').controller('EditClientController', function($scope, $routeParams, $location, ClientResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.client = new ClientResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Clients");
        };
        ClientResource.get({ClientId:$routeParams.ClientId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.client);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.client.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Clients");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Clients");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.client.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});