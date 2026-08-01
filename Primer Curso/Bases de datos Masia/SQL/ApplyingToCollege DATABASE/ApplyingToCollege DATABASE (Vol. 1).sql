-- 2 Show ids, names, surnames, and marks of the students with mark > 3.6.
select id,NAME,SURNAME,MARK
from STUDENTS
where mark > 3.6;

-- 3. Show student names, surnames, and majors that they have applied to. Sort the
-- results by name and surname.
select NAME,SURNAME,MAJOR
from STUDENTS join APPLIES on STUDENTS.ID=APPLIES.SID
order by NAME,SURNAME;

-- 4. Show names, surnames, marks and application decision of students with
-- size_high_school < 1000 applying to CS at Stanford.
select NAME,SURNAME,MARK,DECISION
from STUDENTS join APPLIES on STUDENTS.ID=APPLIES.SID
where SIZE_HIGH_SCHOOL<1000 and COLLEGE='Stanford' and MAJOR='CS';

-- 5. List all large campuses (enrolment> 20,000) with CS applicants.
select distinct NAME
from COLLEGES join APPLIES on COLLEGES.NAME=APPLIES.COLLEGE
where ENROLLMENT>20000 and MAJOR='CS';

-- 6. List applicants to bio majors.
select NAME
from STUDENTS join APPLIES on STUDENTS.ID=APPLIES.SID
where MAJOR='biology';

-- 7. Show the weighted scores on 10 points (now weighted on 5 points)
select NAME, MARK*2 as "Weighted score on 10 points"
from STUDENTS;

-- 8. Scale the marks considering the size of the high school
-- (mark*(high_school_size/1000)). Show the weighted scores on 5 and 10 points.
select (MARK*(SIZE_HIGH_SCHOOL/1000)) as "Mark scaled", MARK,MARK*2 as "Weighted score on 10 points"
from STUDENTS;

-- 9. List the average mark of all students applying to CS (you must think that the same
-- student could apply for CS in many Universities). Clue: wrong average
-- 3.714285714285714, right average 3.6800000000000006.
select round(avg(mark), 15) as avg_mark
from students s
where exists (
    select 1
    from applies a
    where a.sid = s.id
      and a.major = 'CS'
);
create or replace view exercise9 as
select distinct s.id, s.mark
from STUDENTS s,
     APPLIES a
where s.id = a.sid
  and a.major = 'CS'
with read only;

select * from exercise9;
select round(avg(mark), 15) as avg_mark
from exercise9;

-- 10. List the lowest mark of students applying to CS.
select min(MARK)
from STUDENTS s
where exists(
    select 1
    from APPLIES a
    where a.sid = s.ID
    and a.MAJOR = 'CS'
);

-- 11. List the number of colleges bigger than 15,000.
select count(*)
from COLLEGES
where ENROLLMENT>15000;

-- 12. List the number of students applying to Cornell (you must think that students can
-- apply to many majors in the same University). Clue: The right number should be 3.
select count(*)
from STUDENTS s
where exists(
    select 1
    from APPLIES a
    where s.ID=a.SID
    and a.COLLEGE='Cornell'
    );
select count(distinct (SID))
from APPLIES
where COLLEGE='Cornell';
-- 13. List the number of applications to each college (sorted by college name)
select COLLEGE,count(*)
from APPLIES
group by COLLEGE
order by COLLEGE;

-- 15. List the number of college enrolments by state.
select count(*),STATE
from COLLEGES join APPLIES on COLLEGES.NAME=APPLIES.COLLEGE
group by STATE;

select count(*),STATE
from COLLEGES c,applies a
where a.COLLEGE=c.NAME
group by STATE;

select sum(ENROLLMENT),STATE
from COLLEGES
group by STATE;

-- 16. List the minimum and maximum marks of all the students
select min(MARK) as "Minimum mark", max(MARK) as "Maximum mark"
from STUDENTS;

-- 17. List the difference between the maximum and the minimum marks of all the
-- students.
select max(MARK)-min(MARK) as "Difference between the maximum and minimum mark"
from STUDENTS;

-- 18. List the number of colleges applied to by each student. Sort the results by student
-- surname, student name and student id.
select NAME,SURNAME,ID,count(*) as "Number of colleges applied to"
from STUDENTS join APPLIES on STUDENTS.ID=APPLIES.SID
group by NAME,SURNAME,ID
order by SURNAME,NAME,ID;

select NAME,SURNAME,ID,count(distinct(a.COLLEGE)) as "Number of colleges applied to"
from STUDENTS s,APPLIES a
where s.ID=a.SID
group by NAME,SURNAME,ID
order by SURNAME,NAME,ID;

-- 19. List the number of colleges applied to by each student, including 0 for those who
-- applied nowhere. CLUE: Do the union between to queries.
(select NAME, SURNAME, ID, count(distinct (a.COLLEGE)) as "Number of colleges applied to"
from STUDENTS s join APPLIES a on s.ID = a.SID
group by NAME, SURNAME, ID)
union
(select NAME, SURNAME, ID, 0 as "Number of colleges applied to"
from STUDENTS
where ID not in (select SID from APPLIES));

-- 20. Do the last query sorting by student surname, student name and student id.
(select NAME, SURNAME, ID, count(*) as "Number of colleges applied to"
 from STUDENTS join APPLIES on STUDENTS.ID = APPLIES.SID
 group by NAME, SURNAME, ID)
union
(select NAME, SURNAME, ID, 0 as "Number of colleges applied to"
 from STUDENTS
 where ID not in (select SID from APPLIES))
order by SURNAME, NAME, ID;

