select * from userfeedback
select * from atomicfeedback
select * from category

select * from atomicfeedback_category



delete from atomicfeedback_category
delete from atomicfeedback
delete from userfeedback













INSERT INTO Category (name)
SELECT 'Examples' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Examples')
UNION ALL
SELECT 'Inspiration' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Inspiration')
UNION ALL
SELECT 'Tempo' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Tempo')
UNION ALL
SELECT 'Usefulness' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Usefulness')
UNION ALL
SELECT 'EducationalValue' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'EducationalValue')
UNION ALL
SELECT 'RoomEnvironment' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'RoomEnvironment')
UNION ALL
SELECT 'Catering' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Catering')
UNION ALL
SELECT 'Speakers' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Speakers')
UNION ALL
SELECT 'Timing' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Timing')
UNION ALL
SELECT 'Readability' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Readability')
UNION ALL
SELECT 'Boredom' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Boredom')
UNION ALL
SELECT 'Complexity' WHERE NOT EXISTS (SELECT 1 FROM Category WHERE name = 'Complexity');





