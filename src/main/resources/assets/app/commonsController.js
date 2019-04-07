/**
 * Created by sylvanasp on 2016/11/14.
 */
actionApp.controller('commonsController',['$scope','$http',
    function ($scope,$http) {
        $scope.$on('$viewContentLoaded',function(){
           console.log("页面加载完毕!");
        });

        jQuery.ajax({
            url:"/hello",
            type:"GET",
            dataType:"json",
            success : function (data) {
                $("#helloDiv").val(data);
            }
        });
    }
]);