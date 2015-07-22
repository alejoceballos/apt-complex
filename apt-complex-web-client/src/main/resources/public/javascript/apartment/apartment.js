(function() {
    var app = angular.module('jk4j-apartment', ['ui.codemirror']);


    app.factory('remoteApi', [
        '$http',
        function($http) {
            var url = "api/apartment/";

            var RemoteApi = function() {
                this.getAll = function() {
                    var options = {
                        method: 'GET',
                        url: url
                    };

                    return $http(options).then(
                        function(response) {
                            return response.data;
                        }
                    ).catch(
                        function(err) {
                            console.log(err);
                            throw err;
                        }
                    );
                };
            };

            return new RemoteApi();
        }
    ]);

    app.controller('ApartmentController', [
        '$scope', 'remoteApi',
        function($scope, remoteApi) {
            console.log(">> ApartmentController");

            this.list = { };

            remoteApi.getAll().then(
                function(result) {
                    this.list = result.data;
                }
            ).catch(
                function(err) {
                    console.log("-- ApartmentController: " + err);
                }
            );
        }
    ]);

})();
