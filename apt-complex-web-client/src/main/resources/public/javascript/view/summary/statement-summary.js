(function() {
    var app = angular.module('app-statement-summary', []);

    app.controller('StatementSummaryController',
        [
            "$scope",
            "$rootScope",
            "remoteApiService",
            "domainService",
            function($scope, $rootScope, remoteApiService, domainService) {
                console.log(">> Statement Summary Controller");
                this.data = controllerData;

                function updateView(aDate) {
                    remoteApiService.statement.summary(
                        aDate.getFullYear(),
                        aDate.getMonth() + 1
                    ).then(
                        function(result) {
                            if (result.data && result.data != null) {
                                controllerData.statement = domainService.MonthlyStatement.build(result.data);
                                controllerData.apartmentsStatements = controllerData.statement.apartmentsStatements();

                            } else {
                                controllerData.statement = null;
                                controllerData.apartmentsStatements = [];
                            }
                        }
                    ).catch(
                        function(err) {
                            console.log("-- StatementSummaryController: " + err);
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
        statement: null,
        apartmentsStatements: []
    };

})();