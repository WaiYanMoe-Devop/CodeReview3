**USE CASE: 2 Retrieve List of Countries in a continent by Population Order largest to smallest**
================================================================================

**CHARACTERISTIC INFORMATION**
=================================

**Goal in Context**
===================

As an Analyst, I want to retrieve a list of all countries in a continent organized by their population from largest to smallest, so that I can use this information to assist the understanding of the demographic distribution across countries in a continent.

**Scope**
==========
 
organizational black box

**Level**
==========

Primary task.

**Preconditions**
=================

The system has access to a database containing the population data for all countries of a continent.

**Success End Condition**
=========================

A list of countries in a continent, ordered by population from largest to smallest, is provided to the Analyst.

**Failed End Condition**
======================

No list is generated, or an error occurs during the retrieval process the system will show ""Failed to get country details by continent" message.

**Primary Actor**
=================

Analyst 

**Trigger**
============

A request for population information is sent to Analyst by organization. Analyst initiates the request to retrieve the list of countries in a continent organized by population largest to smallest.

**MAIN SUCCESS SCENARIO**
==========================

1. The analyst initiates the request to retrieve the list of countries in a continent organized by population largest to smallest.
2. The system receives the request.
3. The system retrieves the population data of all countries in a continent from the database.
4. The system organizes the list of countries in a continent from largest population to smallest.
5. The system provides the ordered list of countries in a continent from largest to smallest population to the analyst according to organization request. 

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

