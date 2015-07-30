SET SQL_SAFE_UPDATES = 0;

-- ----------------------------------------------------------------------------
-- TABLE - APARTMENT
-- ----------------------------------------------------------------------------

INSERT INTO `apartment`
  (`id`, `number`)
VALUES
  (1, '101'), (2, '102'), (3, '103'),
  (4, '201'), (5, '202'), (6, '203'),
  (7, '301'), (8, '302'), (9, '303'),
  (10, '401'), (11, '402'), (12, '403'),
  (13, '501'), (14, '502'), (15, '503'),
  (16, '601'), (17, '602'), (18, '603'),
  (19, '701'), (20, '702'), (21, '703'),
  (22, '801'), (23, '802'), (24, '803'),
  (25, '901'), (26, '902'), (27, '903'),
  (28, '1001'), (29, '1002'), (30, '1003'),
  (31, '1101'), (32, '1102'), (33, '1103'),
  (34, '1201'), (35, '1202'), (36, '1203');

-- ----------------------------------------------------------------------------
-- TABLE - PERSON
-- ----------------------------------------------------------------------------

INSERT INTO `person`
  (`id`, `first_name`, `middle_name`, `last_name`)
VALUES
  (1, 'John', '1', 'Doe'), (2, 'John', '2', 'Doe'), (3, 'John', '3', 'Doe'),
  (4, 'John', '4', 'Doe'), (5, 'John', '5', 'Doe'), (6, 'John', '6', 'Doe'),
  (7, 'John', '7', 'Doe'), (8, 'John', '8', 'Doe'), (9, 'John', '9', 'Doe'),
  (10, 'John', '10', 'Doe'), (11, 'John', '11', 'Doe'), (12, 'John', '12', 'Doe'),
  (13, 'John', '13', 'Doe'), (14, 'John', '14', 'Doe'), (15, 'John', '15', 'Doe'),
  (16, 'John', '16', 'Doe'), (17, 'John', '17', 'Doe'), (18, 'John', '18', 'Doe'),
  (19, 'John', '19', 'Doe'), (20, 'John', '20', 'Doe'), (21, 'John', '21', 'Doe'),
  (22, 'John', '22', 'Doe'), (23, 'Alejo', NULL, 'Ceballos'), (24, 'John', '24', 'Doe'),
  (25, 'John', '25', 'Doe'), (26, 'John', '26', 'Doe'), (27, 'John', '27', 'Doe'),
  (28, 'John', '28', 'Doe'), (29, 'John', '29', 'Doe'), (30, 'John', '30', 'Doe'),
  (31, 'John', '31', 'Doe'), (32, 'John', '32', 'Doe'), (33, 'John', '33', 'Doe'),
  (34, 'John', '34', 'Doe'), (35, 'John', '35', 'Doe'), (36, 'John', '36', 'Doe')
;

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - RESIDENCE, THE APARTMENT RESIDENT (PERSON)
-- ----------------------------------------------------------------------------

INSERT INTO `residence`
  (`apartment_id`, `person_id`)
VALUES
  (1, 1), (2, 2), (3, 3),
  (4, 4), (5, 5), (6, 6),
  (7, 7), (8, 8), (9, 9),
  (10, 10), (11, 11), (12, 12),
  (13, 13), (14, 14), (15, 15),
  (16, 16), (17, 17), (18, 18),
  (19, 19), (20, 20), (21, 21),
  (22, 22), (23, 23), (24, 24),
  (25, 25), (26, 26), (27, 27),
  (28, 28), (29, 29), (30, 30),
  (31, 31), (32, 32), (33, 33),
  (34, 34), (35, 35), (36, 36)
;

-- ----------------------------------------------------------------------------
-- TABLE - BILL (CAN BE AN APARTMENT BILL OR EVEN A CONDOMINIUM CLEANING STUFF BILL)
-- ----------------------------------------------------------------------------

/*
INSERT INTO  `bill`
  (`id`, `reference_date`)
VALUES
  (1, '2015-06-01'), (2, '2015-06-01'), (3, '2015-06-01'),
  (4, '2015-06-01'), (5, '2015-06-01'), (6, '2015-06-01'),
  (7, '2015-06-01'), (8, '2015-06-01'), (9, '2015-06-01'),
  (10, '2015-06-01'), (11, '2015-06-01'), (12, '2015-06-01'),
  (13, '2015-06-01'), (14, '2015-06-01'), (15, '2015-06-01'),
  (16, '2015-06-01'), (17, '2015-06-01'), (18, '2015-06-01'),
  (19, '2015-06-01'), (20, '2015-06-01'), (21, '2015-06-01'),
  (22, '2015-06-01'), (23, '2015-06-01'), (24, '2015-06-01'),
  (25, '2015-06-01'), (26, '2015-06-01'), (27, '2015-06-01'),
  (28, '2015-06-01'), (29, '2015-06-01'), (30, '2015-06-01'),
  (31, '2015-06-01'), (32, '2015-06-01'), (33, '2015-06-01'),
  (34, '2015-06-01'), (35, '2015-06-01'), (36, '2015-06-01')
;
*/

