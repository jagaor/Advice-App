package com.example.api;

public class AdviceResponse {
    private Slip slip;

    public Slip getSlip() {
        return slip;
    }

    public class Slip {
        private String advice;

        public String getAdvice() {
            return advice;
        }
    }
}
