/*global angular,console,SockJS,Stomp*/

(function () {

    'use strict';

    /* Services */

    var midasServices = angular.module('midasServices', ['ngResource']);

    midasServices.factory('AlertService', [ '$rootScope', '$timeout',
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

    midasServices.factory('myHttpInterceptor', [ '$q', 'AlertService', '$filter', function ($q, alertService, $filter) {

        var httpInterceptor = {},
            errorTimeout = 5000;

        httpInterceptor.handleError = function (rejection) {
            var errorMessage;
            if (rejection.status === 500) {
                errorMessage = $filter('translate')('general_internal_error');
            } else if (rejection.status === 404) {
                errorMessage = $filter('translate')('general_not_found_error');
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

    midasServices.factory('MessageService', [ 'AlertService', '$timeout', function (alertService, $timeout) {

        var errorTimeout = 5000,
            infoTimeout = 3000,
            mySocket = {},
            sockjs = new SockJS('http://localhost:8080/midas/rest/stomp'),
            stomp = Stomp.over(sockjs);

        mySocket.notify = function (message) {
            console.log('message received : ' + message);
            alertService.add('info', message.data, infoTimeout);
        };

        mySocket.connect = function () {
            stomp.connect({},
                // Connect callback
                function () {
                    stomp.subscribe("/topic", mySocket.notify);

                },
                // Error callback
                function () {
                    $timeout(function () {
                        alertService.add('danger', 'TODO connection broken', errorTimeout);
                    }, 10);
                });
        };

        mySocket.disconnect = function () {
            stomp.disconnect();
        };

        return mySocket;
    }]);


    // Resource services

    midasServices.factory('Medewerker', ['$resource',
        function ($resource) {
            return $resource('/midas/rest/medewerkers', {}, {});
        }]);

    midasServices.factory('Control', ['$resource',
        function ($resource) {
            return $resource('/midas/rest/controles/:controlId', {uniekeSleutel: '@controlId'}, {
                'query': { method: 'GET', isArray: false },
                'save': { method: 'POST', url: '/midas/rest/controles/action' },
                'update': { method: 'PUT', url: '/midas/rest/controles/:controlId/action' },
                'delete': { method: 'DELETE', url: '/midas/rest/controles/:controlId/action' }
            });
        }]);

    midasServices.factory('User', ['$resource',
        function ($resource) {
            return $resource('/midas/rest/users', {}, {
                'loggedIn': { method: 'GET', url: '/midas/rest/users/loggedIn' }
            });
        }]);

}());
