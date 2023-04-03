import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalApiHelper {

    private static final String API_URL = "https://api.publicapis.org/entries";

    private static List<ApiEntry> entries;

    static {
        Response response = RestAssured.get(API_URL);
        entries = response.jsonPath().getList("entries", ApiEntry.class);
    }

    public static List<ApiEntry> getApiEntries() {
        return entries;
    }

    public static List<ApiEntry> getAnimalGithubApis(List<ApiEntry> entries) {
        if (entries == null) {
            throw new IllegalArgumentException("entries cannot be null");
        }
        return entries.stream()
                .filter(entry -> entry.getCategory() != null && entry.getCategory().equals("Animals"))
                .filter(entry -> entry.getLink() != null && entry.getLink().contains("github"))
                .collect(Collectors.toList());
    }

    public static Set<String> getDistinctCorsValues(List<ApiEntry> entries) {
        if (entries == null) {
            throw new IllegalArgumentException("entries cannot be null");
        }
        return entries.stream()
                .map(ApiEntry::getCors)
                .filter(cors -> cors != null && !cors.isEmpty())
                .collect(Collectors.toSet());
    }

    public static long getNumberOfAnimalApis(List<ApiEntry> entries) {
        if (entries == null) {
            throw new IllegalArgumentException("entries cannot be null");
        }
        return entries.stream()
                .filter(entry -> entry.getCategory() != null && entry.getCategory().equals("Animals"))
                .count();
    }
}
