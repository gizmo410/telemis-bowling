/*global angular,window,console*/

(function () {

    'use strict';

    /* Controllers */

    var bowlingControllers = angular.module('bowlingControllers', []);

    bowlingControllers.controller('MainCtrl', ['$scope', '$location',
        function ($scope, $location) {

            $scope.isActiveLink = function (page) {
                var currentRoute = $location.path().substring(1) || 'home';
                return page === currentRoute ? 'active' : '';
            };

        }]);

    bowlingControllers.controller('AlertCtrl', ['$scope', 'AlertService',
        function ($scope, alertService) {

            $scope.closeAlert = function (index) {
                alertService.closeAlertForIndex(index);
            };

        }]);

    bowlingControllers.controller('HomeCtrl', ['$scope', '$http',
        function ($scope, $http) {

            $scope.testNotification = function () {
                $http.get('/bowling/rest/lost');
            };

        }]);

    bowlingControllers.controller('GameDetailCtrl', ['$scope', '$routeParams', 'Game',
        function ($scope, $routeParams, Game) {

            var getGameSuccessCallback,
                getGame;

            getGameSuccessCallback = function (game) {
                if (game) {
                    $scope.game = game;
                    $scope.players = JSON.stringify(game.players);
                    console.log(JSON.stringify(game.players));
                }
            };

            getGame = function () {
                Game.get({gameId: $routeParams.gameId}, getGameSuccessCallback);
            };

            getGame();


            $scope.$on('event:GameStopped', function (event, data) {
                console.log('event:GameStopped');
                $scope.gameStopped = true;
                $scope.scores = data.playerScores;
            });

            $scope.$on('event:PlayerScoreUpdated', function (event, data) {
                console.log('event:PlayerScoreUpdated');
                $scope.numberOfSkittlesDown = undefined;
                getGame();
            });

            $scope.cancelGame = function () {
                Game.delete({gameId: $routeParams.gameId});
            };

            $scope.updateScore = function () {
                var playerScore = new Game({
                    "gameId": $routeParams.gameId,
                    "numberOfSkittlesDown": $scope.numberOfSkittlesDown
                });

                playerScore.$update({gameId: $routeParams.gameId});

            };

        }]);

    bowlingControllers.controller('GameCreateCtrl', [ '$scope', 'Game', '$location', function ($scope, Game, $location) {

        $scope.$on('event:GameCreated', function (event, data) {
            $location.path('/games/' + data.identifier);
        });

        var defaultGame = {
            numberOfPlayers: 2
        };

        $scope.newGame = angular.copy(defaultGame);

//        $scope.resetForm = function () {
//            $scope.newControl = angular.copy(defaultControl);
//            $scope.form.$setPristine();
//        };

        $scope.isUnchanged = function (game) {
            return angular.equals(game, defaultGame);
        };

        $scope.saveGame = function () {

            var newGame = new Game({
                numberOfPlayers: $scope.newGame.numberOfPlayers
            });

            newGame.$save();
        };

    }]);

}());