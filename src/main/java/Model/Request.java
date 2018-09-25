package Model;

import java.util.Map;

public class Request {
    private String URL;
    private String Method;
    private Map<String, String> Parameters;

    private boolean consoleLog;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public Map<String, String> getParameters() {
        return Parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        Parameters = parameters;
    }

    public boolean isConsoleLog() {
        return consoleLog;
    }

    public void setConsoleLog(boolean consoleLog) {
        this.consoleLog = consoleLog;
    }
}