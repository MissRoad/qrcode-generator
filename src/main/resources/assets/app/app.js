/**
 * Created by sylvanasp on 2016/11/14.
 */
//定义模块actionApp,并依赖于路由模块ngRoute
var actionApp = angular.module('actionApp',['ngRoute']);
//配置路由
actionApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/qrcode',
        {
            controller:'qrcodeController',
            templateUrl:'assets/template/qrcode.html'
        }
    )
        .when('/hello',
            {
                controller:'commonsController',
                templateUrl:'assets/template/hello.html'
            });
}]);
