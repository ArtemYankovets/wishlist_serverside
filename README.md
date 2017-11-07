# WISHLIST project
This is the servers side of a project. 
There are Spring Boot, Spring Data, Spring REST, MongoDB technologies in this project.

**Main REST end point methods**<br/>
**0. Server:**<br/>
        <ul>
          <li>Testing connection with server - /test</li>
        </ul>
**1. User:**<br/>
    1.1. CREATE<br/>
        <ul>
          <li>Creating new User - /api/wishlist/users/new</li>
        </ul>
    1.2. READ<br/>
        <ul>
           <li>Getting User by id - /api/wishlist/users/{id}</li>
           <li>Getting all Users in collection - /api/wishlist/users/all</li>
        </ul>
    1.3. UPDATE<br/>
        <ul>
           <li>Updating list of WishList ids in User - /api/wishlist/users/{id}/update/wishlist</li>
        </ul>
    1.4. DELETE<br/>
        <ul>
           <li>Removing User - /api/wishlist/users/remove/{id}</li>
        </ul>
    
**2. WishList:**<br/>
    2.1. CREATE<br/>
        <ul>
           <li>Creating new WishList - /api/wishlist/lists/new</li>
        </ul>
    2.2. READ<br/>
        <ul>
           <li>Getting WishList - /api/wishlist/lists/{id}</li>
           <li>Getting all WishLists - /api/wishlist/lists/all</li>
        </ul>
    2.3. UPDATE<br/>
        <ul>
           <li></li>
        </ul>
    2.4. DELETE<br/>
        <ul>
           <li>Removing WishList - /api/wishlist/list/remove/{id}</li>
        </ul>
    
**3. Wish:**<br/>
    3.1. CREATE<br/>
        <ul>
           <li>Creating new Wish /api/wishlist/wishes/new</li>
        </ul>
    3.2. READ<br/>
        <ul>
           <li>Getting Wish - /api/wishlist/wishes/{id}</li>
           <li>Getting all Wishes - /api/wishlist/wishes/all</li>
        </ul>
    3.3. UPDATE<br/>
        <ul>
           <li>
               <b>Updating a one Wish</b><br/>
               <u>Request Method:</u> PATCH<br/>
               <u>Input:</u> one Wish in JSON format<br/>
               <u>Path:</u> /api/wishlist/wishes/update<br/>
               <u>Description:</u>
               <i>Updates a one Wish. If you add connection to a WishList new changes would be making in WishList also.
               But if a Wish was contained in a WishList and in a new version of a Wish this connection is removed
               the WishList would not be updated.
               For this case and for removing Wishes form a WishList must be used path statement for 'updateOneWishList' 
               or 'updateListOfWishLists' methods.</i>
           </li>
           <li>
               <b>Updating a list of Wishes</b><br/>
               <u>Request Method:</u> PATCH<br/>
               <u>Input:</u> list of Wishes in JSON format<br/>
               <u>Path:</u> /api/wishlist/wishes/update/list<br/>
               <u>Description:</u>
               <i>Updates list of Wishes. If you add connections to a WishList new changes would be making in WishList also.
               But if a Wish was contained in a WishList and in a new version of a Wish this connection is removed
               the WishList would not be updated.
               For this case and for removing Wishes form a WishList must be used path statement for 'updateOneWishList' 
               or 'updateListOfWishLists' methods.</i>
           </li>
        </ul>
    3.4. DELETE<br/>
        <ul>
           <li>Removing Wish - /api/wishlist/wishes/remove/{id}</li>
        </ul>
