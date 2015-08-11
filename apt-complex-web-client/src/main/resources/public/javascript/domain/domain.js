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
        NO_APARTMENT_FOUND: "No \"apartment\" found in object"
    };

    app.service("domainService",
        [
            function() {
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
                        if (!obj.id) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.type) throw DOMAIN_OBJ + ": " + ERROR.NO_TYPE_FOUND;
                        this.type = obj.type;

                        if (!obj.description) throw DOMAIN_OBJ + ": " + ERROR.NO_DESC_FOUND;
                        this.description = obj.description;

                        if (!obj.value) throw DOMAIN_OBJ + ": " + ERROR.NO_VALUE_FOUND;
                        this.value = obj.value;
                    }
                };

                BillItem.build = function(obj) {
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new BillItem(obj[idx]));
                            }

                        } else {
                            result = new BillItem(obj);
                        }

                    } else {
                        result = new BillItem();
                    }

                    return result;
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
                        if (!obj.id) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.type) throw DOMAIN_OBJ + ": " + ERROR.NO_VALUE_FOUND;
                        this.value = obj.value;

                        if (!obj.type) throw DOMAIN_OBJ + ": " + ERROR.NO_TYPE_FOUND;
                        this.type = obj.type;

                        if (!obj.type) throw DOMAIN_OBJ + ": " + ERROR.NO_WHEN_FOUND;
                        this.when = obj.when;

                        if (!obj.type) throw DOMAIN_OBJ + ": " + ERROR.NO_NOTE_FOUND;
                        this.note = obj.note;
                    }
                };

                Payment.build = function(obj) {
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new Payment(obj[idx]));
                            }

                        } else {
                            result = new Payment(obj);
                        }

                    } else {
                        result = new Payment();
                    }

                    return result;
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
                        if (!obj.id) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
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
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new Bill(obj[idx]));
                            }

                        } else {
                            result = new Bill(obj);
                        }

                    } else {
                        result = new Bill();
                    }

                    return result;
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
                        if (!obj.id) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.number) throw DOMAIN_OBJ + ": " + ERROR.NO_NUMBER_FOUND;
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
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new Apartment(obj[idx]));
                            }

                        } else {
                            result = new Apartment(obj);
                        }

                    } else {
                        result = new Apartment();
                    }

                    return result;
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // APARTMENT BALANCE
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var ApartmentBalance = function(obj) {
                    var DOMAIN_OBJ = "ApartmentBalance";

                    if (!obj) {
                        this.id = null;
                        this.apartment = new Apartment();
                        this.fees = [];
                        this.apartmentNumber = this.apartment.number;

                    } else {
                        if (!obj.id) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.apartment) throw DOMAIN_OBJ + ": " + ERROR.NO_APARTMENT_FOUND;
                        this.apartment = obj.apartment;
                        this.apartmentNumber = obj.apartment.apartmentNumber;

                        if (!obj.fees) {
                            this.fees = [];
                        } else {
                            this.fees = Bill.build(obj.fees);
                        }
                    }

                    Object.defineProperty(this, "hasFee", {
                        get: function () {
                            return this.fees.length > 0;
                        },
                        enumerable: true,
                        configurable: false
                    });

                    Object.defineProperty(this, "totalFee", {
                        get: function () {
                            var total = 0;

                            for (var feeIdx in this.fees) {
                                total += this.fees[feeIdx].totalFee;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });

                    Object.defineProperty(this, "hasPayment", {
                        get: function () {
                            for (var feeIdx in this.fees) {
                                if (this.fees[feeIdx].payments.length > 0) {
                                    return true;
                                }

                                return false;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });

                    Object.defineProperty(this, "totalPayment", {
                        get: function () {
                            var total = 0;

                            for (var feeIdx in this.fees) {
                                total += this.fees[feeIdx].totalPayment;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });

                    Object.defineProperty(this, "paymentStatus", {
                        get: function () {
                            var diff = this.totalPayment - this.totalFee;
                            return diff >= 0 ? "ok" : (this.totalPayment > 0 ? "partial" : "none");
                        },
                        enumerable: true,
                        configurable: false
                    });

                };

                ApartmentBalance.build = function(obj) {
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new ApartmentBalance(obj[idx]));
                            }

                        } else {
                            result = new ApartmentBalance(obj);
                        }

                    } else {
                        result = new ApartmentBalance();
                    }

                    return result;
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // APARTMENT BALANCE GROUP
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                var ApartmentBalanceGroup = function(obj) {
                    var DOMAIN_OBJ = "ApartmentBalanceGroup";

                    if (!obj) {
                        this.id = null;
                        this.apartmentsBalance = [];

                    } else {
                        if (!obj.id) throw DOMAIN_OBJ + ": " + ERROR.NO_ID_FOUND;
                        this.id = obj.id;

                        if (!obj.apartmentsBalance) {
                            this.apartmentsBalance = [];
                        } else {
                            this.apartmentsBalance = ApartmentBalance.build(obj.apartmentsBalance);
                        }
                    }

                    Object.defineProperty(this, "totalFee", {
                        get: function () {
                            var total = 0;

                            for (var abIdx in this.apartmentsBalance) {
                                total += this.apartmentsBalance[abIdx].totalFee;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });

                    Object.defineProperty(this, "totalPayment", {
                        get: function () {
                            var total = 0;

                            for (var abIdx in this.apartmentsBalance) {
                                total += this.apartmentsBalance[abIdx].totalPayment;
                            }

                            return total;
                        },
                        enumerable: true,
                        configurable: false
                    });
                };

                ApartmentBalanceGroup.build = function(obj) {
                    var result;

                    if (obj) {
                        if (obj instanceof Array) {
                            result = new Array();

                            for (var idx in obj) {
                                result.push(new ApartmentBalanceGroup(obj[idx]));
                            }

                        } else {
                            result = new ApartmentBalanceGroup(obj);
                        }

                    } else {
                        result = new ApartmentBalanceGroup();
                    }

                    return result;
                };

                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
                // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

                this.BillItem = BillItem;
                this.Payment = Payment;
                this.Bill = Bill;
                this.Apartment = Apartment;
                this.ApartmentBalance = ApartmentBalance;
                this.ApartmentBalanceGroup = ApartmentBalanceGroup;
            }
        ]
    );

})();