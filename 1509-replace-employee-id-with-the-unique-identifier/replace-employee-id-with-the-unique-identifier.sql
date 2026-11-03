# Write your MySQL query statement below
SELECT eu.unique_id, e.name
FROM employees e
LEFT JOIN employeeuni eu
on e.id=eu.id;