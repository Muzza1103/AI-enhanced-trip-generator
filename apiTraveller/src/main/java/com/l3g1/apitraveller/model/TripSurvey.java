        package com.l3g1.apitraveller.model;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import java.time.LocalDate;

        import java.util.List;
        import java.util.Date;
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public class TripSurvey {
            private String localisation;
            private Climate climate;
            private Landscape landscape;
            private Temperature temperature;
            private List<ActivityType> activityType;
            private String startingDate;
            private String endingDate;
            private boolean roadTrip;
            private int budget;

        }
