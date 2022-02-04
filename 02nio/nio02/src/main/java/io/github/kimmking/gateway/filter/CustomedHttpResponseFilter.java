package io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class CustomedHttpResponseFilter implements HttpResponseFilter{
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("xy-java", "lemon-lee-out");
    }
}
