package com.github.tokuramakura.quarkus.kotlin.coroutine;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.reactive.ReactiveFlowKt;
import org.jboss.resteasy.spi.AsyncStreamProvider;
import org.reactivestreams.Publisher;

import javax.ws.rs.ext.Provider;

@Provider
public class FlowProvider implements AsyncStreamProvider<Flow<?>> {
    @Override
    public Publisher<?> toAsyncStream(Flow<?> flow) {
        return ReactiveFlowKt.asPublisher(flow);
    }
}
