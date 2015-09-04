(function() {
    var app = angular.module('app-menu-bar',
        [
            "ngResource",
            "pascalprecht.translate",
            'angularMoment',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker'

        ]
    );

    app.factory('remoteCallSpinnerInterceptor',
        [
            "$q",
            "$window",
            function ($q, $window) {
                return {
                    "request": function(config) {
                        $("#remote-call-spinner").show();
                        $("#remote-call-spinner-hidden").hide();
                        return config;
                    },

                    "requestError": function(rejection) {
                        $("#remote-call-spinner").hide();
                        $("#remote-call-spinner-hidden").show();
                        return $q.reject(rejection);
                    },

                    "response": function(response) {
                        $("#remote-call-spinner").hide();
                        $("#remote-call-spinner-hidden").show();
                        return response;
                    },

                    "responseError": function(rejection) {
                        $("#remote-call-spinner").hide();
                        $("#remote-call-spinner-hidden").show();
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

    app.controller('MenuBarController',
        [
            "$rootScope",
            "$translate",
            "amMoment",
            "APP_LOCALE",
            function($rootScope, $translate, amMoment, APP_LOCALE) {
                console.log(">> MenuBarController");

                $rootScope.data = {
                    language: APP_LOCALE.EN_US,
                    referenceDate: new Date()
                };

                this.changeLanguage = function(key) {
                    $rootScope.data.language = key;
                    $translate.use(key);

                    if (key == APP_LOCALE.PT_BR) {
                        amMoment.changeLocale("pt-br");
                    } else {
                        amMoment.changeLocale("en");
                    }
                };
            }
        ]
    );

})();