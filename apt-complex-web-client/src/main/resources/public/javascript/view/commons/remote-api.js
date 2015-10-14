(function() {
    var app = angular.module("app-remote-api",
        [
            "pascalprecht.translate",
            'angular-growl'
        ]
    );

    app.service("remoteApiService",
        [
            '$http',
            '$translate',
            'growl',
            function($http, $translate, growl) {
                var url = "api/";

                this.balance = {
                    summary: function(year, month) {
                        var options = {
                            method: 'GET',
                            url: url + "balance/summary/year/" + year + "/month/" + month
                        };

                        $translate("REMOTE_API_BALANCE_SUMMARY_RETRIEVING").then(function(msg) {
                            growl.info(msg + month + "/" + year);
                        });

                        return $http(options).then(
                            function(response) {
                                $translate("REMOTE_API_BALANCE_SUMMARY_RETRIEVED").then(function(msg) {
                                    growl.success(msg);
                                });
                                return response.data;
                            }
                        ).catch(
                            function(err) {
                                $translate("REMOTE_API_BALANCE_SUMMARY_ERROR_RETRIEVING").then(function(msg) {
                                    growl.error(msg + " (" + err.status + " - " + err.statusText + ")");
                                });
                                console.log(err);
                                throw err;
                            }
                        );
                    }
                };

                this.statement = {
                    summary: function(year, month) {
                        var options = {
                            method: 'GET',
                            url: url + "statement/summary/year/" + year + "/month/" + month
                        };

                        $translate("REMOTE_API_STATEMENT_SUMMARY_RETRIEVING").then(function(msg) {
                            growl.info(msg + month + "/" + year);
                        });

                        return $http(options).then(
                            function(response) {
                                $translate("REMOTE_API_STATEMENT_SUMMARY_RETRIEVED").then(function(msg) {
                                    growl.success(msg);
                                });
                                return response.data;
                            }
                        ).catch(
                            function(err) {
                                $translate("REMOTE_API_STATEMENT_SUMMARY_ERROR_RETRIEVING").then(function(msg) {
                                    growl.error(msg + " (" + err.status + " - " + err.statusText + ")");
                                });
                                console.log(err);
                                throw err;
                            }
                        );
                    },

                    save: function(statement) {
                        var options = {
                            method: 'PUT',
                            url: url + "statement/save",
                            data: statement
                        };

                        $translate("REMOTE_API_STATEMENT_SUMMARY_SAVING").then(function(msg) {
                            growl.info(msg);
                        });

                        return $http(options).then(
                            function(response) {
                                $translate("REMOTE_API_STATEMENT_SUMMARY_SAVED").then(function(msg) {
                                    growl.success(msg);
                                });
                            }
                        ).catch(
                            function(err) {
                                $translate("REMOTE_API_STATEMENT_SUMMARY_ERROR_SAVING").then(function(msg) {
                                    growl.error(msg + " (" + err.status + " - " + err.statusText + ")");
                                });
                                console.log(err);
                                throw err;
                            }
                        );
                    }
                };
            }
        ]
    );
})();