package itis.socialtest;


import itis.socialtest.entities.Post;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/*
 * В папке resources находятся два .csv файла.
 * Один содержит данные о постах в соцсети в следующем формате: Id автора, число лайков, дата, текст
 * Второй содержит данные о пользователях  - id, никнейм и дату рождения
 *
 * Напишите код, который превратит содержимое файлов в обьекты в package "entities"
 * и осуществите над ними следующие опреации:
 *
 * 1. Выведите в консоль все посты в читабельном виде, с информацией об авторе.
 * 2. Выведите все посты за сегодняшнюю дату
 * 3. Выведите все посты автора с ником "varlamov"
 * 4. Проверьте, содержит ли текст хотя бы одного поста слово "Россия"
 * 5. Выведите никнейм самого популярного автора (определять по сумме лайков на всех постах)
 *
 * Для выполнения заданий 2-5 используйте методы класса AnalyticsServiceImpl (которые вам нужно реализовать).
 *
 * Требования к реализации: все методы в AnalyticsService должны быть реализованы с использованием StreamApi.
 * Использование обычных циклов и дополнительных переменных приведет к снижению баллов, но допустимо.
 * Парсинг файлов и реализация методов оцениваются ОТДЕЛЬНО
 *
 *
 * */

public class MainClass {

    private List<Post> allPosts;

    private AnalyticsService analyticsService = new AnalyticsServiceImpl();

    public static void main(String[] args) {
        new MainClass().run("src/itis/socialtest/resources/PostDatabase.csv",
                "src/itis/socialtest/resources/Authors.csv");
    }

    private void run(String postsSourcePath, String authorsSourcePath) {
        try {
            authors = new AuthorStorage(authorsSourcePath);
            allPosts = parsePosts(new File(postsSourcePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("1 распечатать все посты");
        task1();
        System.out.println("2 все посты за сегодня");
        task2();
        System.out.println("3 все посты варламова");
        task3();
        System.out.println("4 содержит ли хоть один пост слово Россия");
        task4();
        System.out.println("5 никнейм самого популярного автора");
        task5();
    }

    public void task1() {
        System.out.println(allPosts);
    }

    public void task2() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        int month = gregorianCalendar.get(Calendar.MONTH) + 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(day)
                .append('.')
                .append(month % 10 != 0 ? "0" + month : month)
                .append('.')
                .append(new Date().getYear() + 1900);
        String date = stringBuilder.toString();
        System.out.println(analyticsService.findPostsByDate(allPosts, date));
    }

    public void task3() {
        System.out.println(analyticsService.findAllPostsByAuthorNickname(allPosts, "varlamov"));
    }

    public void task4() {
        System.out.println(analyticsService.checkPostsThatContainsSearchString(allPosts, "Россия"));
    }

    public void task5() {
        System.out.println(analyticsService.findMostPopularAuthorNickname(allPosts));
    }

    private AuthorStorage authors;

    private List<Post> parsePosts(File source) throws FileNotFoundException {
        ArrayList<Post> posts = new ArrayList<>();
        Scanner scan = new Scanner(source);
        while (scan.hasNext()) {
            posts.add(parsePostFromString(scan.nextLine()));
        }
        return posts;
    }

    private Post parsePostFromString(String str) {
        String[] arr = str.split(", ");
        return new Post(arr[2], arr[3], Long.parseLong(arr[1]), authors.getAuthor(Integer.parseInt(arr[0])));
    }

}
