# Origin Backend Take-Home Assignment

### How the solution was architected

#### Basic concepts
I chose to implement three functional programming concepts, `Immutability`, `Purity` and `Monads`.

`Imutability` -> All data classes are immutable, this means they can't change after instantiation and they don't hold `state`.
To change a immutable class we have to instantiate another one with the new values. This concept automatic
makes then `DDD Value Objects`.
The motivation for this is that I've considered this application as a immutable application, this means it receives 
a request, do some "calculation" and returns a value, atomically, so the application does't have to maintain `state`. 

Some benefits of this approach are:

- Thread safety
- No invalid state
- Better encapsulation
- Simpler to test
- More readable and maintainable code

`Purity` -> Purity means that all functions always returns the same output given the same parameters, and they don't produce
side effects, that is, they don't alter the state of any object or variable, event at operational system level.
The pure functions brings the same benefits of immutability.

`Monads` -> I chose to use the `Option` Monad at domain level, so joining this with the null safety feature of Kotlin, 
it is guaranteed that we will NEVER have Null Values and NullPointerException at domain level.

**Important**: I'm using some functional concepts but the intent was not to produce a 100% functional app, I think the hybrid
model of FP and OOP brings more benefits.

`Single Responsability` -> All classes were design to be as small as possible and have one single responsibility.

#### Layers

Below are the main points of attention:

src  
+--main  
+----kotlin  
+------com.origin.risk  
+--------[domain](#domain)  
+----------assets  
+----------customer  
+----------[engine](#engine)  
+----------[insurance](#insurance)    
+--------[web](#web)  
+--test

#### Domain
This layer represents all domain logic off the application. It is free of any
kind of "technology locking", it just contains entities, vo's and business logic. 

#### Engine
This domain layer contains the `Risk Engine`, it is a rule engine based class, responsible
for the evaluation of one or more `Rule` instance. A `Rule` is a interface that receives 
a `customer` and a `riskProfile` and returns a new (immutable) `riskProfile` based on its own logic.

#### Insurance
This layer contains de business logic for calculating the `Risk Profile` for all the lines of insurance.
Here are implemented all the `Rules` for the lines of insurance and each line of insurance has its own `Domain Service`,
responsible for aggregating the rules for that line.

#### Web
The web layer contains the logic to expose this application as a REST API.
The framework of choice was Spring Boot.

### How I would deploy this application
I interpreted the `Risk Profile of Lines of Insurance` as a bounded context, so I would deploy this application
as a single micro service. If we have another context, like `Risk Profile of Credit`, it could be another bounded context,
and so, another service. (Assuming a micro services architecture).
Another approach could be to have a single (generic) Rule Engine Service and each other service (`risk-insurance`, `risk-credit`) consuming it
to execute their rules.

### Running the service

`docker-compose up risk-insurance`

- API will be available at [http://localhost:8080](http://localhost:8080)  
- API documentation will be available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Running the tests

`docker-compose build risk-insurance-test`

**Important:** First builds are slow, subsequent builds will be faster (it is a gradle issue with docker)

