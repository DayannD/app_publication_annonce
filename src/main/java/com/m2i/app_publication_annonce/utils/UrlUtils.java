package com.m2i.app_publication_annonce.utils;

public interface UrlUtils {
    interface User {
        String BASE_URL = "/users";
        String LOGIN = BASE_URL + "/login";
        String REGISTER = BASE_URL + "/register";
        String ID = BASE_URL + "/{id}";
    }

    interface Publication {
        String BASE_URL = "/publications";
        String CREATE = BASE_URL + "/create";
        String ALL = BASE_URL + "/all";
        String ID = BASE_URL + "/{id}";
    }
}
