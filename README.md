### Throttler
Small spring-boot starter for canary release of new feature (microservice) calls in monolith application, does a throttling of method calls on application level

### Usage
Add a `@Throttle(percentage)` annotation to service call you want to canary release
```java
@Throttle(30)
public Object newRiskyCall() {
    some_risky_call_code_here;
}
```
Add call to proper place in business logic
```java
@GetMapping("/example")
public ResponseEntity example() {
    exampleService.example(params);
    newExampleService.newRiskyCall(params);
    return ResponseEntity.ok().build();
}
```
As result, calls will be intercepted by starter and it will pass only specified amount of traffic 
```
2020-11-22 13:27:25.432 TRACE 9712 --- [nio-8080-exec-1] c.g.m.throttler.ThrottlingInterceptor    : Method call passed, percentage before call: 0.0
2020-11-22 13:27:25.658 TRACE 9712 --- [nio-8080-exec-2] c.g.m.throttler.ThrottlingInterceptor    : Method call skipped, percentage before call: 50.0
2020-11-22 13:27:26.112 TRACE 9712 --- [nio-8080-exec-3] c.g.m.throttler.ThrottlingInterceptor    : Method call skipped, percentage before call: 33.33333333333333
2020-11-22 13:27:26.417 TRACE 9712 --- [nio-8080-exec-4] c.g.m.throttler.ThrottlingInterceptor    : Method call passed, percentage before call: 25.0
2020-11-22 13:27:26.600 TRACE 9712 --- [nio-8080-exec-5] c.g.m.throttler.ThrottlingInterceptor    : Method call skipped, percentage before call: 40.0
2020-11-22 13:27:26.768 TRACE 9712 --- [nio-8080-exec-6] c.g.m.throttler.ThrottlingInterceptor    : Method call skipped, percentage before call: 33.33333333333333
2020-11-22 13:27:26.945 TRACE 9712 --- [nio-8080-exec-7] c.g.m.throttler.ThrottlingInterceptor    : Method call passed, percentage before call: 28.57142857142857
2020-11-22 13:27:27.104 TRACE 9712 --- [nio-8080-exec-8] c.g.m.throttler.ThrottlingInterceptor    : Method call skipped, percentage before call: 37.5
```
