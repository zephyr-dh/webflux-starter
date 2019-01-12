package io.education.springboot.webflux.starter.router;

import io.education.springboot.webflux.starter.domain.User;
import io.education.springboot.webflux.starter.handler.TimeHandler;
import io.education.springboot.webflux.starter.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.*;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

//静态导入

import java.util.Date;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

/**
 * @Description
 * @auther zephyr
 * @create 2019-01-10 11:50 AM
 */

@Configuration
public class RouterConfiguration {
    @Autowired
    private TimeHandler timeHandler;

    @Autowired
    private UserHandler userHandler;


    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        return RouterFunctions.route(GET("/time"), timeHandler::getTime)
                .andRoute(GET("/date"), timeHandler::getDate)
                .andRoute(GET("/times"), timeHandler::sendTimePerSec);
    }

    @Bean
    public RouterFunction<ServerResponse> userRouter() {

        /*
        public static <ServerResponse> RouterFunction<ServerResponse> route(RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction)
         */
        return RouterFunctions.route(
                //增
//                POST("/user"), request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userHandler.save(), User.class)
                POST("/user"), request -> {
                    Mono<MultiValueMap<String, String>> formData = request.formData();
                    Mono<User> user = formData.map(m -> new User(null,m.get("username").get(0),m.get("phone").get(0),m.get("email").get(0),m.get("name").get(0),new Date()));
                    Disposable disposable = user.subscribe(userHandler::save);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.empty(), Void.class);
                }
        ).andRoute(
                //查
                GET("/user"), request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userHandler.findAll(), User.class)
        ).andRoute(
                //查
                GET("/user/{username}"), request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userHandler.findByUsername(request.pathVariable("username")), User.class)
        ).andRoute(
                //删
                DELETE("/user/{username}"), request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userHandler.deleteByUsername(request.pathVariable("username")), Long.class)
        );
    }
}
