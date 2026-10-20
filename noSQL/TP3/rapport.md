## EX 3

**_1. _**

MATCH (a) RETURN count(a)


MATCH (n:OSMNode) return * 


MATCH (n:OSMNode) return * limit(50)


MATCH (n:OSMNode) where n.name is not null order by n.name return distinct n.name  limit(50)

MATCH (n:PointOfInterest) where n.name = "Delacorte Musical Clock" return n.type

Match (n:PointOfInterest) match(clock:PointOfInterest) where clock.type = "clock" and point.distance(n.location, clock.location) < 200 and n<>clock return n


## EX 4

1.
PROFILE MATCH (n:PointOfInterest) where n.name="Delacorte Musical Clock" return n

PROFILE MATCH(n) where n.name="Delacorte Musical Clock" return n

2.

SHOW INDEXES

create TEXT index index_poi_name for (n:PointOfInterest) on (n.name)

SHOW INDEXES

3.
PROFILE MATCH (n:PointOfInterest) where n.name="Delacorte Musical Clock" return n

PROFILE MATCH(n) where n.name="Delacorte Musical Clock" return n

## EX 5

MATCH ()-[n:OSMEdge]->() return *

devient :

MATCH ()-[n:ROUTE]->() return *

match (n{name:"Delacorte Musical Clock"})-[]-(e) return *

match (n{type:"clock"})-[*0..5]-(e) return *