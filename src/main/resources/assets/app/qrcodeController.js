/**
 * Created by sylvanasp on 2016/11/14.
 */
actionApp.controller('qrcodeController', ['$rootScope', '$scope', '$http',
    function ($rootScope, $scope, $http) {
        $scope.$on('$viewContentLoaded', function () {
            console.log('页面加载完成!');
        });

        $scope.generate = function () {
            var qrcodeContent = $scope.qrcodeContent;
            console.log('二维码内容为:' + qrcodeContent);
            $http.get('qrcodeGenerate', {
                params: {qrcodeContent: qrcodeContent}
            }).success(function (data) {
                console.log(data);
                document.getElementById("qrcodeImage").src
                    = "../images/qrcodeImages/" + data;
                //$scope.qrcodeImage = "../images/qrcodeImages/" + data;
            });
        };
    }]
);
