/*global angular,window,console*/

(function () {

    'use strict';

    /* Controllers */

    var midasControllers = angular.module('midasControllers', []);

    midasControllers.controller('MainCtrl', ['$scope', '$location', 'User',
        function ($scope, $location, User) {

            $scope.user = User.loggedIn();

            $scope.isActiveLink = function (page) {
                var currentRoute = $location.path().substring(1) || 'home';
                return page === currentRoute ? 'active' : '';
            };

            $scope.zoeken = function () {
                window.alert("TODO: Stay tuned !");
            };

        }]);

    midasControllers.controller('AlertCtrl', ['$scope', 'AlertService',
        function ($scope, alertService) {

            $scope.closeAlert = function (index) {
                alertService.closeAlertForIndex(index);
            };

        }]);

    midasControllers.controller('HomeCtrl', ['$scope', 'Control', '$http',
        function ($scope, Control, $http) {

            $scope.addNotification = function () {
                $http.get('/midas/rest/toto');
            };

        }]);

    midasControllers.controller('ControlListCtrl', ['$scope', 'Control',
        function ($scope, Control) {

            // Remember it is async !
            $scope.controls = Control.query();

            $scope.filterOptions = {
                filterText: "",
                useExternalFilter: true
            };
            $scope.totalServerItems = 0;
            $scope.pagingOptions = {
                pageSizes: [25, 50, 100],
                pageSize: 25,
                currentPage: 1
            };

            // TODO fill in to configure data fetch whenever paging/filtering options changes

            $scope.gridOptions = {
                data: 'controls',
                enablePaging: true,
                showFooter: true,
                totalServerItems: 'totalServerItems',
                pagingOptions: $scope.pagingOptions,
                filterOptions: $scope.filterOptions
            };

        }]);

    midasControllers.controller('ControlDetailCtrl', ['$scope', '$routeParams', 'Control',
        function ($scope, $routeParams, Control) {
            $scope.control = Control.get({uniekeSleutel: $routeParams.controlId}, function (control) {
                //$scope.mainImageUrl = phone.images[0];
            });
        }]);

    midasControllers.controller('ControlCreateCtrl', [ '$scope', 'Control', function ($scope, Control) {

        $scope.popover = {title: 'TODO Title', content: 'Hello Popover<br />This is a multiline message!'};

        var defaultControle = {
        };

        $scope.nieuwControle = angular.copy(defaultControle);

//        $scope.addUntilDate = function () {
//            $scope.newControl.withUntilDate = true;
//        };
//
//        $scope.removeUntilDate = function () {
//            $scope.newControl.withUntilDate = undefined;
//        };

//        $scope.resetForm = function () {
//            $scope.newControl = angular.copy(defaultControl);
//            $scope.form.$setPristine();
//        };

        $scope.isUnchanged = function (controle) {
            return angular.equals(controle, defaultControle);
        };

        $scope.saveControle = function () {

            var testCommand = new Control({
                uitvoerendToezichthouder: "me & my guitar",
                onderwerp: "onderwerp",
                programmaOnderdelen: ["programma onderdelen"],
                internBegeleider: ["intern begeleider"]
            });

            testCommand.$save();
        };

    }]);

}());