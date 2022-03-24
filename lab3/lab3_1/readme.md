## a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

#### **A_EmployeeRepositoryTest**
```
//l.37
assertThat( found ).isEqualTo(alex);

//l.53
assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());
```

#### **B_EmployeeService_UnitTest**
```
//l.63
assertThat(found.getName()).isEqualTo(name);;

//l.93
assertThat(fromDb.getName()).isEqualTo("john");
```

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 
On **B_EmployeeService_UnitTest**, the repository is mocked. Here we create a set of static repository calls with its expected results.

```
Employee john = new Employee("john", "john@deti.com");
Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
```

## c) What is the difference between standard @Mock and @MockBean?
While **@Mock** is an annotation from Mockito library, used to mock classes/interfaces to record behaviours and function calls, **@MockBean** is a Spring Boot class, which loads the Mockito mock into the Spring ApplicationContext as a bean. This is useful when we need to mock an entity dependant on the Spring Context.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
This file is used to override Spring Boot properties when running tests. This is useful because generally we want to have 2 different environments when running code for production and for testing (for example, different database configs).

## e)

#### C_EmployeeController_WithMockServiceTest
- Simulates a light simplified application server with @WebMvcTest.
- Service dependencies are mocked.
- No repository involved..

#### D_EmployeeRestControllerIT
- Uses the full mvc environment (@SpringBootTest)
- The API is fully deployed to Spring Boot Context
- The server-side's entry point is simplified, uses @MockMvc

#### E_EmployeeRestControllerTemplateIT
- Uses the full mvc environment (@SpringBootTest).
- The API is fully deployed to Spring Boot Context.
- In this implementation, the server-side's entry point uses an explicit HTTP client with TestRestTemplate (realistic requests).
