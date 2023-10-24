# Cuisines Registry

## The story:

Cuisines Registry is an important part of Book-That-Table Inc. backend stack. It keeps in memory customer preferences for restaurant cuisines and is accessed by a bunch of components to register and retrieve data. 


The first iteration of this component was implemented by rather inexperienced developer and now may require some cleaning while new functionality is being added. But fortunately, according to his words: "Everything should work and please keep the test coverage as high as I did"


## Your tasks:
1. **[Important!]** Adhere to the boy scout rule. Leave your code better than you found it.
It is ok to change any code as long as the CuisinesRegistry interface remains unchanged.
2. **[Important!]** Please provide your implementation using Core Java without any external frameworks (Spring Boot, Hibernate etc)
3. Make is possible for customers to follow more than one cuisine (return multiple cuisines in de.quandoo.recruitment.registry.api.CuisinesRegistry#customerCuisines)
4. Implement de.quandoo.recruitment.registry.api.CuisinesRegistry#topCuisines - returning list of most popular (highest number of registered customers) ones
5. Create a short write up on how you would plan to scale this component to be able to process in-memory billions of customers and millions of cuisines (Book-That-Table is already planning for galactic scale). (100 words max)

## Submitting your solution

+ Fork it to a **[!]**private**[!]** gitlab repository (go to `Settings -> General -> Visibility, project features, permissions -> Project visibility`).
+ Put the write up mentioned in point 4. into the end of this file.
+ Share the project with gitlab user *quandoo_recruitment_task* (go to `Settings -> Members -> Invite member`, find the user in `Select members to invite` and set `Choose a role permission` to `Developer`)
+ Send us an **ssh** clone link to the repository.

## Scaling 

+ Fistly I would consider using external in-memory caching tools like **Redis**. A high-availability Redis cluster would be a great solution for caching since I would assume the data access rate would be very high. 
+ Secondly I would think about **horizontal scaling** the java component where the load can be distributed. 
+ In order for the java component processing to be as fast as possible and unblocking, I would consider an **event driven design**, in particular reactive frameworks and libraries(`Reactor project, RxJava, Akka` etc) and external tools such as messaging queues (`Kafka`, etc).