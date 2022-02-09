# _The H Cafe Locations_

##### _This app fetches the restaurant of given ID and displays the associated locations - February 2022_

#### By _**Boris Guenebaut**_

## Description

This app dives into the **company** endpoint of the ChowNow API and uses a simple list/detail UI to demonstrate the fetching of restaurant data.  

It includes a separate module **chow-now-api** that leverages Retrofit to communicate with the API and extract the desired information. It only focuses on desired fields: location name, formatted address, latitude, longitude.

## Setup

1. Use Android Studio or Gradle script to build the app
2. Run the tests in module **app** if needed

## Engineering choices

### Architecture

The architecture follows an MVVM approach. Longer term, it could have a few more abstractions around the Jetpack libraries to ease adaptations down the road.

### ChowNow API

The **chow-now-api** module allows a better separation of concerns and can in fine publish its own library to a centralized repo.

The **chow-now-api** module leverages Retrofit to create an API wrapper and converts JSON responses into models only targeting desired fields.

The **RestaurantRepository** interface allows for various implementations such as **RestaurantRemoteRepository** leveraging the **ChowNowApi** interface. If time allows, it could also lead to a **LocalRestaurantRepository** leveraging Room to cache API results and serve them first when not expired.

### List view

The current list view is a RecyclerView following the standard guidelines. However, it does not handle loading, empty and errors nicely.

Implementing a compound view in the future as described in this [Medium article](https://susuthapa19961227.medium.com/recycler-view-with-empty-view-loading-view-and-error-view-1266c34c1504) should make it much nicer.

### Mapping

Mapping is currently done via [osmdroid](https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library-(Kotlin)) as it is straightforward to integrate and use without any specific developer account.  
For more reliable mapping services and customer support, it would be better to leverage [HERE Android SDK](https://developer.here.com/documentation/android-sdk-navigate/4.10.2.0/dev_guide/index.html), or Mapbox, or Google Maps.

### Layout

UI layouts are in XML but a migration to Compose could be beneficial as the industry moves towards it.

Both Phone and Tablet screens are supported, the tablet adaptation being that the list of locations remains visible on the left side.

### Local persistence

TODO.

### Dependency injection

TODO.

## Legal

Copyright (c) 2022 **_Boris Guenebaut_**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.