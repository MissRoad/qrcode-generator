/**
 * Created by sylvanasp on 2016/11/14.
 */
actionApp.controller('commonsController',['$scope','$http',
    function ($scope,$http) {
        $scope.$on('$viewContentLoaded',function(){
           console.log("页面加载完毕!");
        });

        var hello = $scope.hello;
        $http.get("hello").success(function(data){
            hello = data;
        });
    }
]);