(function() {
    'use strict';

    var app = angular.module('apt-complex-app',
        [
            'app-i18n',
            'app-remote-api',
            'app-view-menu-bar',
            'app-domain',
            'app-summary',
            'ngRoute',
            'ui.bootstrap',
            'ui.bootstrap.tpls',
            'angular-growl',
            'ngAnimate',
            'ngResource',
            'nsPopover'
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
                        templateUrl: VIEW_PATH + 'summary/summary.tpl.html'
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
