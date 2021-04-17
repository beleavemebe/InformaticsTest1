package itis.socialtest;

import itis.socialtest.entities.Author;
import itis.socialtest.entities.Post;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AnalyticsServiceImpl implements AnalyticsService {
    @Override
    public List<Post> findPostsByDate(List<Post> posts, String date) {
        return posts.stream()
                .filter(post -> post.getDate().contains(date))
                .collect(Collectors.toList());
    }

    @Override
    public String findMostPopularAuthorNickname(List<Post> posts) {
        Map<Long, List<Author>> a = posts.stream()
                .map(Post::getAuthor)
                .collect(Collectors.groupingBy(author -> posts.stream()
                        .filter(post -> post.getAuthor() == author)
                        .mapToLong(Post::getLikesCount)
                        .reduce(Long::sum)
                        .getAsLong()));
        TreeMap<Long, List<Author>> longListTreeMap = new TreeMap<>(a);
        return longListTreeMap.lastEntry().getValue().get(0).getNickname();
    }

    @Override
    public Boolean checkPostsThatContainsSearchString(List<Post> posts, String searchString) {
        return posts.stream()
                .anyMatch(post -> post.getContent().contains(searchString));
    }

    @Override
    public List<Post> findAllPostsByAuthorNickname(List<Post> posts, String nick){
        return posts.stream()
                .filter(post -> post.getAuthor().getNickname().equals(nick))
                .collect(Collectors.toList());
    }
}
