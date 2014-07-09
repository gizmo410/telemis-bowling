/*global angular,console,SockJS,Stomp*/

(function () {

    'use strict';

    /* Services */

    var bowlingServices = angular.module('bowlingServices', ['ngResource']);

    bowlingServices.factory('AlertService', [ '$rootScope', '$timeout',
        function ($rootScope, $timeout) {

            var alertService = {};

            $rootScope.alerts = [];

            alertService.add = function (type, msg, timeoutDuration) {

                var newAlert = {'type': type, 'msg': msg}, timeoutCallBack;

                $rootScope.alerts.push(newAlert);

                if (timeoutDuration) {

                    timeoutCallBack = function () {
                        alertService.closeAlert(newAlert);
                    };

                    $timeout(timeoutCallBack, timeoutDuration);
                }

            };

            alertService.closeAlert = function (alert) {
                var alertIndex = $rootScope.alerts.indexOf(alert);
                return this.closeAlertForIndex(alertIndex);
            };

            alertService.closeAlertForIndex = function (alertIndex) {
                return $rootScope.alerts.splice(alertIndex, 1);
            };

            return alertService;
        }]);

    bowlingServices.factory('myHttpInterceptor', [ '$q', 'AlertService',
        function ($q, alertService) {

            var httpInterceptor = {},
                errorTimeout = 5000;

            httpInterceptor.handleError = function (rejection) {
                var errorMessage;
                if (rejection.status === 500) {
                    errorMessage = 'Oops... An unexpected error has occured';
                } else if (rejection.status === 404) {
                    errorMessage = 'You little rascal ! You just tried to get a resource that does not exist';
                }
                alertService.add('danger', errorMessage, errorTimeout);
            };

            httpInterceptor.request = function (config) {
                // do something on success
                return config;
            };

            httpInterceptor.requestError = function (rejection) {
                // do something on error
                httpInterceptor.handleError(rejection);
                return $q.reject(rejection);
            };

            httpInterceptor.response = function (response) {
                // do something on success
                return response;
            };

            httpInterceptor.responseError = function (rejection) {
                // do something on error
                httpInterceptor.handleError(rejection);
                return $q.reject(rejection);
            };

            return httpInterceptor;
        }]);

    bowlingServices.factory('MessageService', [ 'AlertService', '$timeout', '$rootScope',
        function (alertService, $timeout, $rootScope) {

            var errorTimeout = 5000,
                infoTimeout = 3000,
                notificationTimeout = 10,
                mySocket = {},
                sockjs = new SockJS('http://localhost:8080/bowling/rest/stomp'),
                stomp = Stomp.over(sockjs);

            mySocket.notify = function (message) {
                $timeout(function () {
                    var eventName = 'event:' + message.headers.clazz;
                    $rootScope.$broadcast(eventName, JSON.parse(message.body));
                }, notificationTimeout);
            };

            mySocket.connect = function () {
                stomp.connect({},
                    // Connect callback
                    function () {
                        stomp.subscribe("/topic/domain-events", mySocket.notify);

                    },
                    // Error callback
                    function () {
                        $timeout(function () {
                            alertService.add('danger', 'Connection broken', errorTimeout);
                        }, notificationTimeout);
                    });
            };

            mySocket.disconnect = function () {
                stomp.disconnect();
            };

            return mySocket;
        }]);


    // Resource services

    bowlingServices.factory('Game', ['$resource',
        function ($resource) {
            return $resource('/bowling/rest/games/:gameId', {identifier: '@gameId'}, {
                'query': { method: 'GET', isArray: false },
                'get': {  method: 'GET', url: '/bowling/rest/games/:gameId', isArray: false},
                'save': { method: 'POST', url: '/bowling/rest/games/action' },
                'update': { method: 'PUT', url: '/bowling/rest/games/:gameId/action' },
                'delete': { method: 'DELETE', url: '/bowling/rest/games/:gameId/action' }
            });
        }]);

}());
