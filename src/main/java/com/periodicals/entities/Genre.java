package com.periodicals.entities;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * Class that represents periodical genre
 */
public class Genre {
    public static final int GENRE_NAME_MAX_LENGTH = 100;

    private UUID id;
    private String name;

    public Genre() {

    }

    public Genre(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) &&
                Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
