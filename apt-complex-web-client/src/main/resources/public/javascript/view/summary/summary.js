(function() {
    var app = angular.module("app-summary", []);

    app.controller('SummaryController',
        [
            "$scope",
            "$rootScope",
            "remoteApiService",
            "domainService",
            function($scope, $rootScope, remoteApiService, domainService) {
                console.log(">> SummaryController");
                this.data = controllerData;

                function updateView(aDate) {
                    remoteApiService.balance.summary(
                        aDate.getFullYear(),
                        aDate.getMonth() + 1
                    ).then(
                        function(result) {
                            if (result.data && result.data != null) {
                                controllerData.balance = domainService.MonthlyBalance.build(result.data);
                                controllerData.totals = controllerData.balance.getIncomeTotals();

                            } else {
                                controllerData.balance = null;
                                controllerData.totals = {
                                    fee: 0,
                                    payment: 0
                                };
                            }
                        }
                    ).catch(
                        function(err) {
                            console.log("-- SummaryController: " + err);
                        }
                    );
                }

                $scope.$watch(
                    function() {
                        return $rootScope.data.referenceDate;
                    },
                    function(newValue, oldValue){
                        if(newValue === oldValue){
                            return;
                        }

                        updateView(newValue);
                    }
                );

                updateView($rootScope.data.referenceDate);
            }
        ]
    );

    var controllerData = {
        balance: null,
        totals: {
            payment: 0,
            fee: 0
        }
    };

})();