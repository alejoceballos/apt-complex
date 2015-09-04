(function() {
    var app = angular.module('app-apartment', []);

    app.controller('ApartmentController',
        [
            '$scope',
            'remoteApiService',
            'domainService',
            function($scope, $rootScope, remoteApiService, domainService) {
                console.log(">> Apartment Controller");
            }
        ]
    );

    var controllerData = {
    };

})();
