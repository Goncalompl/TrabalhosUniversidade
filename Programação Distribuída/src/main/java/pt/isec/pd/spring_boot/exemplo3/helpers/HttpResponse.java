package pt.isec.pd.spring_boot.exemplo3.helpers;

public class HttpResponse {
    private int code;
    private Object response;

    public HttpResponse(int code, Object response) {
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", response=" + response +
                '}';
    }
}

/*
{
    "code": 401,
    "errorMessage": "Credentials invalid",
    "data": [],
}

{
    "code": 200,
    "errorMessage": "",
    "data": [Events],
}
 */