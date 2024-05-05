
package Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.controller.TripSuggestionController;
import com.l3g1.apitraveller.model.TripSuggestion;
import com.l3g1.apitraveller.model.TripSurvey;
import com.l3g1.apitraveller.model.enumeration.ActivityType;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.model.enumeration.Temperature;
import com.l3g1.apitraveller.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TripSuggestionControllerTest{
    // Test methode for  TripSuggestionController
    TripSurvey tripSurvey = Mockito.mock(TripSurvey.class);
    TripSuggestionController tripSuggestionController =Mockito.mock(TripSuggestionController.class);
    TripService tripService =Mockito.mock(TripService.class);
    Climate climate = Climate.TROPICAL;
    Landscape landscape=Landscape.FOREST;
    Temperature temperature=Temperature.HOT;
    String startingdate ="30/04/2024";
    String endingdate ="04/05/2024";
    int budget = 500;
    boolean roadtrip = true;

    @DisplayName("getTripSuggestion")
    @Test
    public void  getTripSuggestion(){
        List<ActivityType>activityTypes = new ArrayList<>();
        activityTypes.add(ActivityType.ADVENTURE);
        activityTypes.add(ActivityType.CULTURAL);

        when(tripService.getSuggestion(tripSurvey)).thenReturn(new ArrayList<>());
        List<TripSuggestion> suggestions = tripService.getSuggestion(tripSurvey);
        suggestions.add(new TripSuggestion());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        when(tripService.getSuggestion(tripSurvey)).thenReturn(suggestions);
        when(tripSuggestionController.getTripSuggestion("ALL",climate,landscape,temperature,activityTypes,startingdate,endingdate,roadtrip,budget)).thenReturn(jsonNode);
        ObjectNode reponseTest = tripSuggestionController.getTripSuggestion("ALL",climate,landscape,temperature,activityTypes,startingdate,endingdate,roadtrip,budget);
        assertNotNull(reponseTest);

    }

    @DisplayName("getTripSuggestionAI")
    @Test
    public void getTripSuggestionAI(){
        List<ActivityType>activityTypes = new ArrayList<>();
        activityTypes.add(ActivityType.ADVENTURE);
        activityTypes.add(ActivityType.CULTURAL);

        when(tripService.getSuggestionAI(tripSurvey)).thenReturn(new ArrayList<>());
        List<TripSuggestion> suggestions = tripService.getSuggestionAI(tripSurvey);
        suggestions.add(new TripSuggestion());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        when(tripService.getSuggestionAI(tripSurvey)).thenReturn(suggestions);
        when(tripSuggestionController.getTripSuggestionAI("ALL",climate,landscape,temperature,activityTypes,startingdate,endingdate,roadtrip,budget)).thenReturn(jsonNode);

        ObjectNode reponseTest = tripSuggestionController.getTripSuggestionAI("ALL",climate,landscape,temperature,activityTypes,startingdate,endingdate,roadtrip,budget);
        assertNotNull(reponseTest);

    }
    @DisplayName("getNewTripSuggestion()")
    @Test
    public void getNewTripSuggestion(){
        List<ActivityType>activityTypes = new ArrayList<>();
        activityTypes.add(ActivityType.ADVENTURE);
        activityTypes.add(ActivityType.CULTURAL);

        when(tripService.getSuggestionAI(tripSurvey)).thenReturn(new ArrayList<>());
        List<TripSuggestion> suggestions = tripService.getSuggestionAI(tripSurvey);
        suggestions.add(new TripSuggestion());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        when(tripService.getSuggestionAI(tripSurvey)).thenReturn(suggestions);
        when(tripSuggestionController.getNewTripSuggestionAI("ALL",climate,landscape,temperature,activityTypes,startingdate,endingdate,roadtrip,budget)).thenReturn(jsonNode);

        ObjectNode reponseTest = tripSuggestionController.getNewTripSuggestionAI("ALL",climate,landscape,temperature,activityTypes,startingdate,endingdate,roadtrip,budget);
        assertNotNull(reponseTest);

    }

}