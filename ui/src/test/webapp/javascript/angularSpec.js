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
            controlListScope, controlListCtrl,
            controlCreateCtrl, controlCreateCtrlScope,
            $httpBackend, $location;

        /**
         * mock Application to allow us to inject our own dependencies
         */
        beforeEach(module("midasApp"));

        /**
         * Mock the controller + $http and $location service
         */
        beforeEach(inject([ '$injector', function ($injector) {

            $httpBackend = $injector.get('$httpBackend');
            $httpBackend.expect('GET', 'languages/nl.json').respond(200);

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

                var user = {
                    username: "Anakin Skywalker",
                    roles: []
                };
                $httpBackend.expect('GET', '/midas/rest/users/loggedIn').respond(200, user);

                mainCtrlScope = $rootScope.$new();
                mainCtrl = $controller('MainCtrl', {$scope: mainCtrlScope, $location: $location});

            }));

            it("should have a scope with 2 method", function () {

                expect(mainCtrlScope.isActiveLink).toBeDefined();
                expect(mainCtrlScope.zoeken).toBeDefined();

                $httpBackend.flush();

            });

            it("should have the user in its scope", function () {

                $httpBackend.flush();

                expect(mainCtrlScope.user).not.toBeUndefined();
                expect(mainCtrlScope.user.username).toEqual("Anakin Skywalker");
                expect(mainCtrlScope.user.roles).toEqual([]);


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

            it("should have a scope with a addNotification() method", function () {

                expect(homeCtrlScope.addNotification).toBeDefined();

                $httpBackend.flush();

            });

        });

        /**
         * Testing the Control List Controller.
         */
        describe("ControlList Controller Suite", function () {

            var eenTestControle, controles;

            beforeEach(inject(function () {

                eenTestControle = {
                    "uitvoerendToezichthouder": "me & my guitar",
                    "kenmerk": null,
                    "onderwerp": "onderwerp",
                    "omschrijving": null,
                    "besluit": null,
                    "alleenBesluit": false,
                    "status": null,
                    "programmaOnderdelen": [ "programma onderdelen" ],
                    "internBegeleiders": [ "intern begeleider" ],
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/midas/rest/controles/00855658-d7dd-4504-a11b-c6272b87bee5"
                        },
                        "action": {
                            "href": "http://localhost:8080/midas/rest/controles/00855658-d7dd-4504-a11b-c6272b87bee5/action"
                        }
                    }
                };

                controles = {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/midas/rest/controles{?page,size,sort}",
                            "templated": true
                        }
                    },
                    "_embedded": {
                        "controles": [
                            eenTestControle
                        ]
                    },
                    "page": {
                        "size": 20,
                        "totalElements": 1,
                        "totalPages": 1,
                        "number": 0
                    }
                };

                $httpBackend.expect('GET', '/midas/rest/controles').respond(200, controles);

                controlListScope = $rootScope.$new();
                controlListCtrl = $controller('ControlListCtrl', {$scope: controlListScope});

            }));


            it("should contain list of controls", function () {

                $httpBackend.flush();

                var expectedResult = {"_links": {
                    "self": {
                        "href": "http://localhost:8080/midas/rest/controles{?page,size,sort}",
                        "templated": true
                    }
                }, "_embedded": {
                    "controles": [
                        {
                            "uitvoerendToezichthouder": "me & my guitar",
                            "kenmerk": null,
                            "onderwerp": "onderwerp",
                            "omschrijving": null,
                            "besluit": null,
                            "alleenBesluit": false,
                            "status": null,
                            "programmaOnderdelen": ["programma onderdelen"],
                            "internBegeleiders": ["intern begeleider"],
                            "_links": {
                                "self": {
                                    "href": "http://localhost:8080/midas/rest/controles/00855658-d7dd-4504-a11b-c6272b87bee5"
                                },
                                "action": {
                                    "href": "http://localhost:8080/midas/rest/controles/00855658-d7dd-4504-a11b-c6272b87bee5/action"
                                }
                            }
                        }
                    ]
                }, "page": {
                    "size": 20,
                    "totalElements": 1,
                    "totalPages": 1,
                    "number": 0
                }, "$promise": {}, "$resolved": true};

                expect(JSON.stringify(controlListScope.controls)).toEqual(JSON.stringify(expectedResult));

            });
        });

        /**
         * Testing the Create Controle Controller.
         */
        describe("Create Controle Controller Suite", function () {

            beforeEach(inject(function () {

                controlCreateCtrlScope = $rootScope.$new();
                controlCreateCtrl = $controller('ControlCreateCtrl', {$scope: controlCreateCtrlScope});

            }));

            it("should have a scope with a saveControle() method", function () {

                expect(controlCreateCtrlScope.saveControle).toBeDefined();

                $httpBackend.flush();

            });

            it("should save a controle when saveControle is called", function () {

                var expectedControleToBeSaved = {
                    "uitvoerendToezichthouder": "me & my guitar",
                    "onderwerp": "onderwerp",
                    "programmaOnderdelen": ["programma onderdelen"],
                    "internBegeleider": ["intern begeleider"]
                };

                $httpBackend.expectPOST('/midas/rest/controles/action', expectedControleToBeSaved).respond(200);

                controlCreateCtrlScope.saveControle();

                $httpBackend.flush();

            });

        });

    });

}());