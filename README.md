# WISHLIST project
This is the servers side of a project. 
There are Spring Boot, Spring Data, Spring REST, MongoDB technologies in this project.

**Main REST end point methods**<br/>
**1. User:**<br/>
    1.1. CREATE<br/>
        <ul>
          <li>Creating new User - /users/new</li>
        </ul>
    1.2. READ<br/>
        <ul>
           <li>Getting User by id - /users/{id}</li>
           <li>Getting all Users in collection - /users/all</li>
        </ul>
    1.3. UPDATE<br/>
        <ul>
           <li>Updating list of WishList ids in User - /users/{id}/update/wishlist</li>
        </ul>
    1.4. DELETE<br/>
        <ul>
           <li>Removing User - /users/remove/{id}</li>
        </ul>
    
**2. WishList:**<br/>
    2.1. CREATE<br/>
        <ul>
           <li>Creating new WishList - /lists/new</li>
        </ul>
    2.2. READ<br/>
        <ul>
           <li>Getting WishList - /lists/{id}</li>
           <li>Getting all WishLists - /lists/all</li>
        </ul>
    2.3. UPDATE<br/>
        <ul>
           <li></li>
        </ul>
    2.4. DELETE<br/>
        <ul>
           <li>Removing WishList - /list/remove/{id}</li>
        </ul>
    
**3. Wish:**<br/>
    3.1. CREATE<br/>
        <ul>
           <li>Creating new Wish /wishes/new</li>
        </ul>
    3.2. READ<br/>
        <ul>
           <li>Getting Wish - /wishes/{id}</li>
           <li>Getting all Wishes - /wishes/all</li>
        </ul>
    3.3. UPDATE<br/>
        <ul>
           <li></li>
        </ul>
    3.4. DELETE<br/>
        <ul>
           <li>Removing Wish - /wishes/remove/{id}</li>
        </ul>
