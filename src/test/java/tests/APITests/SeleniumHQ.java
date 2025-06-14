package tests.APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class SeleniumHQ {

    private final String BASE_URL = "https://api.github.com/orgs/SeleniumHQ/repos";

    @Test
    public void testGitHubRepoData() {
        Response response = RestAssured
                .given()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JSONArray repos = new JSONArray(response.getBody().asString());

        int totalOpenIssues = 0;
        Map<String, Integer> watchersMap = new HashMap<>();
        List<JSONObject> repoList = new ArrayList<>();

        for (int i = 0; i < repos.length(); i++) {
            JSONObject repo = repos.getJSONObject(i);
            totalOpenIssues += repo.getInt("open_issues_count");

            watchersMap.put(repo.getString("name"), repo.getInt("watchers_count"));
            repoList.add(repo);
        }

        // a. Total open issues
        System.out.println("Total Open Issues: " + totalOpenIssues);

        // b. Sort by updated_at (desc)
        repoList.sort((a, b) -> b.getString("updated_at").compareTo(a.getString("updated_at")));
        System.out.println("Top 3 Recently Updated Repos:");
        repoList.subList(0, Math.min(3, repoList.size()))
                .forEach(repo -> System.out.println(repo.getString("name") + " -> " + repo.getString("updated_at")));

        // c. Repo with most watchers
        String topWatchedRepo = Collections.max(watchersMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        int topWatchers = watchersMap.get(topWatchedRepo);
        System.out.println("Most Watched Repo: " + topWatchedRepo + " (" + topWatchers + " watchers)");

        assertTrue(totalOpenIssues >= 0);

    }

}
