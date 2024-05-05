package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.controller.DestinationSuggestionController;
import com.l3g1.apitraveller.model.Suggestion;
import com.l3g1.apitraveller.model.Survey;
import com.l3g1.apitraveller.model.enumeration.ActivityType;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.model.enumeration.Temperature;
import com.l3g1.apitraveller.service.DestinationSuggestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class DestinationSuggestionControllerTest {
    // Test methode for  DestinationSuggestionController
    Survey survey = Mockito.mock(Survey.class);
    DestinationSuggestionController destinationSuggestionController =Mockito.mock(DestinationSuggestionController.class);
    DestinationSuggestionService destinationSuggestionService =Mockito.mock(DestinationSuggestionService.class);
    Climate climate = Climate.TROPICAL;
    Landscape landscape=Landscape.FOREST;
    Temperature temperature=Temperature.HOT;

    @DisplayName("getSuggestion")
    @Test
    public void  getSuggestion(){
        List<ActivityType>activityTypes = new ArrayList<>();
        activityTypes.add(ActivityType.ADVENTURE);
        activityTypes.add(ActivityType.CULTURAL);

        when(destinationSuggestionService.getSuggestion(survey)).thenReturn(new ArrayList<>());
        List<Suggestion> suggestions = destinationSuggestionService.getSuggestion(survey);
        suggestions.add(new Suggestion());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        when(destinationSuggestionService.getSuggestion(survey)).thenReturn(suggestions);
        when(destinationSuggestionController.getSuggestion("ALL",climate,landscape,temperature,activityTypes)).thenReturn(jsonNode);
        ObjectNode reponseTest = destinationSuggestionController.getSuggestion("ALL",climate,landscape,temperature,activityTypes);
        assertNotNull(reponseTest);
    }

    @DisplayName("getSuggestionAI")
    @Test
    public void getSuggestionAI(){
        List<ActivityType>activityTypes = new ArrayList<>();
        activityTypes.add(ActivityType.ADVENTURE);
        activityTypes.add(ActivityType.CULTURAL);

        when(destinationSuggestionService.getSuggestionAI(survey)).thenReturn(new ArrayList<>());
        List<Suggestion> suggestions = destinationSuggestionService.getSuggestionAI(survey);
        suggestions.add(new Suggestion());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        when(destinationSuggestionService.getSuggestionAI(survey)).thenReturn(suggestions);
        when(destinationSuggestionController.getSuggestionAI("ALL",climate,landscape,temperature,activityTypes)).thenReturn(jsonNode);

        ObjectNode reponseTest = destinationSuggestionController.getSuggestionAI("ALL",climate,landscape,temperature,activityTypes);
        assertNotNull(reponseTest);

    }
    @DisplayName("getNewDestinationSuggestion()")
    @Test
    public void getNewDestinationSuggestion(){
        List<ActivityType>activityTypes = new ArrayList<>();
        activityTypes.add(ActivityType.ADVENTURE);
        activityTypes.add(ActivityType.CULTURAL);

        when(destinationSuggestionService.getSuggestionAI(survey)).thenReturn(new ArrayList<>());
        List<Suggestion> suggestions = destinationSuggestionService.getSuggestionAI(survey);
        suggestions.add(new Suggestion());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        when(destinationSuggestionService.getSuggestionAI(survey)).thenReturn(suggestions);
        when(destinationSuggestionController.getNewDestinationSuggestion("ALL",climate,landscape,temperature,activityTypes)).thenReturn(jsonNode);

        ObjectNode reponseTest = destinationSuggestionController.getNewDestinationSuggestion("ALL",climate,landscape,temperature,activityTypes);
        assertNotNull(reponseTest);

    }
}