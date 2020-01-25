package com.github.tokuramakura.quarkus.kotlin.coroutine;

import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.reactive.ReactiveFlowKt;
import org.jboss.resteasy.spi.AsyncStreamProvider;
import org.reactivestreams.Publisher;

import javax.ws.rs.ext.Provider;

@Provider
public class ChannelProvider implements AsyncStreamProvider<Channel<?>> {
    @Override
    public Publisher<?> toAsyncStream(Channel<?> channel) {
        return ReactiveFlowKt.asPublisher(FlowKt.consumeAsFlow(channel));
    }
}
