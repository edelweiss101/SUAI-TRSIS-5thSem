var app = angular.module('SecSysApp', []);


app.service('myData', function(){
    this.employeesList = [];
    this.roomsList = [];
});


app.controller('EmployeeController', function ($scope, $http, myData) {
    // Получение списка сотрудников
    $scope.getEmployees = function () {
        $http.get('/company/employees').then(
            function success(response) {
                myData.employeesList = response.data;
                console.log("Employees: ");
                console.log(myData.employeesList);
            }
        );
    };
    $scope.getEmployeesList = function() {
        return myData.employeesList;
    }

    // Добавление/изменение сотрудника
    $scope.addEmployee = function (newEmployee, newEmployeeForm) {
        if (newEmployeeForm.$valid) {
            $http({
                method: 'POST', url: '/company/employees/' + newEmployee.id,
                params: {
                    'name': newEmployee.name,
                    'department': newEmployee.department,
                    'position': newEmployee.position
                }
            }).then(
                function success(response) {
                    $scope.getEmployees();
                    switch (response.status) {
                        case 200:
                            $scope.employee_add_status = "Успешно добавлено";
                            break;
                        case 208:
                            $scope.employee_add_status = "Успешно изменено";
                            break;
                        default:
                            console.log(response);
                            $scope.employee_add_status = "Неизвестный код ответа!";
                            break;
                    }
                    document.getElementById('employeeID').value = '';
                    document.getElementById('employeeName').value = '';
                    document.getElementById('employeeDepartment').value = '';
                    document.getElementById('employeePosition').value = '';
                },
                function error(response) {
                    console.log("addEmployee error: ")
                    console.log(response);
                }
            );
        }
    };

    // Удаление сотрудника
    $scope.deleteEmployee = function (id) {
        $http.delete('/company/employees/' + id).then(
            function success(response) {
                myData.employeesList.splice(
                    myData.employeesList.findIndex(el => el.id == id), 1
                );
                $scope.del_status = "Успешно удалено";
            },
            function error(response) {
                console.log(response);
                $scope.del_status = "ID не найден!";
            }
        );
    };
});


app.controller('RoomController', function ($scope, $http, myData) {
    // Получение списка сотрудников
    $scope.getRooms = function () {
        $http.get('/company/rooms').then(
            function success(response) {
                myData.roomsList = response.data;
                console.log("Rooms: ");
                console.log(myData.roomsList);
            }
        );
    };
    $scope.getRoomsList = function() {
        return myData.roomsList;
    }

    // Добавление/изменение сотрудника
    $scope.addRoom = function (newRoom, newRoomForm) {
        if (newRoomForm.$valid) {
            $http({
                method: 'POST', url: '/company/rooms/' + newRoom.id,
                params: {
                    'name': newRoom.name,
                    'department': newRoom.department,
                    'capacity': newRoom.capacity
                }
            }).then(
                function success(response) {
                    $scope.getRooms();
                    switch (response.status) {
                        case 200:
                            $scope.room_add_status = "Успешно добавлено";
                            break;
                        case 208:
                            $scope.room_add_status = "Успешно изменено";
                            break;
                        default:
                            console.log(response);
                            $scope.room_add_status = "Неизвестный код ответа!";
                            break;
                    }
                    document.getElementById('roomID').value = '';
                    document.getElementById('roomName').value = '';
                    document.getElementById('roomDepartment').value = '';
                    document.getElementById('roomCapacity').value = '';
                },
                function error(response) {
                    console.log("addRoom error: ")
                    console.log(response);
                }
            );
        }
    };

    // Удаление сотрудника
    $scope.deleteRoom = function (id) {
        $http.delete('/company/rooms/' + id).then(
            function success(response) {
                myData.roomsList.splice(
                    myData.roomsList.findIndex(el => el.id == id), 1
                );
                $scope.del_status = "Успешно удалено";
            },
            function error(response) {
                console.log(response);
                $scope.del_status = "ID не найден!";
            }
        );
    };
});


app.controller('AccessController', function ($scope, $http) {
    // Проверка доступа
    $scope.checkAccess = function (e_id, r_id) {
        $http.get('/company/access/' + e_id + "-" + r_id).then(
            function success(response) {
                $scope.check_status = "Доступ разрешен";
            },
            function error(response) {
                $scope.check_status = "Доступ запрещен / ID не найден!";
            }
        );
    };

    // Выдача доступа
    $scope.grantAccess = function (e_id, r_id) {
        $http.post('/company/access/' + e_id + "-" + r_id).then(
            function success(response) {
                $scope.grant_status = "Доступ выдан";
            },
            function error(response) {
                $scope.grant_status = "ID не найден!";
            }
        );
    };

    // Отзыв доступа
    $scope.revokeAccess = function (e_id, r_id) {
        $http.delete('/company/access/' + e_id + "-" + r_id).then(
            function success(response) {
                $scope.revoke_status = "Доступ отозван";
            },
            function error(response) {
                $scope.revoke_status = "ID не найден!";
            }
        );
    };
});
