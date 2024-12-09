var app = angular.module('SecSysApp', []).config(function ($httpProvider) {
    csrftoken = $("meta[name='_csrf']").attr("content");
    csrfheader = $("meta[name='_csrf_header']").attr("content");
    $httpProvider.defaults.headers.common["X-CSRF-TOKEN"] = csrftoken;
});


app.service('myData', function () {
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
    $scope.getEmployeesList = function () {
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
                },
                responseType: 'json'
            }).then(
                function success(response) {
                    $scope.getEmployees();
                    console.log("addEmployee success: ");
                    console.log(response);
                    switch (response.status) {
                        case 200:
                            $scope.employee_add_status = add_success;
                            break;
                        case 208:
                            $scope.employee_add_status = add_edited;
                            break;
                        default:
                            $scope.employee_add_status = add_unknown_answer_code;
                    };
                    document.getElementById('employeeID').value = '';
                    document.getElementById('employeeName').value = '';
                    document.getElementById('employeeDepartment').value = '';
                    document.getElementById('employeePosition').value = '';
                },
                function error(response) {
                    switch (response.status) {
                        case 401:
                            alert(alert401);
                            break;
                        case 403:
                            alert(alert403);
                            break;
                        default:
                            console.log("addEmployee error: ");
                            console.log(response);
                            break;
                    };
                }
            );
        }
    };

    // Удаление сотрудника
    $scope.deleteEmployee = function (id) {
        $http.delete('/company/employees/' + id, { responseType: 'json' }).then(
            function success(response) {
                myData.employeesList.splice(
                    myData.employeesList.findIndex(el => el.id == id), 1
                );
                $scope.del_status = "Успешно удалено";
            },
            function error(response) {
                switch (response.status) {
                    case 401:
                        alert(alert401);
                        break;
                    case 403:
                        alert(alert403);
                        break;
                    case 404:
                        $scope.employee_del_status = "ID не найден!";
                        break;
                    default:
                        console.log("deleteEmployee error: ")
                        console.log(response);
                };
            }
        );
    };
});


app.controller('RoomController', function ($scope, $http, myData) {
    // Получение списка комнат
    $scope.getRooms = function () {
        $http.get('/company/rooms').then(
            function success(response) {
                myData.roomsList = response.data;
                console.log("Rooms: ");
                console.log(myData.roomsList);
            }
        );
    };
    $scope.getRoomsList = function () {
        return myData.roomsList;
    }

    // Добавление/изменение комнаты
    $scope.addRoom = function (newRoom, newRoomForm) {
        if (newRoomForm.$valid) {
            $http({
                method: 'POST', url: '/company/rooms/' + newRoom.id,
                params: {
                    'name': newRoom.name,
                    'department': newRoom.department,
                    'capacity': newRoom.capacity
                },
                responseType: 'json'
            }).then(
                function success(response) {
                    $scope.getRooms();
                    console.log("addRoom success: ")
                    console.log(response);
                    switch (response.status) {
                        case 200:
                            $scope.room_add_status = add_success;
                            break;
                        case 208:
                            $scope.room_add_status = add_edited;
                            break;
                        default:
                            $scope.room_add_status = add_unknown_answer_code;
                    };
                    document.getElementById('roomID').value = '';
                    document.getElementById('roomName').value = '';
                    document.getElementById('roomDepartment').value = '';
                    document.getElementById('roomCapacity').value = '';
                },
                function error(response) {
                    switch (response.status) {
                        case 401:
                            alert(alert401);
                            break;
                        case 403:
                            alert(alert403);
                            break;
                        default:
                            console.log("addRoom error: ")
                            console.log(response);
                    };
                }
            );
        }
    };

    // Удаление комнаты
    $scope.deleteRoom = function (id) {
        $http.delete('/company/rooms/' + id, { responseType: 'json' }).then(
            function success(response) {
                myData.roomsList.splice(
                    myData.roomsList.findIndex(el => el.id == id), 1
                );
                $scope.del_status = "Успешно удалено";
            },
            function error(response) {
                switch (response.status) {
                    case 401:
                        alert(alert401);
                        break;
                    case 403:
                        alert(alert403);
                        break;
                    case 404:
                        $scope.room_del_status = "ID не найден!";
                        break;
                    default:
                        console.log("deleteRoom error: ")
                        console.log(response);
                };
            }
        );
    };
});


app.controller('AccessController', function ($scope, $http) {
    // Проверка доступа
    $scope.checkAccess = function (e_id, r_id) {
        $http.get('/company/access/' + e_id + "-" + r_id, { responseType: 'json' }).then(
            function success(response) {
                $scope.check_status = access_check_success;
            },
            function error(response) {
                switch (response.status) {
                    case 401:
                        alert(alert401);
                        break;
                    case 403:
                        alert(alert403);
                        break;
                    case 404:
                        $scope.check_status = access_check_error;
                        break;
                    default:
                        console.log("checkAccess error: ")
                        console.log(response);
                };
            }
        );
    };

    // Выдача доступа
    $scope.grantAccess = function (e_id, r_id) {
        $http.post('/company/access/' + e_id + "-" + r_id, {}, { responseType: 'json' }).then(
            function success(response) {
                $scope.grant_status = access_grant_success;
            },
            function error(response) {
                switch (response.status) {
                    case 401:
                        alert(alert401);
                        break;
                    case 403:
                        alert(alert403);
                        break;
                    case 404:
                        $scope.grant_status = access_error;
                        break;
                    default:
                        console.log("grantAccess error: ")
                        console.log(response);
                };
            }
        );
    };

    // Отзыв доступа
    $scope.revokeAccess = function (e_id, r_id) {
        $http.delete('/company/access/' + e_id + "-" + r_id, { responseType: 'json' }).then(
            function success(response) {
                $scope.revoke_status = access_revoke_success;
            },
            function error(response) {
                switch (response.status) {
                    case 401:
                        alert(alert401);
                        break;
                    case 403:
                        alert(alert403);
                        break;
                    case 404:
                        $scope.revoke_status = access_error;
                        break;
                    default:
                        console.log("revokeAccess error: ")
                        console.log(response);
                };
            }
        );
    };
});
