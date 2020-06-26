package com.shinonometn.fx

import java.util.stream.Collectors.toMap

infix fun <I, O> Collection<O>.indexing(indexProvider: (O) -> I): MutableMap<I, O> = stream().collect(toMap(
        { i -> indexProvider(i) },
        { i -> i }
))