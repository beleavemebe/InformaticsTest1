package itis.socialtest;

import itis.socialtest.entities.Author;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AuthorStorage {

    Map<Long, Author> data;

    public AuthorStorage(String source) throws FileNotFoundException {
        data = parseAuthors(new File(source));
    }

    private Map<Long, Author> parseAuthors(File source) throws FileNotFoundException {
        Scanner scan = new Scanner(source);
        Map<Long, Author> data = new HashMap<>();
        while (scan.hasNext()) {
            Author author = parseAuthorFromString(scan.nextLine());
            data.put(author.getId(), author);
        }
        return data;
    }

    private Author parseAuthorFromString(String arg) {
        String[] args = arg.split(", ");
        return new Author(Long.parseLong(args[0]), args[1], args[2]);
    }

    public Author getAuthor(long id) {
        return data.get(id);
    }

}
