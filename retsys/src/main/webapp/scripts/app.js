'use strict';

angular.module('retsys',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Clients',{templateUrl:'views/Client/search.html',controller:'SearchClientController'})
      .when('/Clients/new',{templateUrl:'views/Client/detail.html',controller:'NewClientController'})
      .when('/Clients/edit/:ClientId',{templateUrl:'views/Client/detail.html',controller:'EditClientController'})
      .when('/ClientChallans',{templateUrl:'views/ClientChallan/search.html',controller:'SearchClientChallanController'})
      .when('/ClientChallans/new',{templateUrl:'views/ClientChallan/detail.html',controller:'NewClientChallanController'})
      .when('/ClientChallans/edit/:ClientChallanId',{templateUrl:'views/ClientChallan/detail.html',controller:'EditClientChallanController'})
      .when('/Items',{templateUrl:'views/Item/search.html',controller:'SearchItemController'})
      .when('/Items/new',{templateUrl:'views/Item/detail.html',controller:'NewItemController'})
      .when('/Items/edit/:ItemId',{templateUrl:'views/Item/detail.html',controller:'EditItemController'})
      .when('/Products',{templateUrl:'views/Product/search.html',controller:'SearchProductController'})
      .when('/Products/new',{templateUrl:'views/Product/detail.html',controller:'NewProductController'})
      .when('/Products/edit/:ProductId',{templateUrl:'views/Product/detail.html',controller:'EditProductController'})
      .when('/Projects',{templateUrl:'views/Project/search.html',controller:'SearchProjectController'})
      .when('/Projects/new',{templateUrl:'views/Project/detail.html',controller:'NewProjectController'})
      .when('/Projects/edit/:ProjectId',{templateUrl:'views/Project/detail.html',controller:'EditProjectController'})
      .when('/ProjectItems',{templateUrl:'views/ProjectItems/search.html',controller:'SearchProjectItemsController'})
      .when('/ProjectItems/new',{templateUrl:'views/ProjectItems/detail.html',controller:'NewProjectItemsController'})
      .when('/ProjectItems/edit/:ProjectItemsId',{templateUrl:'views/ProjectItems/detail.html',controller:'EditProjectItemsController'})
      .when('/PurchaseOrders',{templateUrl:'views/PurchaseOrder/search.html',controller:'SearchPurchaseOrderController'})
      .when('/PurchaseOrders/new',{templateUrl:'views/PurchaseOrder/detail.html',controller:'NewPurchaseOrderController'})
      .when('/PurchaseOrders/edit/:PurchaseOrderId',{templateUrl:'views/PurchaseOrder/detail.html',controller:'EditPurchaseOrderController'})
      .when('/PurchaseOrderDetails',{templateUrl:'views/PurchaseOrderDetail/search.html',controller:'SearchPurchaseOrderDetailController'})
      .when('/PurchaseOrderDetails/new',{templateUrl:'views/PurchaseOrderDetail/detail.html',controller:'NewPurchaseOrderDetailController'})
      .when('/PurchaseOrderDetails/edit/:PurchaseOrderDetailId',{templateUrl:'views/PurchaseOrderDetail/detail.html',controller:'EditPurchaseOrderDetailController'})
      .when('/Vendors',{templateUrl:'views/Vendor/search.html',controller:'SearchVendorController'})
      .when('/Vendors/new',{templateUrl:'views/Vendor/detail.html',controller:'NewVendorController'})
      .when('/Vendors/edit/:VendorId',{templateUrl:'views/Vendor/detail.html',controller:'EditVendorController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
