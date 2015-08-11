(function() {
    var app = angular.module("app-summary", []);

    app.controller('SummaryController',
        [
            "$scope",
            "$rootScope",
            "remoteApiService",
            function($scope, $rootScope, remoteApiService) {
                console.log(">> SummaryController");
                var that = this;

                that.data = controllerData;

                function updateView(aDate) {
                    remoteApiService.balance.summary(
                        aDate.getFullYear(),
                        aDate.getMonth() + 1
                    ).then(
                        function(result) {
                            controllerData.balance = result.data;
                            that.data.balance = controllerData.balance;
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
        balance: null
    };

})();