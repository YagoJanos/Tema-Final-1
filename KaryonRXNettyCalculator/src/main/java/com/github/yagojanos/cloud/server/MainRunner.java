package com.github.yagojanos.cloud.server;

import com.github.yagojanos.cloud.handler.HealthCheckResource;
import com.github.yagojanos.cloud.handler.RxNettyHandler;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import netflix.karyon.health.HealthCheckHandler;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;

public class MainRunner {

    public static void main(String[] args) {

        HealthCheckHandler healthCheckResource = new HealthCheckResource();
        RxNettyHandler mainRXNettyHandler = new RxNettyHandler("/healthcheck", new HealthCheckEndpoint(healthCheckResource));
        HttpServer<ByteBuf, ByteBuf> rxNettyServer = RxNetty.createHttpServer(8081, mainRXNettyHandler);
        rxNettyServer.startAndWait();
    }
}
