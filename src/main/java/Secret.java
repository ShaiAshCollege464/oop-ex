import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Secret {

    private final String id;

    public Secret(String id) {
        this.id = id;
    }

    public void check () {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", this.id);
            String value = getAuctionFromServer(params);
            JSONObject jsonObject = new JSONObject(value);
            int price = jsonObject.getInt("price");
            JSONArray jsonArray = jsonObject.getJSONArray("names");
            String[] names = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                names[i] = jsonArray.getString(i);
            }
            jsonArray = jsonObject.getJSONArray("bids");
            int[] bids = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                bids[i] = jsonArray.getInt(i);
            }
            VickeryAuction vickeryAuction = new VickeryAuction("", price);
            for (int i = 0; i < names.length; i++) {
                Participant participant = new Participant(names[i], "",bids[i]);
                vickeryAuction.addParticipant(participant);
            }
            Winner winner = vickeryAuction.getWinner();
            String[] tokens = winner.toString().split("_");
            params.put("name", tokens[0].replace(" ", "_"));
            params.put("price", tokens[1]);
            String[] results = getResults(params);
            String[] resultsText = new String[2];
            if (results != null && results.length == 3) {
                int count = 0;
                boolean result = Boolean.parseBoolean(results[0]);
                if (result) {
                    count++;
                }
                resultsText[0] = "Winner Name: " + (result ? "\033[32mCORRECT\033[0m" : "\033[31mWRONG\033[0m");
                result = Boolean.parseBoolean(results[1]);
                if (result) {
                    count++;
                }
                resultsText[1] = "Price: " + (result ? "\033[32mCORRECT\033[0m" : "\033[31mWRONG\033[0m");
                for (int i = 0; i < resultsText.length; i++) {
                    System.out.println(resultsText[i]);
                    Thread.sleep(2 * Constants.SECOND);
                }
                if (count == 2) {
                    System.out.printf( "\033[34mCongrats %s, your all done!\033[0m", results[2]);
                } else {
                    System.out.println("Fix the errors and try again...");
                }
                System.out.println();
                System.out.println();
                System.out.println();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String[] getResults (Map<String, String> params)  throws Exception {
        String[] tokens = null;
        String result = sendGetRequest(Constants.DOMAIN, Paths.check.getPath(), params);
        if (result != null) {
            tokens = result.split(Constants.UNDERSCORE);
        }
        return tokens;
    }

    private String getAuctionFromServer (Map<String, String> params)  throws Exception {
        return sendGetRequest(Constants.DOMAIN, Paths.auction.getPath(), params);
    }

    public String sendGetRequest(String domain, String path, Map<String, String> params) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(domain + path);
        urlBuilder.append(Constants.QUESTION_MARK);
        for (Map.Entry<String, String> param : params.entrySet()) {
            urlBuilder.append(param.getKey()).append(Constants.EQUALS).append(param.getValue()).append(Constants.AND);
        }
        String urlString = urlBuilder.toString();
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(Constants.METHOD_GET);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }


}
