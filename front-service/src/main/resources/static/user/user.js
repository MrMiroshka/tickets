angular.module('tiket').controller('userController', function ($scope, $http, $location, $localStorage) {

    const constPatchUser = 'http://localhost:5555/user-service/v1';
    const constPatchBasket  = 'http://localhost:5555/basket-service/v1/baskets';
    var showUsers = function () {
        document.getElementById("UserList").style.display = "block";
        document.getElementById("FormEdit").style.display = "none";
    };
    var showFormEdit = function () {
        document.getElementById("UserList").style.display = "none";
        document.getElementById("FormEdit").style.display = "block";
    };



    $scope.loadUsers = function () {
        $scope.findPage(0);
    };
    $scope.findPage = function (diffPage) {
        var page = parseInt(document.getElementById("Page").value) + diffPage;
        document.getElementById("Page").value = page;
        $http({
            url: constPatchUser + "/user",
            method: "get",
            params: {
                page: page,
                size: 10,
                name: User ? User.name : null,


            }
        }).then(function (response) {
            $scope.UserList = response.data.content;
            showUsers();
        });

    };
    var User = null;
    $scope.filterUser = function () {
        User = $scope.User;
        document.getElementById("Page").value = "1";
        $scope.findPage(0);
    };

    var UserIdEdit = null;

    $scope.createUser = function () {
        UserIdEdit = null;
        document.getElementById("UserNameEdit").value = "";
        document.getElementById("UserEmail").value = 0;
        $scope.user.id = null;
        showFormEdit();

    };

    $scope.editUser = function (userId) {
        $http.get(constPatchUser + "/user/" + UserId)
            .then(function (response) {
                UserIdEdit = response.data.id;
                $scope.User = response.data;
                console.log($scope.User);

                document.getElementById("UserNameEdit").value = response.data.title;
                document.getElementById("UserEmail").value = response.data.price;
                showFormEdit();
            });
    };
    $scope.deleteUser = function (userId) {
        $http.delete(constPatchUser + "/user/" + userId)
            .then(function (response) {
                $scope.loadUsers();
            });
    };
    $scope.saveUser = function () {
        console.log($scope.User);
        console.log(UserIdEdit);

        $http.post(constPatchUser + "/user",$scope.User)
            .then(function (response) {
                $scope.loadUsers();
            });
    };
    $scope.loadUsers();
})