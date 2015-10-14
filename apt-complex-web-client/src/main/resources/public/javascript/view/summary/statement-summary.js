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

                this.operation = {
                    toggleEditMode: function() {
                        controllerData.statementCopy = createDomainObject(controllerData.statement);
                        controllerData.apartmentsStatementsCopy = controllerData.statementCopy.apartmentsStatements();

                        controllerData.editMode = !controllerData.editMode;
                    },

                    applyChanges: function() {
                        remoteApiService.statement.save(controllerData.statementCopy).then(
                            function(result) {
                                controllerData.statement = createDomainObject(controllerData.statementCopy);
                                controllerData.apartmentsStatements = controllerData.statement.apartmentsStatements();

                                controllerData.editMode = !controllerData.editMode;
                            }
                        ).catch(
                            function(err) {
                                console.log("-- StatementSummaryController: " + err);
                            }
                        );
                    },

                    discardChanges: function() {
                        controllerData.statementCopy = null;
                        controllerData.apartmentsStatementsCopy = [];

                        controllerData.editMode = !controllerData.editMode;
                    },

                    toggleFeePaid: function(apt) {
                        if (controllerData.editMode) {
                            apt.feePaid = !apt.feePaid;
                        }
                    },

                    toggleSurchargePaid: function(apt) {
                        if (controllerData.editMode) {
                            apt.surchargePaid = !apt.surchargePaid;
                        }
                    }
                };

                function createDomainObject(from) {
                    var domainObj = JSON.parse(JSON.stringify(from));
                    domainObj = domainService.MonthlyStatement.build(domainObj);
                    return domainObj;
                }

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
                        if(newValue.toString() === oldValue.toString()){
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
        editMode: false,
        statement: null,
        apartmentsStatements: [],
        statementCopy: null,
        apartmentsStatementsCopy: []
    };

})();