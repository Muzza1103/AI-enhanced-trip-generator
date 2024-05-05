package Controller;

import com.l3g1.apitraveller.controller.AiController;
import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.model.Survey;
import com.l3g1.apitraveller.model.TripSurvey;
import com.l3g1.apitraveller.service.AiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AiControllerTest {
    //Test methode for AiController
    AiService aiService = Mockito.mock(AiService.class);
    AiController aiController = Mockito.mock(AiController.class);
    Country country = Mockito.mock(Country.class);
    Survey survey =Mockito.mock(Survey.class);
    TripSurvey tripSurvey = Mockito.mock(TripSurvey.class);

    @DisplayName("chat()")
    @Test()
    public void chat(){
        when(aiController.chat()).thenReturn(new String());
        String chat = aiController.chat();
        assertNotNull(chat);

    }
    @DisplayName("chatCity()")
    @Test()
    public void chatCity(){
        when(aiController.chatCity(country.getCountryName())).thenReturn(new String());
        String chatCity = aiController.chatCity(country.getCountryName());
        assertNotNull(chatCity);
    }
    @DisplayName("chatTest()")
    @Test()
    public void chatTest(){
        when(aiController.chatTest(survey)).thenReturn(new String());
        when(aiService.chatCountry(survey)).thenReturn(new String());
        String  chatTest= aiController.chatTest(survey);
        String  chatCountry =  aiService.chatCountry(survey);
        assertEquals(chatTest,chatCountry);
    }

    @DisplayName("chatTripTest")
    @Test()
    public void chatTripTest(){
        when(aiController.chatTest(tripSurvey)).thenReturn(new String());
        when(aiService.chatTripCountry(tripSurvey)).thenReturn(new String());
        String chatTripTest= aiController.chatTest(tripSurvey);
        String chatTripCountry = aiService.chatTripCountry(tripSurvey);
        assertEquals(chatTripTest,chatTripCountry);
    }
}
