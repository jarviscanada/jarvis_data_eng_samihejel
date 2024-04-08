# Introduction
This project is a learning exercise aiming to strengthen my SQL abilities. In this project I tried to complete various SQL query tasks given to me starting with the basics followed by a focus on joins, window functions, nested queries, and aggregations. I used PostgreSQL for the database instance and pgAdmin as my IDE of choice.

# SQL Queries

###### Table Setup (DDL)
Create the members table:
```sql
CREATE TABLE cd.members
    (
       memid integer NOT NULL, 
       surname character varying(200) NOT NULL, 
       firstname character varying(200) NOT NULL, 
       address character varying(300) NOT NULL, 
       zipcode integer NOT NULL, 
       telephone character varying(20) NOT NULL, 
       recommendedby integer,
       joindate timestamp NOT NULL,
       CONSTRAINT members_pk PRIMARY KEY (memid),
       CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby)
            REFERENCES cd.members(memid) ON DELETE SET NULL
    );
```
Create the booking table:
```sql
CREATE TABLE cd.bookings
    (
       bookid integer NOT NULL, 
       facid integer NOT NULL, 
       memid integer NOT NULL, 
       starttime timestamp NOT NULL,
       slots integer NOT NULL,
       CONSTRAINT bookings_pk PRIMARY KEY (bookid),
       CONSTRAINT fk_bookings_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
       CONSTRAINT fk_bookings_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
    );
```

Create the facilities table:
```sql
CREATE TABLE cd.facilities
(
    facid integer NOT NULL, 
    name character varying(100) NOT NULL, 
    membercost numeric NOT NULL, 
    guestcost numeric NOT NULL, 
    initialoutlay numeric NOT NULL, 
    monthlymaintenance numeric NOT NULL, 
    CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
```

###### Show all members 

```sql
SELECT 
  * 
FROM 
  cd.members

```

## Practicing Queries
### Modifying Data
###### Question 1: Insert some data into a table

```sql
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2: Insert calculated data into a table
```sql
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

```

###### Question 3: Update some existing data
```sql
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 8000 
WHERE 
  facid = 1;

```

###### Question 4: Update a row based on the contents of another row
```sql
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

```

###### Question 5: Delete all bookings
```sql
DELETE FROM cd.bookings;
```

###### Question 6:  Delete a member from the cd.members table 
```sql
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;
```

### Basics
###### Question 1: Control which rows are retrieved - part 2
```sql
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
```

###### Question 2: Basic string searches
```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE ('%Tennis%');
```

###### Question 3: Matching against multiple possible values
```sql
SELECT 
  * 
from 
  cd.facilities 
WHERE 
  facid IN (1, 5)
```
###### Question 4: Working with dates
```sql
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate > '2012-09-01 00:00:00'
```

###### Question 5: Combining results from multiple queries
```sql
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
```

### Join
###### Question 1: Retrieve the start times of members' bookings
```sql
SELECT 
  starttime 
FROM 
  cd.bookings 
  JOIN cd.members ON cd.bookings.memid = cd.members.memid 
WHERE 
  firstname = 'David' 
  AND surname = 'Farrell';
```

###### Question 2: Work out the start times of bookings for tennis courts
```sql
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
```

###### Question 3: Produce a list of all members, along with their recommender
```sql
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
```

###### Question 4: Produce a list of all members who have recommended another member
```sql
SELECT 
  DISTINCT recs.firstname AS firstname, 
  recs.surname AS surname 
FROM 
  cd.members mems 
  INNER JOIN cd.members recs ON recs.memid = mems.recommendedby 
ORDER BY 
  surname, 
  firstname;
```

###### Question 5: Produce a list of all members, along with their recommender, using no joins.
```sql
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
```

### Aggregation
###### Question 1:  Count the number of recommendations each member makes.
```sql
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
```

###### Question 2:  List the total slots booked per facility 
```sql
SELECT 
  facid, 
  SUM(slots) as "Total Slots" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;
```

###### Question 3:  List the total slots booked per facility in a given month  
```sql
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
```

###### Question 4:  List the total slots booked per facility per month 
```sql
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
```

###### Question 5:  Find the count of members who have made at least one booking  
```sql
SELECT 
  COUNT(DISTINCT memid) 
FROM 
  cd.bookings;
```

###### Question 6:  List each member's first booking after September 1st 2012
```sql
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
```

###### Question 7:  Produce a list of member names, with each row containing the total member count
```sql
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
```

###### Question 8:  Produce a numbered list of members
```sql
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
```

###### Question 9:  Output the facility id that has the highest number of slots booked, again 
```sql
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
```

### Strings
###### Question 1: Format the names of members
```sql
SELECT 
  surname || ',' || firstname AS name 
FROM 
  cd.members;
```

###### Question 2: Find telephone numbers with parentheses 
```sql
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone LIKE '(___) ___-____';
```

###### Question 3: Count the number of members whose surname starts with each letter of the alphabet
```sql
SELECT 
  SUBSTR(mems.surname, 1, 1) AS letter, 
  COUNT(*) AS count 
FROM 
  cd.members mems 
GROUP BY 
  letter 
ORDER BY 
  letter;
```



