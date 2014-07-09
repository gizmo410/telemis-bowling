/*global describe,it,beforeEach,afterEach,expect,module,inject,spyOn,window,Stomp*/

(function () {

    'use strict';

    /**
     * Testing the Angular components.
     */
    describe("Angular Module Suite", function () {

        var $rootScope, $controller,
            mainCtrlScope, mainCtrl,
            homeCtrlScope, homeCtrl,
            gameCreateCtrl, gameCreateCtrlScope,
            $httpBackend, $location;

        /**
         * mock Application to allow us to inject our own dependencies
         */
        beforeEach(module("bowlingApp"));

        /**
         * Mock the controller + $http and $location service
         */
        beforeEach(inject([ '$injector', function ($injector) {

            $httpBackend = $injector.get('$httpBackend');

            $location = $injector.get('$location');

            $controller = $injector.get('$controller');
            $rootScope = $injector.get('$rootScope');

        }]));

        /**
         * Assert that every (and only) expected request have been triggered
         */
        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });


        /**
         * Testing the Main Controller.
         */
        describe("Main Controller Suite", function () {

            beforeEach(inject(function () {

                mainCtrlScope = $rootScope.$new();
                mainCtrl = $controller('MainCtrl', {$scope: mainCtrlScope, $location: $location});

            }));

            it("should have a scope with 1 method", function () {

                expect(mainCtrlScope.isActiveLink).toBeDefined();

            });

        });

        /**
         * Testing the Home Controller.
         */
        describe("Home Controller Suite", function () {

            beforeEach(inject(function () {

                homeCtrlScope = $rootScope.$new();
                homeCtrl = $controller('HomeCtrl', {$scope: homeCtrlScope});

            }));

            it("should have a scope with a testNotification() method", function () {

                expect(homeCtrlScope.testNotification).toBeDefined();

            });

        });


        /**
         * Testing the Create Game Controller.
         */
        describe("Create Game Controller Suite", function () {

            beforeEach(inject(function () {

                gameCreateCtrlScope = $rootScope.$new();
                gameCreateCtrl = $controller('GameCreateCtrl', {$scope: gameCreateCtrlScope});

            }));

            it("should have a scope with a saveGame() method", function () {

                expect(gameCreateCtrlScope.saveGame).toBeDefined();

            });

            it("should save a game when saveGame is called", function () {

                var expectedGameToBeSaved = {
                    "numberOfPlayers": 2
                };

                $httpBackend.expectPOST('/bowling/rest/games/action', expectedGameToBeSaved).respond(200);

                gameCreateCtrlScope.saveGame();

                $httpBackend.flush();

            });

        });

    });

}());
