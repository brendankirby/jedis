package redis.clients.jedis;

import java.util.LinkedList;
import java.util.Queue;

import redis.clients.jedis.exceptions.JedisDataException;

public class Queable {
    private Queue<Response<?>> pipelinedResponses = new LinkedList<Response<?>>();

    protected void clean() {
        pipelinedResponses.clear();
    }

    protected Response<?> generateResponse(Object data) {
        if (data instanceof JedisDataException){
            throw new JedisDataException((JedisDataException)data);
        }

        Response<?> response = pipelinedResponses.poll();
        if (response != null) {
            response.set(data);
        }
        return response;
    }

    protected <T> Response<T> getResponse(Builder<T> builder) {
        Response<T> lr = new Response<T>(builder);
        pipelinedResponses.add(lr);
        return lr;
    }

}
