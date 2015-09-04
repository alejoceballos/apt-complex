(function() {
    'use strict';

    var app = angular.module('apt-complex-app',
        [
            'app-i18n',
            'app-remote-api',
            'app-menu-bar',
            'app-domain',
            'app-balance-summary',
            'app-apartment',
            'ngRoute',
            'ui.bootstrap',
            'ui.bootstrap.tpls',
            'angular-growl',
            'ngAnimate',
            'ngResource',
            'nsPopover',
            'ngTable'
        ]);

    var VIEW_PATH = '/javascript/view/';

    app.config(
        [
            '$routeProvider',
            function($routeProvider) {
                'use strict';

                $routeProvider.
                    when('/about', {
                        templateUrl: VIEW_PATH + 'about/about.tpl.html'
                    }).
                    when('/summary', {
                        templateUrl: VIEW_PATH + 'summary/balance-summary.tpl.html'
                    }).
                    when('/apartment', {
                        templateUrl: VIEW_PATH + 'apartment/apartment.tpl.html'
                    }).
                    otherwise({
                        redirectTo: '/about'
                    });
            }
        ]
    );

    app.config(
        [
            'growlProvider',
            function(growlProvider) {
                growlProvider.globalDisableCountDown(true);
                growlProvider.onlyUniqueMessages(false);
                growlProvider.globalReversedOrder(false);
                growlProvider.globalTimeToLive(3000);
            }
        ]
    );

})();
