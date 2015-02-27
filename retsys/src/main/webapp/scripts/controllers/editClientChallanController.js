

angular.module('retsys').controller('EditClientChallanController', function($scope, $routeParams, $location, ClientChallanResource , ProjectResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.clientChallan = new ClientChallanResource(self.original);
            ProjectResource.queryAll(function(items) {
                $scope.projectSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.clientChallan.project && item.id == $scope.clientChallan.project.id) {
                        $scope.projectSelection = labelObject;
                        $scope.clientChallan.project = wrappedObject;
                        self.original.project = $scope.clientChallan.project;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/ClientChallans");
        };
        ClientChallanResource.get({ClientChallanId:$routeParams.ClientChallanId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.clientChallan);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.clientChallan.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/ClientChallans");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/ClientChallans");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.clientChallan.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("projectSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.clientChallan.project = {};
            $scope.clientChallan.project.id = selection.value;
        }
    });
    
    $scope.get();
});