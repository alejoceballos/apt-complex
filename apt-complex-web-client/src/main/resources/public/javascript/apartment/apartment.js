(function() {
    var app = angular.module('apt-complex-apartment',
        [
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'angularMoment'
        ]);


    app.service('remoteApiService', [
        '$http',
        function($http) {
            var url = "api/apartment/";

            this.getByReferenceMonth = function(year, month) {
                var options = {
                    method: 'GET',
                    url: url + "year/" + year + "/month/" + month
                };

                return $http(options).then(
                    function(response) {
                        return response.data;
                    }
                ).catch(
                    function(err) {
                        console.log(err);
                        throw err;
                    }
                );
            };

            this.generateBillsForMonth = function(year, month) {
                var options = {
                    method: 'POST',
                    url: url + "genbill/year/" + year + "/month/" + month
                };

                return $http(options).then(
                    function(response) {
                        return response.data;
                    }
                ).catch(
                    function(err) {
                        console.log(err);
                        throw err;
                    }
                );
            };
        }
    ]);

    app.service('billService', function() {
        this.build = function(bill) {
            bill.hasItems = function() {
                return !!this.items && this.items.length > 0;
            };

            bill.total = function() {
                var total = 0;

                if (this.hasItems()) {
                    for (var itemIdx in this.items) {
                        if (!isNaN(this.items[itemIdx].value)) {
                            total += parseFloat(this.items[itemIdx].value);
                        }
                    }
                }

                return total;
            };

            bill.hasPayment = function() {
                return !!this.payments && this.payments.length > 0;
            };

            bill.totalPayment = function() {
                var total = 0;

                if (this.hasPayment) {
                    for (var pmntIdx in this.payments) {
                        total += this.payments[pmntIdx].value;
                    }
                }

                return total;
            }
        };
    });

    app.service('apartmentService', [
        'billService',
        function(billService) {
            this.build = function(apartment) {
                if (!isNaN(parseInt(apartment.number))) {
                    apartment.number = parseInt(apartment.number);
                }

                function calculateHasFee() {
                    return !!apartment.fees && apartment.fees.length > 0;
                }

                apartment.hasFee = calculateHasFee();

                if (calculateHasFee()) {
                    for (var feeIdx in apartment.fees) {
                        billService.build(apartment.fees[feeIdx]);
                    }
                }

                function calculateTotalFee() {
                    var total = 0;

                    if (calculateHasFee()) {
                        for (var feeIdx in apartment.fees) {
                            total += apartment.fees[feeIdx].total();
                        }
                    }

                    return total;
                }

                apartment.totalFee = calculateTotalFee();

                function calculateTotalPayment() {
                    var total = 0;

                    if (calculateHasFee()) {
                        for (var feeIdx in apartment.fees) {
                            total += apartment.fees[feeIdx].totalPayment();
                        }
                    }

                    return total;
                }

                apartment.totalPayment = calculateTotalPayment();

                function calculatePaymentStatus() {
                    var tFee = calculateTotalFee();
                    var tPymnt = calculateTotalPayment();
                    var diff = tPymnt - tFee;

                    return diff >= 0 ? "ok" : (tPymnt > 0 ? "partial" : "none");
                }

                apartment.paymentStatus = calculatePaymentStatus();

                function calculateHasPayment() {
                    if (calculateHasFee()) {
                        for (var feeIdx in apartment.fees) {
                            if (apartment.fees[feeIdx].hasPayment()) {
                                return true;
                            }
                        }
                    }

                    return false;
                }

                apartment.hasPayment = calculateHasPayment();

                apartment.feesItemsCopy = {
                    list: [],
                    make: function () {
                        apartment.feesItemsCopy.list = [];

                        if (calculateHasFee()) {
                            for (var feeIdx in apartment.fees) {
                                if (apartment.fees[feeIdx].hasItems()) {
                                    for (var itemIdx in apartment.fees[feeIdx].items) {
                                        apartment.feesItemsCopy.list.push(
                                            {
                                                value: apartment.fees[feeIdx].items[itemIdx].value,
                                                originalItem: apartment.fees[feeIdx].items[itemIdx]
                                            }
                                        );
                                    }
                                }
                            }
                        }
                    },

                    apply: function () {
                        for (var copyIdx in apartment.feesItemsCopy.list) {
                            var copyItem = apartment.feesItemsCopy.list[copyIdx];
                            copyItem.originalItem.value = copyItem.value;
                        }

                        apartment.hasFee = calculateHasFee();
                        apartment.totalFee = calculateTotalFee();
                        apartment.totalPayment = calculateTotalPayment();
                        apartment.paymentStatus = calculatePaymentStatus();
                        apartment.hasPayment = calculateHasPayment();
                    },

                    discard: function () {
                        for (var copyIdx in apartment.feesItemsCopy.list) {
                            var copyItem = apartment.feesItemsCopy.list[copyIdx];
                            copyItem.value = copyItem.originalItem.value;
                        }
                    }
                };

                apartment.feesItemsCopy.make();
            };

            this.buildList = function(list) {
                var that = this;

                angular.forEach(list, function (apt) {
                    that.build(apt);
                });
            };
        }
    ]);

    app.controller('ApartmentController', [
        '$scope', 'remoteApiService', 'apartmentService',
        function($scope, remoteApiService, apartmentService) {
            console.log(">> ApartmentController");

            function updateView(aDate) {
                remoteApiService.getByReferenceMonth(
                        aDate.getFullYear(),
                        aDate.getMonth() + 1
                ).then(
                    function(result) {
                        apartmentService.buildList(result.data);
                        controllerData.apartmentList = result.data;
                    }
                ).catch(
                    function(err) {
                        console.log("-- ApartmentController: " + err);
                    }
                );
            }

            this.data = controllerData;

            $scope.$watch(
                function() {
                    return controllerData.referenceDate;
                },
                function(newValue, oldValue){
                    if(newValue === oldValue){
                        return;
                    }

                    updateView(newValue);
                }
            );

            this.generateBillsForMonth = function() {
                remoteApiService.generateBillsForMonth(
                        controllerData.referenceDate.getFullYear(),
                        controllerData.referenceDate.getMonth() + 1
                ).then(
                    function(result) {
                        apartmentService.buildList(result.data);
                        controllerData.apartmentList = result.data;
                    }
                ).catch(
                    function(err) {
                        console.log("-- ApartmentController: " + err);
                    }
                );
            };

            updateView(controllerData.referenceDate);
        }
    ]);

    var controllerData = {
        referenceDate: new Date(),
        apartmentList: undefined
    };

})();
