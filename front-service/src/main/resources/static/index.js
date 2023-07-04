angular.module('app', ['ngStorage']).controller('myController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555';


    $scope.loadAllTicket = function (){
        $http.get(contextPath + "/core/ticket")
            .then(function (response){
                $scope.ticketList = response.data;
        });

    };
    $scope.loadPriorities = function() {
        $http.get(contextPath + "/settings/api/v1/priority/findAll")
            .then(function(response) {
                $scope.priorities = response.data;
            });
    };
    $scope.loadTrackers = function() {
        $http.get(contextPath + "/settings/api/v1/tracker/findAll")
            .then(function(response) {
                $scope.trackers = response.data;
            });
    };
    $scope.loadStatuses = function() {
        $http.get(contextPath + "/settings/api/v1/status/findAll")
            .then(function(response) {
                $scope.statuses = response.data;
            });
    };



    $scope.createTicket = function (){
        $http.post(contextPath + "/core/ticket/create", $scope.ticket)
            .then(function (response){
                console.log("Все ОК");
                $scope.loadAllTicket();
            });
    };

    $scope.updateTicketStatus = function() {
        // Получаем выбранный статус из модели
        let selectedStatusId = $scope.ticket.statusTicket;
        console.log(selectedStatusId)
        // Отправляем запрос на сервер для обновления статуса тикета
        $http.put(contextPath + "/core/ticket/updateStatus/" + $scope.ticket.id, { statusId: selectedStatusId })
            .then(function(response) {
                // Обработка успешного обновления статуса
                console.log("Статус тикета успешно обновлен");
                $scope.loadAllTicket()
            })
            .catch(function(error) {
                // Обработка ошибки при обновлении статуса
                console.error("Ошибка при обновлении статуса тикета", error);
            });
    };




    $scope.tryToAuth = function () {
        $http.post(contextPath + "/auth/auth", $scope.user)
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




    $scope.loadAllTicket();
    // Загрузка списка приоритетов при инициализации контроллера
    $scope.loadPriorities();
    $scope.loadTrackers();
    $scope.loadStatuses();

});