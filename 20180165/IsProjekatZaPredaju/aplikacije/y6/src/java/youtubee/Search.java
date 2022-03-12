package youtubee;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;

public class Search {
    
    private static YouTube youtube;

    public static List<SearchResult> getVideos(String title,long maxNumberVideos) {
        List<SearchResult> searchResultList = null;
        try {
            youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), (HttpRequest request) -> {})
                    .setApplicationName("youtube-cmdline-search-sample").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey = "AIzaSyAuvQQE4u5CPnd3e2sKSGF-9lfl2vx9NXQ";
            search.setKey(apiKey);
            search.setQ(title); 
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(maxNumberVideos);
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();            
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (GeneralSecurityException t) {
            t.printStackTrace();
        }
        return searchResultList;
    }
    
    public static List<SearchResult> getVideos(String title) {
        return getVideos(title,5);
    }
    
    public static void runBrowser(String naslov) throws Exception {
            List<SearchResult> searchResultList = Search.getVideos(naslov);
            ResourceId rid = searchResultList.get(0).getId();
            String url = "https://www.youtube.com/watch?v=" + rid.getVideoId();
            URL u = new URL(url);
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(u.toURI());
    }

}