-- ----------------------------------------------------------------------------
-- TABLE - BILL ITEMS
-- ----------------------------------------------------------------------------

/*
INSERT INTO `bill_item`
  (`id`, `type`, `description`, `value`, `bill_id`)
VALUES
  (1, 'CONDOMINIUM_FEE',       NULL, 800.00, 1), -- 101
  (2, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 1),
  (3, 'CONDOMINIUM_FEE',       NULL, 800.00, 2), -- 102
  (4, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 2),
  (5, 'CONDOMINIUM_FEE',       NULL, 800.00, 3), -- 103
  (6, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 3),
  (7, 'CONDOMINIUM_FEE',       NULL, 800.00, 4), -- 201
  (8, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 4),
  (9, 'CONDOMINIUM_FEE',       NULL, 800.00, 5), -- 202
  (10, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 5),
  (11, 'CONDOMINIUM_FEE',       NULL, 800.00, 6), -- 203
  (12, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 6),
  (13, 'CONDOMINIUM_FEE',       'THE LIQUIDATOR IS EXEMPT FROM PAYMENT', 0.00, 7),  -- 301
  (14, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 7),
  (15, 'CONDOMINIUM_FEE',       NULL, 800.00, 8), -- 302
  (16, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 8),
  (17, 'CONDOMINIUM_FEE',       NULL, 800.00, 9), -- 303
  (18, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 9),
  (19, 'CONDOMINIUM_FEE',       NULL, 800.00, 10), -- 401
  (20, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 10),
  (21, 'CONDOMINIUM_FEE',       NULL, 800.00, 11), -- 402
  (22, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 11),
  (23, 'CONDOMINIUM_FEE',       NULL, 800.00, 12), -- 403
  (24, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 12),
  (25, 'CONDOMINIUM_FEE',       NULL, 800.00, 13), -- 501
  (26, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 13),
  (27, 'CONDOMINIUM_FEE',       NULL, 800.00, 14), -- 502
  (28, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 14),
  (29, 'CONDOMINIUM_FEE',       NULL, 800.00, 15), -- 503
  (30, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 15),
  (31, 'CONDOMINIUM_FEE',       NULL, 800.00, 16), -- 601
  (32, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 16),
  (33, 'CONDOMINIUM_FEE',       NULL, 800.00, 17), -- 602
  (34, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 17),
  (35, 'CONDOMINIUM_FEE',       NULL, 800.00, 18), -- 603
  (36, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 18),
  (37, 'CONDOMINIUM_FEE',       NULL, 800.00, 19), -- 201
  (38, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 19),
  (39, 'CONDOMINIUM_FEE',       NULL, 800.00, 10), -- 202
  (40, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 10),
  (41, 'CONDOMINIUM_FEE',       NULL, 800.00, 21), -- 203
  (42, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 21),
  (43, 'CONDOMINIUM_FEE',       NULL, 800.00, 22),
  (44, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 22),
  (45, 'CONDOMINIUM_FEE',       NULL, 800.00, 23),
  (46, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 23),
  (47, 'CONDOMINIUM_FEE',       NULL, 800.00, 24),
  (48, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 24),
  (49, 'CONDOMINIUM_FEE',       NULL, 800.00, 25),
  (50, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 25),
  (51, 'CONDOMINIUM_FEE',       NULL, 800.00, 26),
  (52, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 26),
  (53, 'CONDOMINIUM_FEE',       NULL, 800.00, 27),
  (54, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 27),
  (55, 'CONDOMINIUM_FEE',       NULL, 800.00, 28),
  (56, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 28),
  (57, 'CONDOMINIUM_FEE',       NULL, 800.00, 29),
  (58, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 29),
  (59, 'CONDOMINIUM_FEE',       NULL, 800.00, 30),
  (60, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 30),
  (61, 'CONDOMINIUM_FEE',       NULL, 800.00, 31),
  (62, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 31),
  (63, 'CONDOMINIUM_FEE',       NULL, 800.00, 32),
  (64, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 32),
  (65, 'CONDOMINIUM_FEE',       NULL, 800.00, 33),
  (66, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 33),
  (67, 'CONDOMINIUM_FEE',       NULL, 800.00, 34),
  (68, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 34),
  (69, 'CONDOMINIUM_FEE',       NULL, 800.00, 35),
  (70, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 35),
  (71, 'CONDOMINIUM_FEE',       NULL, 800.00, 36),
  (72, 'CONDOMINIUM_SURCHARGE', NULL, 250.00, 36)
;
*/

