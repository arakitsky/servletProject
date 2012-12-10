package com.alex.servletProject;

/**
 * .
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public enum State {
    NONE(1),STATE_1(0),STATE_2(1);

    private int status;

    private State() {
    }

    private State(int status) {
        this.status = status;
    }
}
