package com.kevin889.as_rs.core;

/**
 * Created by kevin889 on 16-04-15.
 */
public class Customer {

    private static String name;
    private static String surname;
    private static String address;
    private static String zipcode;
    private static String city;

    public Customer(String name, String surname, String address, String zipcode, String city) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
    }

    public static String getName() {
        return name;
    }

    public static String getSurname() {
        return surname;
    }

    public static String getAddress() {
        return address;
    }

    public static String getZipcode() {
        return zipcode;
    }

    public static String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Customer: "+ name + ", " + surname;
    }
}
