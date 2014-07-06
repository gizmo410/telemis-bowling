/*global angular,console*/

(function () {

    'use strict';

    /* App Module */

    var midasApp = angular.module('midasApp', [
        // Angular Deps
        'ngRoute',
        'ngCookies',
        'ngAnimate',
        'ngSanitize',

        // Internal Deps
        'midasControllers',
        'midasServices',

        // External Deps
        'http-auth-interceptor',
        'pascalprecht.translate',
        'ngGrid',
        'ui.bootstrap',
        'mgcrea.ngStrap'
    ]);

    /**
     * Configure routing within the app.
     */
    midasApp.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'partials/home.html',
                controller: 'HomeCtrl'
            }).
            when('/controles', {
                templateUrl: 'partials/control/control-list.html',
                controller: 'ControlListCtrl'
            }).
            when('/controles/:controlId', {
                templateUrl: 'partials/control/control-detail.html',
                controller: 'ControlDetailCtrl'
            }).
            when('/nieuwControle', {
                templateUrl: 'partials/control/control-create.html',
                controller: 'ControlCreateCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);

    /**
     * Configure error interception within the app.
     */
    midasApp.config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
    }]);

    /**
     * Configuring I18n.
     */
    midasApp.config(['$translateProvider', function ($translateProvider) {

        $translateProvider.preferredLanguage('nl');

        $translateProvider.useStaticFilesLoader({
            prefix: 'languages/',
            suffix: '.json'
        });

        $translateProvider.useLocalStorage();

    }]);

//    midasApp.config(function ($popoverProvider) {
//        angular.extend($popoverProvider.defaults, {
//            html: true
//        });
//    });


    midasApp.run([ 'authService', '$rootScope', '$window', 'MessageService',
        function (authService, $scope, $window, messageService) {

            /**
             * Will be triggered by the "http-auth-interceptor" whenever the user needs to be logged in.
             */
            $scope.$on('event:auth-loginRequired', function () {
                $window.location.reload();
            });

            /**
             * Will be triggered by the "http-auth-interceptor" when the user has been successful logged in..
             */
            $scope.$on('event:auth-loginConfirmed', function () {
            });

            authService.loginConfirmed();

            messageService.connect();

        }]);

}());