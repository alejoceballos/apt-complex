(function() {

    var app = angular.module("app-domain", []);

    var ERROR = {
        NO_ID_FOUND: "No \"id\" found in object",
        NO_TYPE_FOUND: "No \"type\" found in object",
        NO_DESC_FOUND: "No \"description\" found in object",
        NO_VALUE_FOUND: "No \"value\" found in object",
        NO_WHEN_FOUND: "No \"when\" found in object",
        NO_NOTE_FOUND: "No \"note\" found in object",
        NO_NUMBER_FOUND: "No \"number\" found in object",
        NO_APARTMENT_FOUND: "No \"apartment\" found in object",
        NO_REF_DATE_FOUND: "No \"reference date\" found in object",
        NO_BALANCE_GROUPS_FOUND: "No \"balance groups\" found in object"
    };

    app.service("domainService",
        [
            function() {
                function build(Class, obj, asMap) {
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new Class(obj[idx]));
                            }

                        } else if (asMap) {
                            result = {};

                            for (var idx in obj) {
                                result[idx] = new Class(obj[idx]);
                            }

                        } else {
                            result = new Class(obj);
                        }

                    } else {
                        result = new Class();
                    }

                    return result;
                }

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // BILL ITEM
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var BillItem = function(obj) {
                    var DOMAIN_OBJ = "BillItem";

                    if (!obj) {
                        this.id = null;
                        this.type = "CUSTOM";
                        this.description = "Created by GUI";
                        this.value = 0;

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (obj.type === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_TYPE_FOUND;
                        this.type = obj.type;

                        if (obj.description === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_DESC_FOUND;
                        this.description = obj.description;

                        if (obj.value === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_VALUE_FOUND;
                        this.value = obj.value;
                    }
                };

                BillItem.build = function(obj) {
                    return build(BillItem, obj, false);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // PAYMENT
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var Payment = function(obj) {
                    var DOMAIN_OBJ = "Payment";

                    if (!obj) {
                        this.id = null;
                        this.value = 0;
                        this.type = "INCOME";
                        this.when = new Date();
                        this.note = "Created by GUI";

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (obj.value === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_VALUE_FOUND;
                        this.value = obj.value;

                        if (obj.type === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_TYPE_FOUND;
                        this.type = obj.type;

                        if (obj.when === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_WHEN_FOUND;
                        this.when = obj.when;

                        if (obj.note === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_NOTE_FOUND;
                        this.note = obj.note;
                    }
                };

                Payment.build = function(obj) {
                    return build(Payment, obj, false);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // BILL
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var Bill = function(obj) {
                    var DOMAIN_OBJ = "Bill";

                    if (!obj) {
                        this.id = null;
                        this.items = [];
                        this.payments = [];

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.items) {
                            this.items = [];
                        } else {
                            this.items = BillItem.build(obj.items);
                        }

                        if (!obj.payments) {
                            this.payments = [];
                        } else {
                            this.payments = Payment.build(obj.payments);
                        }
                    }

                    Object.defineProperty(this, "totalFee", {
                        get: function () {
                            var total = 0;

                            for (var itemIdx in this.items) {
                                total += this.items[itemIdx].value;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });

                    Object.defineProperty(this, "totalPayment", {
                        get: function () {
                            var total = 0;

                            for (var pymntIdx in this.payments) {
                                total += this.payments[pymntIdx].value;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });
                };

                Bill.build = function(obj) {
                    return build(Bill, obj, false);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // APARTMENT
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var Apartment = function(obj) {
                    var DOMAIN_OBJ = "Apartment";

                    if (!obj) {
                        this.id = null;
                        this.number = null;
                        this.residents = [];

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (obj.number === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_NUMBER_FOUND;
                        // If possible, convert to number so it can be ordered
                        if (!isNaN(parseInt(obj.number))) {
                            this.number = parseInt(obj.number);
                        } else {
                            this.number = obj.number;
                        }

                        // No residents are being considered right now
                        this.residents = [];
                    }
                };

                Apartment.build = function(obj) {
                    return build(Apartment, obj, false);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // APARTMENT BALANCE
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var ApartmentBalance = function(obj) {
                    var DOMAIN_OBJ = "ApartmentBalance";

                    if (!obj) {
                        this.id = null;
                        this.apartment = Apartment.build();
                        this.fees = [];
                        this.apartmentNumber = this.apartment.number;

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (obj.apartment === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_APARTMENT_FOUND;
                        this.apartment = obj.apartment;

                        if (obj.apartment) {
                            this.apartmentNumber = obj.apartment.apartmentNumber;
                        }

                        if (!obj.fees) {
                            this.fees = [];
                        } else {
                            this.fees = Bill.build(obj.fees);
                        }
                    }

                    this.hasFee = function () {
                        return this.fees.length > 0;
                    };

                    this.hasPayment= function () {
                        for (var feeIdx in this.fees) {
                            if (this.fees[feeIdx].payments.length > 0) {
                                return true;
                            }
                        }

                        return false;
                    };

                    this.getTotals = function() {
                        var totalFee = 0;
                        var totalPayment = 0;

                        for (var feeIdx in this.fees) {
                            totalFee += this.fees[feeIdx].totalFee;
                            totalPayment += this.fees[feeIdx].totalPayment;
                        }

                        return {
                            fee: totalFee,
                            payment: totalPayment
                        };
                    };

                    this.getPaymentStatus= function () {
                        var totals = this.getTotals();
                        var diff = totals.totalPayment - totals.totalFee;
                        return diff >= 0 ? "ok" : (this.totalPayment > 0 ? "partial" : "none");
                    };

                };

                ApartmentBalance.build = function(obj, asMap) {
                    return build(ApartmentBalance, obj, asMap);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // APARTMENT BALANCE GROUP
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var ApartmentBalanceGroup = function(obj) {
                    var DOMAIN_OBJ = "ApartmentBalanceGroup";

                    if (!obj) {
                        this.id = null;
                        this.apartmentsBalance = null;

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.apartmentsBalance) {
                            this.apartmentsBalance = { };

                        } else {
                            this.apartmentsBalance = ApartmentBalance.build(obj.apartmentsBalance, true);
                        }
                    }

                    this.getTotals = function () {
                        var totals = {
                            fee: 0,
                            payment: 0
                        };

                        for (var abIdx in this.apartmentsBalance) {
                            var abTotals = this.apartmentsBalance[abIdx].getTotals();

                            totals.fee += abTotals.fee;
                            totals.payment += abTotals.payment;
                        }

                        return totals;
                    };
                };

                ApartmentBalanceGroup.build = function(obj) {
                    return build(ApartmentBalanceGroup, obj, false);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // MONTHLY BALANCE
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var MonthlyBalance = function(obj) {
                    var DOMAIN_OBJ = "MonthlyBalance";

                    if (!obj) {
                        this.id = null;
                        this.referenceMonth = new Date();
                        this.balanceGroups = {
                            INCOMES: null
                        };

                    } else {
                        if (obj.id === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (obj.referenceMonth === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_REF_DATE_FOUND;
                        this.referenceMonth = obj.referenceMonth;

                        if (obj.balanceGroups === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_BALANCE_GROUPS_FOUND;
                        this.balanceGroups = obj.balanceGroups;

                        if (obj.balanceGroups.INCOMES === undefined) throw DOMAIN_OBJ + ": " + ERROR.NO_BALANCE_GROUPS_FOUND + " (INCOMES)";
                        this.balanceGroups.INCOMES = ApartmentBalanceGroup.build(obj.balanceGroups.INCOMES);

                        this.getIncomeTotals = function () {
                            return this.balanceGroups.INCOMES.getTotals();
                        };
                    }
                };

                MonthlyBalance.build = function(obj) {
                    return build(MonthlyBalance, obj, false);
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                this.BillItem = BillItem;
                this.Payment = Payment;
                this.Bill = Bill;
                this.Apartment = Apartment;
                this.ApartmentBalance = ApartmentBalance;
                this.ApartmentBalanceGroup = ApartmentBalanceGroup;
                this.MonthlyBalance = MonthlyBalance;
            }
        ]
    );

})();