-- ----------------------------------------------------------------------------
-- TABLE - PAYMENT
-- ----------------------------------------------------------------------------

/*
INSERT INTO `payment`
  (`id`, `value`, `type`, `when`)
VALUES
  (1, 1050.00, 'INCOME', '2015-06-10'),
  (2, 1050.00, 'INCOME', '2015-06-10'),
  (3, 1050.00, 'INCOME', '2015-06-10'),
  (4, 1050.00, 'INCOME', '2015-06-10'),
  (5, 1050.00, 'INCOME', '2015-06-10'),
  (6, 1050.00, 'INCOME', '2015-06-10'),
  (7, 250.00, 'INCOME', '2015-06-10'),
  (8, 1050.00, 'INCOME', '2015-06-10'),
  (9, 1050.00, 'INCOME', '2015-06-10'),
  (10, 1050.00, 'INCOME', '2015-06-10'),
  (11, 1050.00, 'INCOME', '2015-06-10'),
  (12, 1050.00, 'INCOME', '2015-06-10'),
  (13, 1050.00, 'INCOME', '2015-06-10'),
  (14, 1050.00, 'INCOME', '2015-06-10'),
  (15, 1050.00, 'INCOME', '2015-06-10'),
  (16, 1050.00, 'INCOME', '2015-06-10'),
  (17, 1050.00, 'INCOME', '2015-06-10'),
  (18, 1050.00, 'INCOME', '2015-06-10'),
  (19, 1050.00, 'INCOME', '2015-06-10'),
  (20, 1050.00, 'INCOME', '2015-06-10'),
  (21, 1050.00, 'INCOME', '2015-06-10'),
  (22, 1050.00, 'INCOME', '2015-06-10'),
  (23, 1050.00, 'INCOME', '2015-06-10'),
  (24, 1050.00, 'INCOME', '2015-06-10'),
  (25, 1050.00, 'INCOME', '2015-06-10'),
  (26, 1050.00, 'INCOME', '2015-06-10'),
  (27, 1050.00, 'INCOME', '2015-06-10'),
  (28, 1050.00, 'INCOME', '2015-06-10'),
  (29, 1050.00, 'INCOME', '2015-06-10'),
  (30, 1050.00, 'INCOME', '2015-06-10'),
  (31, 1050.00, 'INCOME', '2015-06-10'),
  (32, 1050.00, 'INCOME', '2015-06-10'),
  (33, 1050.00, 'INCOME', '2015-06-10'),
  (34, 1050.00, 'INCOME', '2015-06-10'),
  (35, 1050.00, 'INCOME', '2015-06-10'),
  (36, 1050.00, 'INCOME', '2015-06-10')
;
*/

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - BILL PAYMENT (ALL PAYMENTS TO PAY OFF THIS DEBT)
-- ----------------------------------------------------------------------------

/*
INSERT INTO `bill_payment`
  (`bill_id`, `payment_id`)
VALUES
  (1, 1), (2, 2), (3, 3),
  (4, 4), (5, 5), (6, 6),
  (7, 7), (8, 8), (9, 9),
  (10, 10), (11, 11), (12, 12),
  (13, 13), (14, 14), (15, 15),
  (16, 16), (17, 17), (18, 18),
  (19, 19), (20, 20), (21, 21),
  (22, 22), (23, 23), (24, 24),
  (25, 25), (26, 26), (27, 27),
  (28, 28), (29, 29), (30, 30),
  (31, 31), (32, 32), (33, 33),
  (34, 34), (35, 35), (36, 36)
;
*/

-- ----------------------------------------------------------------------------
-- BALANCE DIVERGENCE - ALL ACCOUNTS NOT REGISTERED FOR A REFERENCED DATE (FOR
-- ANY REASON WHATSOEVER), BUT ARE "EXPLAINABLE"
-- ----------------------------------------------------------------------------

/*
INSERT INTO `balance_divergence`
  (`id`, `value`, `referenceDate`, `type`, `description`)
VALUES
  (1, -1050.00, '2015-06-01', 'CONDOMINIUM_DISCOUNT', 'I presume 21 apartments paid before day 10, enjoying the R$ 50.00 discount. The balance document from the administration company is not clear about it')
;
*/

--  ----------------------------------------------------------------------------
-- ----------------------------------------------------------------------------
-- END OF FILE, COMMITTING
-- ----------------------------------------------------------------------------
-- ----------------------------------------------------------------------------

COMMIT;
