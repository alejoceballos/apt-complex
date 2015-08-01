(function() {
    var app = angular.module('apt-complex-apartment',
        [
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'angularMoment',
            'angular-growl'
        ]);

    app.service('remoteApiService', [
        '$http',
        'growl',
        function($http, growl) {
            var url = "api/apartment/";

            this.getByReferenceMonth = function(year, month) {
                var options = {
                    method: 'GET',
                    url: url + "year/" + year + "/month/" + month
                };

                growl.info("Retrieving fees");

                return $http(options).then(
                    function(response) {
                        growl.success("Fees retrieved");
                        return response.data;
                    }
                ).catch(
                    function(err) {
                        growl.error("Error retrieving fees");
                        console.log(err);
                        throw err;
                    }
                );
            };

            this.generateFeesForMonth = function(year, month) {
                var options = {
                    method: 'POST',
                    url: url + "generate-bill/year/" + year + "/month/" + month
                };

                growl.info("Generating fees");

                return $http(options).then(
                    function(response) {
                        growl.success("Fees generated");
                        return response.data;
                    }
                ).catch(
                    function(err) {
                        growl.error("Error generating fees");
                        console.log(err);
                        throw err;
                    }
                );
            };

            this.updateFeeForMonth = function(id, year, month, newItems, removedItems, updatedItems) {
                var options = {
                    method: 'POST',
                    url: url + "update-bill/id/" + id + "/year/" + year + "/month/" + month,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: {
                        newItems: newItems,
                        removedItems: removedItems,
                        updatedItems: updatedItems
                    }
                };

                growl.info("Updating fee");

                return $http(options).then(
                    function(response) {
                        growl.success("Fee updated");
                        return response.data;
                    }
                ).catch(
                    function(err) {
                        growl.error("Error updating fee");
                        console.log(err);
                        throw err;
                    }
                );
            };
        }
    ]);

    app.value('BillItemStatus', {
        NEW: "new",
        COPIED: "copied"
    });

    app.factory('BillItemFactory', function() {
        var BillItemFactory = function(billItem) {
            if (!billItem) {
                var _billItem = {
                    id: null,
                    type: "CUSTOM",
                    description: "Created by GUI",
                    value: 0
                };

            } else {
                var _billItem = {
                    id: billItem.id,
                    type: billItem.type,
                    description: billItem.description,
                    value: billItem.value
                };
            }

            this.withValue = function(value) {
                _billItem.value = value;
                return this;
            };

            this.create = function() {
                return _billItem;
            };
        };

        return BillItemFactory;
    });

    app.service('billService', function() {
        this.build = function(bill) {
            if (!(bill.referenceDate instanceof Date)) {
                bill.referenceDate = new Date(bill.referenceDate);
            }

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
        'BillItemStatus',
        'billService',
        'BillItemFactory',
        'remoteApiService',
        function(BillItemStatus, billService, BillItemFactory, remoteApiService) {
            var that = this;

            this.build = function(apartment) {
                // If possible, convert to number so it can be ordered
                if (!isNaN(parseInt(apartment.number))) {
                    apartment.number = parseInt(apartment.number);
                }

                // Summary function
                function calculateHasFee() {
                    return !!apartment.fees && apartment.fees.length > 0;
                }

                // Summary function
                if (calculateHasFee()) {
                    for (var feeIdx in apartment.fees) {
                        billService.build(apartment.fees[feeIdx]);
                    }
                }

                // Summary function
                function calculateTotalFee() {
                    var total = 0;

                    if (calculateHasFee()) {
                        for (var feeIdx in apartment.fees) {
                            total += apartment.fees[feeIdx].total();
                        }
                    }

                    return total;
                }

                // Summary function
                function calculateTotalPayment() {
                    var total = 0;

                    if (calculateHasFee()) {
                        for (var feeIdx in apartment.fees) {
                            total += apartment.fees[feeIdx].totalPayment();
                        }
                    }

                    return total;
                }

                // Summary function
                function calculatePaymentStatus() {
                    var tFee = calculateTotalFee();
                    var tPymnt = calculateTotalPayment();
                    var diff = tPymnt - tFee;

                    return diff >= 0 ? "ok" : (tPymnt > 0 ? "partial" : "none");
                }

                // Summary function
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

                function summarize() {
                    apartment.hasFee = calculateHasFee();
                    apartment.totalFee = calculateTotalFee();
                    apartment.totalPayment = calculateTotalPayment();
                    apartment.paymentStatus = calculatePaymentStatus();
                    apartment.hasPayment = calculateHasPayment();
                }

                apartment.feesItems = {
                    list: [],
                    removed: [],

                    make: function () {
                        apartment.feesItems.list = [];
                        apartment.feesItems.removed = [];

                        if (calculateHasFee()) {
                            for (var feeIdx in apartment.fees) {
                                var fee = apartment.fees[feeIdx];

                                if (fee.hasItems()) {
                                    for (var itemIdx in fee.items) {
                                        var feeItem = fee.items[itemIdx];
                                        var copy = new BillItemFactory(feeItem).create();
                                        copy.status = BillItemStatus.COPIED;
                                        apartment.feesItems.list.push(copy);
                                    }
                                }
                            }
                        }
                    },

                    add: function() {
                        var feeItem = new BillItemFactory().create();
                        feeItem.status = BillItemStatus.NEW;
                        apartment.feesItems.list.push(feeItem);
                    },

                    remove: function(feeItem) {
                        var feeItemIdx = apartment.feesItems.list.indexOf(feeItem);

                        if (feeItemIdx > -1) {
                            apartment.feesItems.removed.push(feeItem.id);
                            apartment.feesItems.list.splice(feeItemIdx, 1);
                        }
                    },

                    apply: function () {
                        var newItems = [];
                        var updatedItems = [];
                        var removedItems = apartment.feesItems.removed;

                        for (var copyIdx in apartment.feesItems.list) {
                            var copy = apartment.feesItems.list[copyIdx];

                            if (copy.status === BillItemStatus.NEW) {
                                newItems.push(new BillItemFactory(copy).create());

                            } else {
                                updatedItems.push(new BillItemFactory(copy).create());
                            }
                        }

                        // get THIS apartment fro THIS year and month
                        remoteApiService.updateFeeForMonth(
                            apartment.id,
                            apartment.fees[0].referenceDate.getFullYear(),
                            apartment.fees[0].referenceDate.getMonth() + 1,
                            newItems,
                            removedItems,
                            updatedItems
                        ).then(
                            function(result) {
                                apartment.id = result.data.id;
                                apartment.number = result.data.number;
                                apartment.residents = result.data.residents;
                                apartment.fees = result.data.fees;

                                that.build(apartment);
                                summarize();
                                apartment.feesItems.make();
                            }
                        ).catch(
                            function(err) {
                                console.log("-- ApartmentController: " + err);
                            }
                        );
                    },

                    discard: function () {
                        apartment.feesItems.make();
                    }

                };

                summarize();
                apartment.feesItems.make();
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

            this.generateFeesForMonth = function() {
                remoteApiService.generateFeesForMonth(
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
