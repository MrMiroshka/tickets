angular.module('app', ['ngStorage']).controller('myController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8899/app';
    // FIXME: 3.06.23 Изменить url

    $scope.loadAllTasks = function (){
        $http.get(contextPath + "/task")
            .then(function (response){
                $scope.taskList = response.data;
        });

    };


    $scope.createTask = function (){
        $http.post('http://localhost:8899/app/task/create', $scope.task)
            .then(function (response){
                console.log("Все ОК");
                $scope.loadAllTasks();
            });
    }



    $scope.tryToAuth = function () {
        $http.post('http://localhost:8899/app/auth', $scope.user)
            .then(function successCallback(response) {
                if(response.data.token){
                    console.log(response.data.token)
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                }
            }, function errorCallback(response){
                console.log("Ошибка: " + response)
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    };
    $scope.clearUser = function (){
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };
    $scope.isUserLoggedIn = function () {
        if($localStorage.springWebUser){
            return true;
        }else{
            return false;
        }
    };

    if($localStorage.springWebUser){
        try {
            let jwt = $localStorage.springWebUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currenttime = parseInt(new Date().getTime() / 1000);
            console.log(currenttime)
            if(currenttime > payload.exp){
                console.log("Токен истек");
                delete $localStorage.springWebUser;
                $http.defaults.headers.common.Authorization = '';
            }
        }catch (e){

        }
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }



    $scope.loadAllTasks();

});