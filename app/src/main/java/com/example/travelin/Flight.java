package com.example.travelin;

public class Flight {
    /** Number of Fight. */
    private int flight_num;
    /** Name of airline. */
    private String airline_name;
    /** Username in session. */
    private String username;

    /**
     * Default Constructor.
     */
    public Flight() {

    }

    /**
     * Constructor.
     *
     * @param flight_num flight number
     * @param airline_name airline name
     * @param username username
     */
    public Flight(int flight_num, String airline_name, String username) {
        this.flight_num = flight_num;
        this.airline_name = airline_name;
        this.username = username;
    }

    /**
     * Flight number getter.
     *
     * @return flight_num
     */
    public int get_flight_num() {
        return flight_num;
    }

    /**
     * Flight number setter.
     *
     * @param flight_num flight_num
     */
    public void set_flight_num(int flight_num) {
        this.flight_num = flight_num;
    }

    /**
     * Airline name getter.
     *
     * @return airline_name
     */
    public String get_airline_name() {
        return airline_name;
    }

    /**
     * Airline name setter.
     *
     * @param airline_name airline_name
     */
    public void set_airline_name(String airline_name) {
        this.airline_name = airline_name;
    }

    /**
     * Username getter.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username setter.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
