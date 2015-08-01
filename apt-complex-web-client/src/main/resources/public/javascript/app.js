(function() {
    'use strict';

    var app = angular.module('apt-complex-app',
        [
            'apt-complex-apartment',
            'apt-complex-menu-bar',
            'ngRoute',
            'ui.bootstrap',
            'ui.bootstrap.tpls',
            'angular-growl',
            'ngAnimate'
        ]);

    app.config(['$routeProvider', function($routeProvider) {
        'use strict';

        $routeProvider.
            when('/about', {
                templateUrl: '/javascript/about/about.tpl.html'
            }).
            when('/apartment', {
                templateUrl: '/javascript/apartment/apartment.tpl.html'
            }).
            otherwise({
                redirectTo: '/about'
            });
    }]);

    app.config(['growlProvider',
        function(growlProvider) {
            growlProvider.globalDisableCountDown(true);
            growlProvider.globalTimeToLive(5000);
        }
    ]);

})();
