package com.promotick.lafabril.common;

import org.apache.commons.lang3.tuple.Pair;

public class CustomPair<L, R> extends Pair<L, R> {

    private L left;
    private R right;

    public CustomPair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public L getLeft() {
        return this.left;
    }

    @Override
    public R getRight() {
        return this.right;
    }

    @Override
    public R setValue(R value) {
        return right;
    }
}
