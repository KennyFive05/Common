package Utility;

import Model.Request;
import Model.Response;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

/**
 * commons-lang3-3.8.jar
 */
public class HttpSend {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final String USER_AGENT = "Mozilla/5.0";

    // HTTP 请求
    public static Response send(Request rq) throws IOException {
        if (rq.isConsoleLog())
            System.out.println("****************************** HttpSend.send Start ******************************");

        if (StringUtils.isEmpty(rq.getMethod()))
            rq.setMethod(GET);

        URL obj = new URL(rq.getURL());
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //添加请求头
        con.setRequestMethod(rq.getMethod());
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5"); // get 可不用這行

        // 組成 Data From
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        String parameters = getParameters(rq.getParameters());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        // Rq Log
        if (rq.isConsoleLog()) {
            System.out.println("Request URL: " + rq.getURL());
            System.out.println("Request Method: " + rq.getMethod());
            System.out.println("Query String Parameters: " + parameters);
        }

        Response rs = new Response();
        rs.setStatusCode(con.getResponseCode());
        if(rs.getStatusCode() == 200) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                rs.setResponse(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            rs.setResponse(con.getResponseMessage());
        }

        if (rq.isConsoleLog()) {
            System.out.println("Status Code: " + rs.getStatusCode());
            System.out.println("Response: \r\n" + rs.getResponse());
            System.out.println("****************************** HttpSend.send End ******************************");
        }

        return rs;
    }

    private static String getParameters(Map<String, String> map) {
        String result = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotEmpty(result)) {
                result += "&";
            }
            result += key + "=" + value;
        }

        return result;
    }
}
