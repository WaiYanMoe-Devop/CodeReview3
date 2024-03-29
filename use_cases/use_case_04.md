**USE CASE: 4 Retrieve List of top ten populated countries in the world**
================================================================================

**CHARACTERISTIC INFORMATION**
=================================

**Goal in Context**
===================

As an Analyst, I want to retrieve a list of top ten populated countries in the world, so that I can use this information to assist the understanding of the demographic distribution of the specific population of the countries in the world.

**Scope**
==========
 
organizational black box

**Level**
==========

Primary task.

**Preconditions**
=================

The system has access to a database containing data for populated countries in the world.

**Success End Condition**
=========================

A list of countries in the world, ordered by top ten, is provided to the Analyst.

**Failed End Condition**
======================

No list is generated, or an error occurs during the retrieval process  the system will show "Failed to get top populated countries" message.

**Primary Actor**
=================

Analyst 

**Trigger**
============

A request for population information is sent to Analyst by organization. Analyst initiates the request to retrieve the list of countries in the world organized by top ten.

**MAIN SUCCESS SCENARIO**
==========================

1. The analyst initiates the request to retrieve the list of countries in the world organized by top ten.
2. The system receives the request.
3. The system retrieves the population data of all countries in the world from the database.
4. The system organizes the list of countries in the world by top ten.
5. The system provides the top ten populated list of countries in the world to the analyst according to organization request. 

**EXTENSIONS**
================

In step 3,

1. if there is an error during the retrieval or processing of data, the system informs the analyst about the issue.
2. if the database does not contain the required population data, the system informs the analyst that the information is unavailable.

**SUB-VARIATIONS**
====================

None.

**SCHEDULE**
================

DUE DATE: 2/2/2024

