Human Map
=========

That app shows some use cases for MongoDb Geospatial Indexing. 
I made it as demo for my talk at #HumanTalks.


How to populate Mongo ?
---

I formatted data from http://datastore.opendatasoft.com/public/explore/dataset/tournagesdefilmsparis2011 into a json file.
These are about movie shootings which took place in Paris since 2008.
That Json file can be found in /populate folder

Just use the following command line to populate Mongo :

> mongoimport --db HumanMapInfo --collection pois --file tournagesdefilmsparis2011_final.json --journal --jsonArray

---

For now, it took me 4 Guinesses to build it.

