(function() {
    var app = angular.module('apt-complex-menu-bar',
        [
            "ngResource"
        ]
    );

    app.factory('remoteCallSpinnerInterceptor',
        [
            "$q",
            "$window",
            function ($q, $window) {
                return {
                    'request': function(config) {
                        $("#remote-call-spinner").show();
                        return config;
                    },

                    'requestError': function(rejection) {
                        $("#remote-call-spinner").hide();
                        return $q.reject(rejection);
                    },

                    'response': function(response) {
                        $("#remote-call-spinner").hide();
                        return response;
                    },

                    'responseError': function(rejection) {
                        $("#remote-call-spinner").hide();
                        return $q.reject(rejection);
                    }
                };
            }
        ]
    );

    app.config(
        [
            "$httpProvider",
            function ($httpProvider) {
                $httpProvider.interceptors.push('remoteCallSpinnerInterceptor');
            }
        ]
    );

    app.controller('MenuBarController', function() {
        console.log(">> MenuBarController");
    });

})();