# RecipesStorage

It's an online restful application that allows to create recipes with description, ingredients and directions.
All the data is saved in H2 local database.

User can make following api calls:
  - GET:
    - /api/recipe/{id}        to get a certain recipe
    - /api/recipe/search      with request parameters "category" and "name" to look for a recipe
  - POST:
    - /api/recipe/new         to post a recipe and become its author; JSON fields: String name, String description, String category, List<String> ingredients, List<String> directions
    - /api/register           to register to the database and Spring Security to be able to make other api calls; requires JSON with fields "email" and "password"
  - PUT:
    - /api/recipe/{id}        to update a recipe if you are its owner; receiver recipe object in request body like in adding new recipe; requires id of the recipe to update
  - DELETE:
    - /api/recipe/{id}        to delete a recipe that you own

Application requires valid input json and basic http authentication. The app has implemented Spring Security that requires to authenticate in every endpoint except from registering. Users' passwords are encrypted with BCryptPasswordEncoder.
