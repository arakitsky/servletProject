package com.alex.servletProject;

/**
 * List of states. Contains the signal needed to go to the next state.
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public enum State {
    NONE(1),STATE_1(0),STATE_2(1);

    /**
     * signal needed to go to the next state
     */
    private int signalChange;

    /**
     * @param signal signal needed to go to the next state.
     */
    private State(int signal) {
        this.signalChange = signal;
    }

    /**
     * @return signal needed to go to the next state.
     */
    public int getSignalChange(){
        return signalChange;
    }

}
