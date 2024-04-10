---MODIFYING DATA
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);


INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  (
    (
      SELECT 
        MAX(facid) + 1 
      FROM 
        cd.facilities
    ), 
    'Spa', 
    20, 
    30, 
    100000, 
    800
  );


UPDATE 
  cd.facilities 
SET 
  initialoutlay = 8000 
WHERE 
  facid = 1;


UPDATE 
  cd.facilities 
SET 
  guestcost = (
    SELECT 
      guestcost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ), 
  membercost = (
    SELECT 
      membercost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) 
WHERE 
  facid = 1;


DELETE FROM cd.bookings;


DELETE FROM 
  cd.members 
WHERE 
  memid = 37;


---BASICS
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  membercost > 0 
  AND membercost < monthlymaintenance * 0.02;


SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE ('%Tennis%');


SELECT 
  * 
from 
  cd.facilities 
WHERE 
  facid IN (1, 5)


SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate > '2012-09-01 00:00:00'


SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;

---JOINS
SELECT 
  starttime 
FROM 
  cd.bookings 
  JOIN cd.members ON cd.bookings.memid = cd.members.memid 
WHERE 
  firstname = 'David' 
  AND surname = 'Farrell';


SELECT 
  cd.bookings.starttime AS start, 
  cd.facilities.name 
FROM 
  cd.facilities 
  INNER JOIN cd.bookings ON cd.facilities.facid = cd.bookings.facid 
WHERE 
  cd.facilities.name LIKE('%Tennis Court%') 
  AND cd.bookings.starttime >= '2012-09-21 00:00:00' 
  AND cd.bookings.starttime <= '2012-09-22 00:00:00' 
ORDER BY 
  cd.bookings.starttime;


SELECT 
  mems.firstname AS memfname, 
  mems.surname AS memsname, 
  recs.firstname AS recfname, 
  recs.surname AS recsname 
FROM 
  cd.members mems 
  LEFT OUTER JOIN cd.members recs ON recs.memid = mems.recommendedby 
ORDER BY 
  memsname, 
  memfname


SELECT 
  DISTINCT recs.firstname AS firstname, 
  recs.surname AS surname 
FROM 
  cd.members mems 
  INNER JOIN cd.members recs ON recs.memid = mems.recommendedby 
ORDER BY 
  surname, 
  firstname;


SELECT 
  DISTINCT mems.firstname || ' ' || mems.surname AS member, 
  (
    SELECT 
      recs.firstname || ' ' || recs.surname 
    FROM 
      cd.members recs 
    WHERE 
      recs.memid = mems.recommendedby
  ) AS recommender 
FROM 
  cd.members mems 
ORDER BY 
  Member;


---AGGREGATION
SELECT 
  recommendedby, 
  count(*) 
FROM 
  cd.members 
WHERE 
  recommendedby IS NOT NULL 
GROUP BY 
  recommendedby 
ORDER BY 
  recommendedby;


SELECT 
  facid, 
  SUM(slots) as "Total Slots" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;


SELECT 
  facid, 
  SUM(slots) as "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012-09-01 00:00:00' 
  AND starttime < '2012-10-01 00:00:00' 
GROUP BY 
  facid 
ORDER BY 
  SUM(slots);


SELECT 
  facid, 
  EXTRACT(
    month 
    FROM 
      starttime
  ) AS month, 
  sum(slots) as "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  EXTRACT(
    year 
    FROM 
      starttime
  ) = 2012 
GROUP BY 
  facid, 
  month 
ORDER BY 
  facid, 
  month;


SELECT 
  COUNT(DISTINCT memid) 
FROM 
  cd.bookings;


SELECT 
  members.surname, 
  members.firstname, 
  members.memid, 
  MIN(bookings.starttime) AS starttime 
FROM 
  cd.bookings 
  INNER JOIN cd.members ON members.memid = bookings.memid 
WHERE 
  starttime >= '2012-09-01' 
GROUP BY 
  members.surname, 
  members.firstname, 
  members.memid 
ORDER BY 
  members.memid;


SELECT 
  (
    SELECT 
      COUNT(*) 
    FROM 
      cd.members
  ) as count, 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;


SELECT 
  row_number() OVER(
    ORDER BY 
      joindate
  ), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;


SELECT 
  facid, 
  total 
FROM 
  (
    SELECT 
      facid, 
      SUM(slots) AS total, 
      rank() OVER (
        ORDER BY 
          sum(slots) DESC
      ) rank 
    FROM 
      cd.bookings 
    GROUP BY 
      facid
  ) as ranked 
WHERE 
  rank = 1;


---STRINGS
SELECT 
  surname || ',' || firstname AS name 
FROM 
  cd.members;


SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone LIKE '(___) ___-____';

SELECT 
  SUBSTR(mems.surname, 1, 1) AS letter, 
  COUNT(*) AS count 
FROM 
  cd.members mems 
GROUP BY 
  letter 
ORDER BY 
  letter;