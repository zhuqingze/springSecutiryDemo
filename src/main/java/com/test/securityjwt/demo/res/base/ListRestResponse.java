package com.test.securityjwt.demo.res.base;


public class ListRestResponse<T> extends BaseResponse {
    String msg;

    ListData<T> result;


    public ListData<T> getResult() {
        return result;
    }

    public ListRestResponse(String msg, int count, T data){
        this.setMessage(msg);
        this.msg = msg;
        this.result = new ListData<>(data,count);
    }

    class ListData<T>{
        private T data;
        private int count;
        public ListData(T data,int count){
            this.data = data;
            this.count = count;
        }

        public T getData() {
            return data;
        }

        public int getCount() {
            return count;
        }
    }
}
