****************************************
Further Extension Points and Future Work
****************************************

A few action items were identified as future work for Data Cafe.


* Evaluation of Apache Accumulo https://accumulo.apache.org/

* API Guide

 Defining the Public facing interfaces and APIs of DataCafe.


CreateDatacafe/

> Create/Add data source

>> Choose data provider


Project > *(High level folder)

Create

Insert

>> Add the query methods.

Delete

DescribeRelationships

1. Connection between data sources

2. API of each data source <-- Query Parameter and return parameters

2.2 How to limit the query to give IDs / all data.


Query :

CreateWarehouse (_in_drill)/

<User write APIs - store as statements>

>> Names of the data sources, <key, value> pairs.


Warehouse of following DS.



**TODO:**

2.1 Add/remove data sources from data cafe.

* Connect the ID <-- ID Intersection.

2. API of each data sources.

<-- REST/Mongo/ODBC ..


Bindaas Model::

Projects > Providers > APIs

Projects: (keys to keep bundles together)

Group of providers together as a project.

Provider/

Providers: 1:1 relationship with dataProvider (providers as plugins)

Plugins for Mongo, Mysql, postgres, http (REST API for another REST API)

MongoPlugin >>>

user name, password, database, collection


<< Meta-data >> Datacafe.

IQueryHandler Interface

(-) submit or delete

RequestContext - who is making the request -- for secury

1 - 2 3 5 << Query Interface

Bindaas Data Providers

Datacafe (including the individual types of data sources >> adds types of data sources. Connect data sources.) and individual data warehouses

Create a project

> Create a data source.

Create data warehouse
