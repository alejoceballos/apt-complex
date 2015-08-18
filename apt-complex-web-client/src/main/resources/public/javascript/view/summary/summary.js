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
                                controllerData.totals = controllerData.balance.totals();
                                controllerData.totals.fee.total =
                                    controllerData.totals.fee.normal +
                                    controllerData.totals.fee.surcharge +
                                    controllerData.totals.fee.other;

                                controllerData.apartmentsBalances = controllerData.balance.apartmentsBalances();
                                for (var abIdx in controllerData.apartmentsBalances) {
                                    var aptBal = controllerData.apartmentsBalances[abIdx];
                                    aptBal.totals = aptBal.totals();
                                }

                            } else {
                                controllerData.balance = null;
                                controllerData.totals = {
                                    fee: {
                                        total: 0,
                                        normal: 0,
                                        surcharge: 0,
                                        other: 0
                                    },
                                    payment: 0
                                };
                                controllerData.apartmentsBalances = [];
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
            fee: {
                total: 0,
                normal: 0,
                surcharge: 0,
                other: 0
            },
            payment: 0
        },
        apartmentsBalances: []
    };

})();