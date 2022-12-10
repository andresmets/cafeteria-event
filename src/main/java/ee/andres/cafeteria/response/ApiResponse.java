package ee.andres.cafeteria.response;

import com.fasterxml.jackson.databind.JsonNode;

public class ApiResponse {
    private Integer status;
    private JsonNode response;
    private JsonNode labels;

    public ApiResponse(Integer status) {
        this.status = status;
    }
    public ApiResponse(Integer status, JsonNode response) {
        this.status = status;
        this.response = response;
    }

    public ApiResponse(Integer status, JsonNode response, JsonNode labels) {
        this.status = status;
        this.response = response;
        this.labels = labels;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public JsonNode getResponse() {
        return response;
    }

    public void setResponse(JsonNode response) {
        this.response = response;
    }

    public JsonNode getLabels() {
        return labels;
    }

    public void setLabels(JsonNode labels) {
        this.labels = labels;
    }
}
