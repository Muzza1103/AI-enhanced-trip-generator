package Controller;

import com.l3g1.apitraveller.controller.ActivityController;
import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.service.ActivityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ActivityControllerTest {
    //Test methode for  ActivityController
    ActivityService activityService = Mockito.mock(ActivityService.class);
    ActivityController activityController = Mockito.mock(ActivityController.class);
    Activity activity = Mockito.mock(Activity.class);
    Long id = activity.getActivityID();
    @DisplayName("getActivity()")
    @Test
    public void getActivity(){
        when(activityService.getActivity()).thenReturn(new ArrayList<>());
        Iterable<Activity> resultCity = activityController.getActivity();
        assertEquals(resultCity,activityService.getActivity());
    }

    @DisplayName("getActivityId()")
    @Test
    public void getActivityId(){
        when(activityController.getActivity(id)).thenReturn(Optional.of(activity));
        Optional<Activity> resultCountry = activityController.getActivity(id);
        assertEquals(Optional.of(activity),resultCountry);
    }

    @DisplayName("saveActivity()")
    @Test
    public void saveActivity(){
        when(activityController.saveActivity(activity)).thenReturn(activity);
        when(activityService.saveActivity(activity)).thenReturn(activity);
        assertEquals(activityController.saveActivity(activity),activityService.saveActivity(activity));

    }

    @DisplayName("deleteActivity()")
    @Test
    public void deleteActivity(){
        doNothing().when(activityController).deleteActivity(id);
        activityController.deleteActivity(id);
        assertFalse(activityService.getActivity(id).isPresent());

    }

}
