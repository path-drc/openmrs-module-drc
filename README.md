# DRC Module

## Description
This module contains customizations of the DRC instance to provide for any instance specific configuration.

## Provided Functionality

### Admin User Initializer
This initializer ensures that the default language for the Admin User is set to the system's default language (in the case
of the DRC, this should always be French).

### Location Initializer
This initializer ensures that only the locations associated with this instance are available. It relies on the OpenMRS
runtime property `drc.instance`, which can be set by setting the OMRS_EXTRA_DRC_INSTANCE environment variable in the
backend container or via a .env file. This should be set to the name of the top-level location associated with that site. 
For instance, all locations under "Centre Hospitalier Akram" will be enabled if this is set to "Centre Hospitalier Akram"
(omitting the quotation marks).
