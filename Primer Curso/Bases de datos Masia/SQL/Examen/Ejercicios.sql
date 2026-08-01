-- 1
SELECT c.Name AS Customer, p.Name AS Province
FROM ex_Customer c
         JOIN ex_Province p ON c.Province_Code = p.Code
WHERE p.Name = 'Ontario';

-- 2
SELECT i.Description, pu.Purchase_Date
FROM ex_Purchase pu
         JOIN ex_Customer c ON pu.Client_DNI = c.DNI
         JOIN ex_Item i ON pu.Item_Code = i.Code
WHERE c.Name = 'Scooby Doo';

-- 3
SELECT COUNT(DISTINCT c.DNI) AS Total_Customers
FROM ex_Customer c
         JOIN ex_Province p ON c.Province_Code = p.Code
         JOIN ex_Purchase pu ON c.DNI = pu.Client_DNI
WHERE p.Name = 'Ontario';

-- 4
SELECT v.Name AS Vendor, s.Name AS Superior
FROM ex_Vendor v
LEFT JOIN ex_Vendor s ON v.Superior_Officer = s.Code;

-- 5
SELECT Name
FROM ex_Customer c
WHERE EXISTS (
    SELECT 1 FROM ex_Purchase pu WHERE pu.Client_DNI = c.DNI
);

-- 6
-- a
SELECT Name FROM ex_Province p
WHERE EXISTS (SELECT 1 FROM ex_Customer c WHERE c.Province_Code = p.Code);

-- b
SELECT Name FROM ex_Province
WHERE Code IN (SELECT Province_Code FROM ex_Customer);

-- c
SELECT DISTINCT p.Name
FROM ex_Province p
JOIN ex_Customer c ON p.Code = c.Province_Code;

-- 7
SELECT DISTINCT c.Name
FROM ex_Customer c
         JOIN ex_Purchase pu ON c.DNI = pu.Client_DNI
         JOIN ex_Item i ON pu.Item_Code = i.Code
WHERE i.Description LIKE '%Beer%'
AND pu.Purchase_Date BETWEEN TO_DATE('01/01/2024', 'DD/MM/YYYY') AND TO_DATE('31/12/2024', 'DD/MM/YYYY');

-- 8
SELECT Name FROM ex_Vendor
WHERE Percentage = (SELECT MAX(Percentage) FROM ex_Vendor);

-- 9
SELECT Name FROM ex_Customer
UNION
SELECT Name FROM ex_Vendor;

-- 10
SELECT v.Name, COUNT(c.DNI) AS Total_Clients
FROM ex_Vendor v
         JOIN ex_Customer c ON v.Code = c.Vendor_Code
GROUP BY v.Name
HAVING COUNT(c.DNI) > 2;

-- 11
UPDATE ex_Vendor
SET Percentage = 15
WHERE Code IN (
    SELECT Vendor_Code FROM ex_Customer
    GROUP BY Vendor_Code
    HAVING COUNT(*) > 3
);
-- 12
SELECT DNI, Name FROM ex_Customer
MINUS
SELECT DISTINCT c.DNI, c.Name
FROM ex_Customer c
JOIN ex_Purchase pu ON c.DNI = pu.Client_DNI;
