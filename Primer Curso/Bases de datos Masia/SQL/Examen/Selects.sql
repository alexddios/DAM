-- 1 List the name of all customers and the name of their province, but only for those customers
-- who live in the province of Ontario.
SELECT c.Name AS Customer, p.Name AS Province
FROM ex_Customer c
         JOIN ex_Province p ON c.Province_Code = p.Code
WHERE p.Name = 'Ontario';

-- 2 Display the products bought by customer 'Scooby Doo' (custumer’s name), the description of
-- the item and the purchase date
SELECT i.Description, pu.Purchase_Date
FROM ex_Purchase pu
         JOIN ex_Customer c ON pu.Client_DNI = c.DNI
         JOIN ex_Item i ON pu.Item_Code = i.Code
WHERE c.Name = 'Scooby Doo';

-- 3 How many customers live in the province of Ontario and have made a purchase.
SELECT COUNT(DISTINCT c.DNI) AS Total_Customers
FROM ex_Customer c
         JOIN ex_Province p ON c.Province_Code = p.Code
         JOIN ex_Purchase pu ON c.DNI = pu.Client_DNI
WHERE p.Name = 'Ontario';

-- 4 Show all vendors with their name and their superior (Null is shown in case of NO superior)
SELECT v.Name AS Vendor, s.Name AS Superior
FROM ex_Vendor v
LEFT JOIN ex_Vendor s ON v.Superior_Officer = s.Code;

-- 5 Display all clients’ names who did one buy at least (Use EXISTS to solve the problem)
SELECT Name
FROM ex_Customer c
WHERE EXISTS (
    SELECT 1 FROM ex_Purchase pu WHERE pu.Client_DNI = c.DNI
);

-- 6 Show provinces with one registered client at least
-- a Use EXISTS to solve the problem
SELECT Name FROM ex_Province p
WHERE EXISTS (SELECT 1 FROM ex_Customer c WHERE c.Province_Code = p.Code);

-- b Use IN to solve the problem
SELECT Name FROM ex_Province
WHERE Code IN (SELECT Province_Code FROM ex_Customer);

-- c Use a JOIN to solve the problem
SELECT DISTINCT p.Name
FROM ex_Province p
JOIN ex_Customer c ON p.Code = c.Province_Code;

-- 7 Show all clients who have bought a product with the description 'Beer' and the purchase date is
-- between 01/01/2024 and 31/12/2024.
SELECT DISTINCT c.Name
FROM ex_Customer c
         JOIN ex_Purchase pu ON c.DNI = pu.Client_DNI
         JOIN ex_Item i ON pu.Item_Code = i.Code
WHERE i.Description LIKE '%Beer%'
AND pu.Purchase_Date BETWEEN TO_DATE('01/01/2024', 'DD/MM/YYYY') AND TO_DATE('31/12/2024', 'DD/MM/YYYY');

-- 8 Show the vendor’s name with the gratest percentage
SELECT Name FROM ex_Vendor
WHERE Percentage = (SELECT MAX(Percentage) FROM ex_Vendor);

-- 9 Show the name of all customers and all vendors in a single column.
SELECT Name FROM ex_Customer
UNION
SELECT Name FROM ex_Vendor;

-- 10 List the name of the vendor and the amount of client who has each vendor, provided that the
-- vendor has more than 2 clients assigned.
SELECT v.Name, COUNT(c.DNI) AS Total_Clients
FROM ex_Vendor v
         JOIN ex_Customer c ON v.Code = c.Vendor_Code
GROUP BY v.Name
HAVING COUNT(c.DNI) > 2;

-- 11 Modify the percentage of all vendors who has more than 3 clients. Set this percentage to 15%
UPDATE ex_Vendor
SET Percentage = 15
WHERE Code IN (
    SELECT Vendor_Code FROM ex_Customer
    GROUP BY Vendor_Code
    HAVING COUNT(*) > 3
);
-- 12 Clients (show DNI and Customer Name) who have no related information in the Purchase table
-- (Use MINUS to solve the problem)
SELECT DNI, Name FROM ex_Customer
MINUS
SELECT DISTINCT c.DNI, c.Name
FROM ex_Customer c
JOIN ex_Purchase pu ON c.DNI = pu.Client_DNI;