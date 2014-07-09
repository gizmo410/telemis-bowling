/*global angular,console*/

(function () {

    'use strict';

    /* App Module */

    var bowlingApp = angular.module('bowlingApp', [
        // Angular Deps
        'ngRoute',
        'ngAnimate',
        'ngSanitize',

        // Internal Deps
        'bowlingControllers',
        'bowlingServices',

        // External Deps
        'ngGrid',
        'ui.bootstrap',
        'mgcrea.ngStrap'
    ]);

    /**
     * Configure routing within the app.
     */
    bowlingApp.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: '/bowling/partials/home.html',
                controller: 'HomeCtrl'
            }).
            when('/games/:gameId', {
                templateUrl: '/bowling/partials/game/game-detail.html',
                controller: 'GameDetailCtrl'
            }).
            when('/newGame', {
                templateUrl: '/bowling/partials/game/game-create.html',
                controller: 'GameCreateCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);

    /**
     * Configure error interception within the app.
     */
    bowlingApp.config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
    }]);


    bowlingApp.run([ 'MessageService', function (messageService) {
        messageService.connect();

    }]);

}());